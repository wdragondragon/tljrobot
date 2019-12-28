package com.jdragon.tljrobot.tlj.controller.conditional.editor;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jdragon.tljrobot.tlj.mappers.NewsMapper;
import com.jdragon.tljrobot.tlj.pojo.News;
import com.jdragon.tljrobot.tlj.pojo.User;
import com.jdragon.tljrobot.tljutils.DateUtil;
import com.jdragon.tljrobot.tljutils.Local;
import com.jdragon.tljrobot.tljutils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/MEditor")
public class MdEditor {
    @Autowired
    NewsMapper newsMapper;

    @RequestMapping("/editor/{userId}")
    public String ed2(@PathVariable String userId){return "simple";}
    @RequestMapping("/full/{userId}")
    public String ed3(@PathVariable String userId){return "full";}
    @PostMapping("/newPublish/{userId}")
    @ResponseBody
    public Result publish(@RequestBody News news, @PathVariable String userId){
        news.setPublishTime(DateUtil.now());
        User user = (User)Local.getSession(userId);
        news.setAuthor(user.getName());
        if(news.insert())
            return Result.success("上传成功");
        else
            return Result.error("上传失败");
    }

    @PostMapping("/getMdContent/{articleId}/{userId}")
    @ResponseBody
    public Result getMdContent(@PathVariable int articleId, @PathVariable String userId){
        News news = newsMapper.selectById(articleId);
        User user =  (User)Local.getSession(userId);
        if(news==null){
            return Result.error("无该id文章");
        }else if(!user.getName().equals(news.getAuthor())){
            return Result.error("不是你上传的文章无法获取");
        }else{
            return Result.success("获取成功").setResult(news);
        }
    }
    @RequestMapping("/manageArticle/{userId}")
    public String getManageArticle(@PathVariable String userId){
        return "manageArticle";
    }
    @PostMapping("/getNewsByUserName/{userId}")
    @ResponseBody
    public Result getNewsByUserId(@PathVariable String userId){
        User user = (User)Local.getSession(userId);
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq(News.Def.NEWS_AUTHOR,user.getName());
        List<News> news = newsMapper.selectList(queryWrapper);
        return Result.success("获取成功").setResult(news);
    }
    @PostMapping("/delete/{articleId}/{userId}")
    @ResponseBody
    public Result delete(@PathVariable int articleId, @PathVariable String userId){
        News news = newsMapper.selectById(articleId);
        User user = (User)Local.getSession(userId);
        if(news==null)return Result.error("无该文章");
        else if(news.getAuthor().equals(user.getName())) {
            int i = newsMapper.deleteById(articleId);
            if(i>0)return Result.success("删除成功");
            else return Result.error("删除失败");
        }else{
            return Result.error("你不能删除别人的文章");
        }
    }
    @GetMapping("/updateArticle/{userId}")
    public String updateArticle(@PathVariable String userId){
        return "updateMD";
    }
    @PostMapping("/updateArticle/{userId}")
    @ResponseBody
    public Result update(@RequestBody News news, @PathVariable String userId){
        News oldNews = newsMapper.selectById(news.getId());
        User user = (User)Local.getSession(userId);
        if(oldNews==null)return Result.error("无该文章无法修改");
        else if(oldNews.getAuthor().equals(user.getName())) {
            int i = newsMapper.updateById(news);
            if (i > 0) return Result.success("更新成功");
            else return Result.error("更新失败");
        }else{
            return Result.error("你不能修改别人的文章");
        }
    }
}
