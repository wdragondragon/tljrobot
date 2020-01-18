package com.jdragon.tljrobot.tlj.mappers;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jdragon.tljrobot.tlj.pojo.Article;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

/**
 * Create by Jdragon on 2020.01.17
 */
@Component
@Mapper
public interface ArticleMapper extends BaseMapper<Article> {
    @Results({
            @Result(column = "id",property = "id",id = true),
            @Result(column = "title" , property = "title"),
            @Result(column = "content",property = "content")
    })
    @Select("select * from all_article where id=#{id};")
    public Article getArticleById(int id);
}
