package com.jdragon.tljrobot.tlj.controller.home;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jdragon.tljrobot.tlj.mappers.NewsMapper;
import com.jdragon.tljrobot.tlj.pojo.News;
import com.jdragon.tljrobot.tljutils.Result;
import io.swagger.annotations.Api;
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

    @RequestMapping("/articles")
    public String getArticle(){
        return "articles";
    }
    @GetMapping("/moreArticles/{articleType}")
    public String moreArticles(Model model, @PathVariable String articleType){
        model.addAttribute("articleType",articleType);
        return "moreArticles";
    }
    @GetMapping("/showArticle/{articleId}")
    public String showArticle(Model model, @PathVariable String articleId){
        model.addAttribute("articleId",articleId);
        return "showArticle";
    }

    @Autowired
    NewsMapper newsMapper;

    @PostMapping("/GetNew/{id}")
    @ResponseBody
    public Result getNew(@PathVariable int id){
        return Result.success("获取成功").setResult(newsMapper.selectById(id));
    }

    @RequestMapping("/getNewsTypeMap/{page}/{pageSize}")
    @ResponseBody
    public Result getNewsTypeMap(@PathVariable int page, @PathVariable int pageSize){
        String[] types = {"方案魔改","方案教学","提升分享","速度录屏","新鲜出炉"};
        HashMap<String, List<News>> newsTypeMap = new HashMap<>();
        PageHelper.startPage(page,pageSize,true);
        for(String type:types) {
            QueryWrapper<News> queryWrapper = new QueryWrapper();
            queryWrapper.eq(News.Def.NEWS_TYPE, type).orderByDesc(News.Def.ID);
            newsTypeMap.put(type, new PageInfo<News>(newsMapper.selectList(queryWrapper)).getList());
        }
        return Result.success("获取成功").setResult(newsTypeMap);
    }
    @RequestMapping("/getNewsByType/{type}/{page}/{pageSize}")
    @ResponseBody
    public Result getNewsByType(@PathVariable int page, @PathVariable int pageSize, @PathVariable String type){
        PageHelper.startPage(page,pageSize,true);
        QueryWrapper<News> queryWrapper = new QueryWrapper();
        queryWrapper.eq(News.Def.NEWS_TYPE, type).orderByDesc(News.Def.ID);
        return Result.success("获取成功").setResult(newsMapper.selectList(queryWrapper));
    }
    @PostMapping("/getNewsById/{articleId}")
    @ResponseBody
    public Result getNews(@PathVariable int articleId){
        News news = newsMapper.selectById(articleId);
        news.setClickNum(news.getClickNum()+1);
        news.updateById();
        news.setMdContent("****");
        return Result.success("获取成功").setResult(news);
    }

    @PostMapping("/getHotArticles")
    @ResponseBody
    public Result getHotArticles(){
        PageHelper.startPage(1,10,true);
        return Result.success("获取成功").setResult(
                new PageInfo<>(newsMapper.selectList(new QueryWrapper<News>().
                                orderByDesc(News.Def.CLICK_NUM))).getList());
    }
}
