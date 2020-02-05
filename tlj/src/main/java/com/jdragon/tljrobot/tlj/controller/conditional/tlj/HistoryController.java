package com.jdragon.tljrobot.tlj.controller.conditional.tlj;

import com.jdragon.tljrobot.tlj.pojo.Article;
import com.jdragon.tljrobot.tlj.pojo.History;
import com.jdragon.tljrobot.tlj.pojo.User;
import com.jdragon.tljrobot.tlj.service.TljService;
import com.jdragon.tljrobot.tljutils.Local;
import com.jdragon.tljrobot.tljutils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * Create by Jdragon on 2020.01.20
 */
@Controller
@RequestMapping("/history")
@Api(tags = "跟打器历史跟打记录")
public class HistoryController {
    @Autowired
    TljService tljService;

    @PostMapping("/uploadHistory/{userId}")
    @ApiOperation("上传历史记录")
    @ResponseBody
    public Result uploadHistory(@PathVariable String userId,
                                @RequestParam String title,
                                @RequestParam String content,
                                @RequestBody History history){
        User user = (User) Local.getSession(userId);
        Article article = new Article(title,content);
        if(history.getParagraph()==0)history.setParagraph(1);
        if(tljService.uploadHistory(user.getId(),history,article)){
            return Result.success("上传成功");
        }else{
            return Result.error("上传失败");
        }
    }

    @PostMapping("/getHistoryArticle/{articleId}/{userId}")
    @ApiOperation("通过articleId获取文章详情")
    @ResponseBody
    public Result getArticleById(@PathVariable String articleId, @PathVariable String userId){
        Article article = new Article().selectById(articleId);
        if(article==null){
            return Result.error("没有该文章");
        }else {
            return Result.success("获取成功").setResult(article);
        }
    }
}
