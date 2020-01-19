package com.jdragon.tljrobot.tlj.service.serviceImpl;

import com.alibaba.nacos.common.util.Md5Utils;
import com.jdragon.tljrobot.tlj.mappers.ArticleMapper;
import com.jdragon.tljrobot.tlj.mappers.TljMatchMapper;
import com.jdragon.tljrobot.tlj.mappers.UserMapper;
import com.jdragon.tljrobot.tlj.pojo.Article;
import com.jdragon.tljrobot.tlj.pojo.History;
import com.jdragon.tljrobot.tlj.pojo.TljMatch;
import com.jdragon.tljrobot.tlj.pojo.User;
import com.jdragon.tljrobot.tlj.service.TljService;
import com.jdragon.tljrobot.tljutils.ArticleUtil;
import com.jdragon.tljrobot.tljutils.DateUtil;
import com.jdragon.tljrobot.tljutils.Local;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

/**
 * Create by Jdragon on 2020.01.18
 */
@PropertySource("classpath:application.yml")
@Service
public class TljServiceImpl implements TljService {
    @Value("${server.secret}")
    String secret;

    @Autowired
    UserMapper userMapper;

    @Autowired
    ArticleMapper articleMapper;

    @Autowired
    TljMatchMapper tljMatchMapper;

    @Override
    public boolean checkNumSecret(int num, int rightNum, int misNum, int dateNum, String numKey) {
        if(!Md5Utils.getMD5((num+rightNum+misNum+dateNum+secret).getBytes()).equals(numKey)){
            return false;
        }
        else return true;
    }

    @Override
    public boolean changeNum(String userId,int num, int rightNum, int misNum, int dateNum) {
        User user = (User) Local.getSession(userId);
        user.setNum(num);
        user.setRightNum(rightNum);
        user.setMisNum(misNum);
        user.setDateNum(dateNum);
        int i = userMapper.updateById(user);
        if(i>0)return true;
        else return false;
    }

    @Override
    public TljMatch insertTljMatch() {
        TljMatch tljMatch = tljMatchMapper.selectTodayTljMatchByDate(DateUtil.now());
        if(tljMatch==null){
            String random = ArticleUtil.getRandomContent();
            Article article = articleMapper.selectArticleByContent(random);
            if(article==null){
                article = new Article("拖拉机日赛",random);
                if(!article.insert())return null;
            }
            tljMatch = new TljMatch(article,DateUtil.now(),"随机生成");
            if(!tljMatch.insert())return null;
        }
        return tljMatch;
    }

    @Override
    public boolean uploadTljMatchAch(int userId, History history) {
        TljMatch tljMatch = tljMatchMapper.selectTodayTljMatchByDate(DateUtil.now());
        history.setArticleId(tljMatch.getArticle().getId());
        history.setUserId(userId);
        history.setTypeDate(DateUtil.now());
        return history.updateById();
    }

    @Override
    public boolean uploadHistory(int userId, History history,Article articleTemp) {
        Article article = articleMapper.selectArticleByContent(articleTemp.getContent());
        if(article==null) {
            if (articleTemp.insert()) {
                article = articleTemp;
            } else {
                return false;
            }
        }
        history.setArticleId(article.getId());
        history.setUserId(userId);
        history.setTypeDate(DateUtil.now());
        if (history.insert())
            return true;
        else return false;
    }
}
