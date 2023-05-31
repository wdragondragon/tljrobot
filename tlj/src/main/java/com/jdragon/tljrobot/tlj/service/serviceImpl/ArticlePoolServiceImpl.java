package com.jdragon.tljrobot.tlj.service.serviceImpl;

import com.alibaba.fastjson.JSONObject;
import com.jdragon.tljrobot.tlj.mappers.ArticlePoolMapper;
import com.jdragon.tljrobot.tlj.pojo.ArticlePool;
import com.jdragon.tljrobot.tlj.service.ArticlePoolService;
import com.jdragon.tljrobot.tljutils.ArticleUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author JDragon
 * @date 2023/5/31 11:35
 * @description
 */
@Slf4j
@Service
public class ArticlePoolServiceImpl implements ArticlePoolService {

    @Autowired
    private ArticlePoolMapper articlePoolMapper;

    private final Pattern contentPattern = Pattern.compile("<div class='flex flex-col mt-4 text-gray-700 leading-7 text-justify'><p>(.*?)</p></div>");

    private final Pattern titlePattern = Pattern.compile("<div class='flex text-xl font-bold text-gray-800'>(.*?)</div>");

    private final String removeStr = "<br>|</br>|「|」|<p>|</p>|\\s*";

    @Override
    public void insertNewContent() {
        String selectArticlePoolUrl = articlePoolMapper.selectArticlePoolUrl();
        boolean existsContent = false;
        int retry = 0;
        do {
            String urlConStr = ArticleUtil.getUrlConStr(selectArticlePoolUrl);
            JSONObject jsonObject = JSONObject.parseObject(urlConStr);
            String shareUrl = jsonObject.getString("share_url");
            String urlConStr2 = ArticleUtil.getUrlConStr(shareUrl);
            Matcher matcher = contentPattern.matcher(urlConStr2);
            String content = null;
            if (matcher.find()) {
                content = matcher.group(1);
                content = content.replaceAll(removeStr, "");
            }

            Matcher matcher2 = titlePattern.matcher(urlConStr2);
            String title = null;
            if (matcher2.find()) {
                title = matcher2.group(1);
                title = title.replaceAll(removeStr, "");
            }

            if (StringUtils.isNoneBlank(title, content)) {
                existsContent = articlePoolMapper.existsContent(content);
                if (!existsContent) {
                    ArticlePool articlePool = new ArticlePool(title, content);
                    articlePoolMapper.insert(articlePool);
                    log.info("文章插入成功 title:{}", title);
                } else {
                    log.info("文章已存在 title:{} , retry:{}", title, retry++);
                }
            } else {
                log.info("获取文章title或content为空 retry:{}", retry++);
            }
        } while (existsContent && retry < 10);
    }
}
