package com.jdragon.tljrobot.tlj.controller.conditional.editor;

import com.jdragon.tljrobot.tlj.mappers.NewsMapper;
import com.jdragon.tljrobot.tlj.pojo.News;
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
    NewsMapper newsMapper;
    @GetMapping("/updateArticle/{articleId}/{userId}")
    public String updateArticle(Model model, @PathVariable String userId, @PathVariable String articleId){
        model.addAttribute("articleId",articleId);
        return "editor/updateUEditor";
    }
    @RequestMapping("/editor/{userId}")
    public String ed1(@PathVariable String userId){
        return "editor/editor";
    }
    @PostMapping("/newPublish/{userId}")
    @ResponseBody
    public Result publish(@RequestBody News news, @PathVariable String userId){
        User user = (User)Local.getSession(userId);
        news.setAuthor(user.getName());
        news.setPublishTime(DateUtil.now());
        if(news.insert()){
            System.out.println("new"+news.toString());
            return Result.success("上传成功").setResult(news.getId());
        }else {
            return Result.success("上传失败");
        }
    }

    @PostMapping("/getNew/{articleId}/{userId}")
    @ResponseBody
    public Result getNews(@PathVariable int articleId, @PathVariable String userId){
        User user = (User)Local.getSession(userId);
        News news = newsMapper.selectById(articleId);
        if(news==null){
            return Result.error("获取失败，该文章不存在");
        }else if(news.getAuthor().equals(user.getName())){
            return Result.success("获取成功").setResult(news);
        }else{
            return Result.error("不能获取别人的文章来修改");
        }
    }

    @PostMapping("/updateArticle/{userId}")
    @ResponseBody
    public Result updateArticle(@RequestBody News news , @PathVariable String userId){
        User user = (User)Local.getSession(userId);
        News oldNews = newsMapper.selectById(news.getId());
        if(oldNews==null){
            return Result.error("文章不存在，无法修改");
        }else if(oldNews.getAuthor().equals(user.getName())){
            if(newsMapper.updateById(news)>0){
                return Result.success("更新成功");
            }else {
                return Result.error("更新失败");
            }
        }else{
            return Result.error("更新失败，你不能更新别人的文章");
        }
    }
}
