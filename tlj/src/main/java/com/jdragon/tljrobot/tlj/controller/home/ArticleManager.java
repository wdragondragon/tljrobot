package com.jdragon.tljrobot.tlj.controller.home;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jdragon.tljrobot.tlj.mappers.BlogMapper;
import com.jdragon.tljrobot.tlj.pojo.Blog;
import com.jdragon.tljrobot.tlj.service.BlogService;
import com.jdragon.tljrobot.tljutils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@Controller
@RequestMapping("/home")
@Api(tags = "无条件操作")
public class ArticleManager {

    @GetMapping("/blog")
    @ApiOperation("进入文章特区")
    public String getBlog(){
        return "blog";
    }
    @GetMapping("/moreBlog/{blogType}")
    @ApiOperation("进入更多分类页面")
    public String moreBlogs(Model model, @PathVariable String blogType){
        model.addAttribute("blogType",blogType);
        return "moreBlog";
    }
    @GetMapping("/showBlog/{blogId}")
    @ApiOperation("打开某篇文章")
    public String showBlog(Model model, @PathVariable String blogId){
        model.addAttribute("blogId",blogId);
        return "showBlog";
    }
    @Autowired
    BlogMapper blogMapper;
    @Autowired
    BlogService blogService;

    @PostMapping("/GetBlog/{id}")
    @ApiOperation("通过id获取文章详情（用于修改文章）")
    @ResponseBody
    public Result getBlog(@PathVariable int id){
        return Result.success("获取成功").setResult(blogMapper.selectById(id));
    }

    @PostMapping("/getBlogTypeMap/{page}/{pageSize}")
    @ApiOperation("分页获取分类文章")
    @ResponseBody
    public Result getBlogTypeMap(@PathVariable int page, @PathVariable int pageSize){
        PageHelper.startPage(page,pageSize,true);
        String[] types = {"方案魔改","方案教学","提升分享","速度录屏","新鲜出炉"};
        HashMap<String, List<Blog>> newsTypeMap = new HashMap<>();
        for(String type:types) {
            newsTypeMap.put(type, new PageInfo<>(blogService.getBlogByType(type)).getList());
        }
        return Result.success("获取成功").setResult(newsTypeMap);
    }
    @PostMapping("/getBlogByType/{type}/{page}/{pageSize}")
    @ApiOperation("通过类型分页获取文章")
    @ResponseBody
    public Result getBlogByType(@PathVariable int page, @PathVariable int pageSize, @PathVariable String type){
        PageHelper.startPage(page,pageSize,true);
        List<Blog> blogList = blogService.getBlogByType(type);
        PageInfo<Blog> blogPageInfo = new PageInfo<>(blogList);
        return Result.success("获取成功").setResult(blogPageInfo.getList());
    }
    @PostMapping("/getBlogById/{blogId}")
    @ApiOperation("通过id获取文章（用于用户浏览）")
    @ResponseBody
    public Result getBlogById(@PathVariable int blogId){
        return Result.success("获取成功").setResult(blogService.getBlogById(blogId));
    }
    @PostMapping("/getHotBlog")
    @ApiOperation("获取点击量较高的文章")
    @ResponseBody
    public Result getHotBlog(){
        return Result.success("获取成功").setResult(blogService.getHotBlog(10));
    }
}