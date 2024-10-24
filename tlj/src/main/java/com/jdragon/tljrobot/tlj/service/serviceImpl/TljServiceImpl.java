package com.jdragon.tljrobot.tlj.service.serviceImpl;

import com.alibaba.nacos.common.util.Md5Utils;
import com.jdragon.tljrobot.tlj.mappers.ArticleMapper;
import com.jdragon.tljrobot.tlj.mappers.HistroyMapper;
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
import org.springframework.transaction.annotation.Transactional;

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

    @Autowired
    HistroyMapper histroyMapper;
    @Override
    public boolean checkNumSecret(int num, int rightNum, int misNum, int dateNum, String numKey) {
        if(!Md5Utils.getMD5((num+rightNum+misNum+dateNum+secret).getBytes()).equals(numKey)){
            return false;
        }
        else {
            return true;
        }
    }

    @Override
    public boolean changeNum(String userId,int num, int rightNum, int misNum, int dateNum) {
        User user = (User) Local.getSession(userId);
        user.setNum(num);
        user.setRightNum(rightNum);
        user.setMisNum(misNum);
        user.setDateNum(dateNum);
        int i = userMapper.updateById(user);
        return i > 0;
    }

    @Transactional
    @Override
    public TljMatch insertTljMatch() {
        TljMatch tljMatch = tljMatchMapper.selectTljMatchByDate(DateUtil.now());
        if(tljMatch==null){
            String random = ArticleUtil.getRandomContent();
            String title = DateUtil.now()+"日生稿赛";
            Article article = articleMapper.selectArticleByContent(title,random);
            if(article==null){
                article = new Article(title,random);
                if(!article.insert()) {
                    return null;
                }
            }
            tljMatch = new TljMatch(article,DateUtil.now(),"随机生成");
            if(!tljMatch.insert()) {
                return null;
            }
        }
        return tljMatch;
    }

    @Override
    public boolean uploadTljMatchAch(int userId, History history) {
        TljMatch tljMatch = tljMatchMapper.selectTljMatchByDate(DateUtil.now());
        history.setArticleId(tljMatch.getArticle().getId());
        history.setUserId(userId);
        return histroyMapper.updateById(history)>0;
    }

    @Transactional
    @Override
    public boolean uploadHistory(int userId, History history,Article articleTemp) {
        if(history.getTime()<0)return false;
        Article article = articleMapper.selectArticleByContent(articleTemp.getTitle(),articleTemp.getContent());
        if(article==null) {
            if (articleMapper.insert(articleTemp)>0) {
                article = articleTemp;
            } else {
                return false;
            }
        }
        history.setArticleId(article.getId());
        history.setUserId(userId);
        return histroyMapper.insert(history)>0;
    }
}
