package com.jdragon.tljrobot.tlj.controller.conditional.editor;

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

@Controller
@RequestMapping("/UEditor")
public class UEditor {
    @Autowired
    BlogMapper blogMapper;
    @GetMapping("/updateBlog/{articleId}/{userId}")
    public String updateArticle(Model model, @PathVariable String userId, @PathVariable String articleId){
        model.addAttribute("blogId",articleId);
        return "editor/updateUEditor";
    }
    @RequestMapping("/editor/{userId}")
    public String ed1(@PathVariable String userId){
        return "editor/editor";
    }
    @PostMapping("/blogPublish/{userId}")
    @ResponseBody
    public Result publish(@RequestBody Blog blog, @PathVariable String userId){
        User user = (User)Local.getSession(userId);
        blog.setAuthor(user.getUsername());
        blog.setPublishTime(DateUtil.now());
        if(blog.insert()){
            System.out.println("new"+ blog.toString());
            return Result.success("上传成功").setResult(blog.getId());
        }else {
            return Result.success("上传失败");
        }
    }

    @PostMapping("/blog/{articleId}/{userId}")
    @ResponseBody
    public Result getNews(@PathVariable int articleId, @PathVariable String userId){
        User user = (User)Local.getSession(userId);
        Blog blog = blogMapper.selectById(articleId);
        if(blog ==null){
            return Result.error("获取失败，该文章不存在");
        }else if(blog.getAuthor().equals(user.getUsername())){
            return Result.success("获取成功").setResult(blog);
        }else{
            return Result.error("不能获取别人的文章来修改");
        }
    }

    @PostMapping("/updateBlog/{userId}")
    @ResponseBody
    public Result updateArticle(@RequestBody Blog blog, @PathVariable String userId){
        User user = (User)Local.getSession(userId);
        Blog oldBlog = blogMapper.selectById(blog.getId());
        if(oldBlog ==null){
            return Result.error("文章不存在，无法修改");
        }else if(oldBlog.getAuthor().equals(user.getUsername())){
            if(blogMapper.updateById(blog)>0){
                return Result.success("更新成功");
            }else {
                return Result.error("更新失败");
            }
        }else{
            return Result.error("更新失败，你不能更新别人的文章");
        }
    }
}
