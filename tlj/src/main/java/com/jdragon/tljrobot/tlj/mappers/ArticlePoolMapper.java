package com.jdragon.tljrobot.tlj.mappers;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jdragon.tljrobot.tlj.pojo.ArticlePool;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

/**
 * @author JDragon
 * @date 2023/5/31 11:25
 * @description
 */
@Component
@Mapper
public interface ArticlePoolMapper extends BaseMapper<ArticlePool> {
    @Select("select article_pool_url from article_pool_url limit 1")
    String selectArticlePoolUrl();

    @Select("select exists(select content from article_pool where content=#{content}})")
    Boolean existsContent(String content);
}
