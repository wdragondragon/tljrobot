package com.jdragon.tljrobot.tlj.service.serviceImpl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jdragon.tljrobot.tlj.mappers.BlogMapper;
import com.jdragon.tljrobot.tlj.pojo.Blog;
import com.jdragon.tljrobot.tlj.service.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Create by Jdragon on 2020.01.18
 */
@Service
public class BlogServiceImpl implements BlogService {
    @Autowired
    BlogMapper blogMapper;

    @Override
    public Blog getBlogById(int blogId) {
        Blog blog = blogMapper.selectById(blogId);
        blog.setClickNum(blog.getClickNum()+1);
        blog.updateById();
        blog.setMdContent("****");
        return blog;
    }
    @Override
    public List<Blog> getBlogByType(String type) {
        QueryWrapper<Blog> queryWrapper = new QueryWrapper();
        queryWrapper.eq(Blog.Def.NEWS_TYPE, type).orderByDesc(Blog.Def.ID);
        return blogMapper.selectList(queryWrapper);
    }

    @Override
    public List<Blog> getHotBlog(int hotNum) {
        PageHelper.startPage(1,hotNum,true);
        return new PageInfo<>(blogMapper.selectList(new QueryWrapper<Blog>().
                orderByDesc(Blog.Def.CLICK_NUM))).getList();
    }
}
