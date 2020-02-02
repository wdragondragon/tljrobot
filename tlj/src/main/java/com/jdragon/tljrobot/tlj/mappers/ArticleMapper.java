package com.jdragon.tljrobot.tlj.mappers;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jdragon.tljrobot.tlj.pojo.Article;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

/**
 * Create by Jdragon on 2020.01.17
 */
@Component
@Mapper
public interface ArticleMapper extends BaseMapper<Article> {
    @Results(id = "articleResultMap",value = {
            @Result(column = "id",property = "id",id = true),
            @Result(column = "title" , property = "title"),
            @Result(column = "content",property = "content")
    })
    @Select("select * from all_article where id=#{id};")
    public Article selectArticleById(int id);

    @Select("select * from all_article where title=#{title} and content=#{content}")
    public Article selectArticleByContent(String title,String content);
}
