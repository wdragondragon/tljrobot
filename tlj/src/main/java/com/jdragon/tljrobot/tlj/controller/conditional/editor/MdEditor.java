package com.jdragon.tljrobot.tlj.controller.conditional.editor;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jdragon.tljrobot.tlj.mappers.BlogMapper;
import com.jdragon.tljrobot.tlj.pojo.Blog;
import com.jdragon.tljrobot.tlj.pojo.User;
import com.jdragon.tljrobot.tljutils.DateUtil;
import com.jdragon.tljrobot.tljutils.Local;
import com.jdragon.tljrobot.tljutils.Result;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/MEditor")
public class MdEditor {
    @Autowired
    BlogMapper blogMapper;

    @RequestMapping("/editor/{userId}")
    public String ed2(@PathVariable String userId){return "editor/MDEditor";}
    @GetMapping("/updateBlog/{articleId}/{userId}")
    public String updateArticle(Model model, @PathVariable String userId, @PathVariable String articleId){
        model.addAttribute("blogId",articleId);
        return "editor/updateMD";
    }
    @RequestMapping("/manageBlog/{userId}")
    public String getManageArticle(@PathVariable String userId){
        return "manageBlog";
    }
    @RequestMapping("/full/{userId}")
    public String ed3(@PathVariable String userId){return "model/full";}
    @PostMapping("/blogPublish/{userId}")
    @ResponseBody
    public Result publish(@RequestBody Blog blog, @PathVariable String userId){
        blog.setPublishTime(DateUtil.now());
        User user = (User)Local.getSession(userId);
        blog.setAuthor(user.getUsername());
        if(blogMapper.insert(blog)>0)
            return Result.success("上传成功").setResult(blog.getId());
        else
            return Result.error("上传失败");
    }
    @PostMapping("/getMdContent/{articleId}/{userId}")
    @ResponseBody
    public Result getMdContent(@PathVariable int articleId, @PathVariable String userId){
        Blog blog = blogMapper.selectById(articleId);
        User user =  (User)Local.getSession(userId);
        if(blog ==null){
            return Result.error("无该id文章");
        }else if(!user.getUsername().equals(blog.getAuthor())){
            return Result.error("不是你上传的文章无法获取");
        }else{
            return Result.success("获取成功").setResult(blog);
        }
    }
    @PostMapping("/getBlogByUserName/{userId}")
    @ResponseBody
    public Result getNewsByUserId(@PathVariable String userId){
        User user = (User)Local.getSession(userId);
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq(Blog.Def.NEWS_AUTHOR,user.getUsername());
        List<Blog> blogs = blogMapper.selectList(queryWrapper);
        return Result.success("获取成功").setResult(blogs);
    }
    @PostMapping("/delete/{articleId}/{userId}")
    @ResponseBody
    public Result delete(@PathVariable int articleId, @PathVariable String userId){
        Blog blog = blogMapper.selectById(articleId);
        User user = (User)Local.getSession(userId);
        if(blog ==null)return Result.error("无该文章");
        else if(blog.getAuthor().equals(user.getUsername())) {
            int i = blogMapper.deleteById(articleId);
            if(i>0)return Result.success("删除成功");
            else return Result.error("删除失败");
        }else{
            return Result.error("你不能删除别人的文章");
        }
    }

    @PostMapping("/updateBlog/{userId}")
    @ResponseBody
    public Result update(@RequestBody Blog blog, @PathVariable String userId){
        Blog oldBlog = blogMapper.selectById(blog.getId());
        User user = (User)Local.getSession(userId);
        if(oldBlog ==null)return Result.error("无该文章无法修改");
        else if(oldBlog.getAuthor().equals(user.getUsername())) {
            int i = blogMapper.updateById(blog);
            if (i > 0) return Result.success("更新成功");
            else return Result.error("更新失败");
        }else{
            return Result.error("你不能修改别人的文章");
        }
    }
}
