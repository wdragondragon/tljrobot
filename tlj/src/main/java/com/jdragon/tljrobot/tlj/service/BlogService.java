package com.jdragon.tljrobot.tlj.service;

import com.jdragon.tljrobot.tlj.pojo.Blog;

import java.util.List;

/**
 * Create by Jdragon on 2020.01.18
 */
public interface BlogService {
    public Blog getBlogById(int blogId);
    public List<Blog> getBlogByType(String type);
    public List<Blog> getHotBlog(int hotNum);

}
