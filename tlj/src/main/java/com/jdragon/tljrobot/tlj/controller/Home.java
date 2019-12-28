package com.jdragon.tljrobot.tlj.controller;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jdragon.tljrobot.tlj.mappers.NewsMapper;
import com.jdragon.tljrobot.tlj.mappers.UserMapper;
import com.jdragon.tljrobot.tlj.pojo.News;
import com.jdragon.tljrobot.tlj.pojo.User;
import com.jdragon.tljrobot.tljutils.Local;
import com.jdragon.tljrobot.tljutils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@Controller
@RequestMapping("/home")
@Api(tags = "无条件操作")
public class Home {
    @Autowired
    private UserMapper userMapper;
    @PostMapping(value = "/login/{username}/{password}")
    @ApiOperation(value = "登录接口")
    @ResponseBody
    public Result Login(@ApiParam(name = "username",value = "用户名")@PathVariable("username") String name,
                        @ApiParam(name = "password",value = "登录密码")@PathVariable("password") String pwd){
        User user1 = userMapper.selectById(name);
        if(user1==null){
            return  Result.success("无该用户");
        }else if(!user1.getPwd().equals(pwd)){
            return  Result.success("密码错误");
        }else{
            String userId = Local.login(user1);
            return Result.success("登录成功").setResult(userId);
        }
    }
    @GetMapping(value = "/login")
    @ApiOperation("进入登录界面")
    public String Login(){
        return "login";
    }
    @PostMapping(value = "/logout/{userId}")
    @ApiOperation("退出登录")
    @ResponseBody
    public Result Logout(@ApiParam(name = "userId",value = "使用userId退出")@PathVariable String userId){
        Local.logout(userId);
        return Result.success("退出成功");
    }
    @PostMapping(value = "/allUser/{page}")
    @ApiOperation(value = "获取全部用户信息", response = User.class)
    @ApiResponse(code = 200, message = "获取成功")
    @ResponseBody
    public Result AllUser(@PathVariable int page){
        PageHelper.startPage(page,20,true);
        QueryWrapper<User> queryWrapper = new QueryWrapper<User>();
        queryWrapper.orderByDesc(User.Def.USER_NUM);
        List<User> users = userMapper.selectList(queryWrapper);
        PageInfo<User> pageInfo = new PageInfo<User>(users);
        return Result.success("获取成功").setResult(pageInfo);
    }
    @GetMapping(value = "/register")
    @ApiOperation(value = "进入注册界面")
    public String register(){
        return "register";
    }
    @PostMapping(value = "/register/{name}/{pwd}")
    @ApiOperation(value = "注册接口")
    @ResponseBody
    public Result register(@ApiParam(name = "name",value = "用户名")@PathVariable String name,
                           @ApiParam(name = "pwd",value = "密码")@PathVariable String pwd){
        User user = userMapper.selectById(name);
        if(user==null){
            user = new User(name, pwd);
            if(user.insert())
                return Result.success("注册成功");
            else
                return Result.error("注册失败");
        }else {
            return Result.error("已存在");
        }
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
        HashMap<String,List<News>> newsTypeMap = new HashMap<>();
        PageHelper.startPage(page,pageSize,true);
        for(String type:types) {
            QueryWrapper queryWrapper = new QueryWrapper();
            queryWrapper.eq(News.Def.NEWS_TYPE, type);
            newsTypeMap.put(type, new PageInfo<News>(newsMapper.selectList(queryWrapper)).getList());
        }
        return Result.success("获取成功").setResult(newsTypeMap);
    }
    @RequestMapping("/getNewsByType/{type}/{page}/{pageSize}")
    @ResponseBody
    public Result getNewsByType(@PathVariable int page, @PathVariable int pageSize, @PathVariable String type){
        PageHelper.startPage(page,pageSize,true);
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq(News.Def.NEWS_TYPE, type);
        return Result.success("获取成功").setResult(newsMapper.selectList(queryWrapper));
    }
    @PostMapping("/getNews/{newId}")
    @ResponseBody
    public Result getNews(@PathVariable int newId){
        News news = newsMapper.selectById(newId);
        news.setClickNum(news.getClickNum()+1);
        news.updateById();
        news.setMdContent("****");
        return Result.success("获取成功").setResult(news);
    }

    @RequestMapping("/introduce")
    public String getIntroduce(){
        return "introduce";
    }
    @RequestMapping("/myInfo")
    public String getMyInfo(){
        return "myinfo";
    }
    @RequestMapping("/allUser")
    public String getAllUser(){
        return "AllUser";
    }
    @RequestMapping("/historyTable")
    public String getHistoryTable(){
        return "AllHistory";
    }
    @RequestMapping("/footer")
    public String getFooter(){
        return "footer";
    }
    @RequestMapping("/article")
    public String getArticle(){
        return "articles";
    }
    @RequestMapping("/showArticle")
    public String showArticle(){
        return "showArticle";
    }
    @RequestMapping("/showArticle/{articleId}")
    public String showArticle(Model model, @PathVariable String articleId){
        model.addAttribute("articleId",articleId);
        return "showArticle";
    }
    @RequestMapping("/moreArticles")
    public String moreArticles(){
        return "moreArticles";
    }
    @RequestMapping("/community")
    public String community(){
        return "Community";
    }
}
