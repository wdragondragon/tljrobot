package com.jdragon.tljrobot.tlj.controller.conditional.robot;

import com.jdragon.tljrobot.tlj.pojo.Article;
import com.jdragon.tljrobot.tljutils.Result;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

/**
 * @author 10619
 */
@RestController
@RequestMapping("/robot/groupArticleCache")
public class GroupArticleCache {
    public static HashMap<Long,Article> articleHashMap = new HashMap<>();
    @PostMapping("/uploadArticle")
    public Result uploadArticle(@RequestParam String groupId, @RequestBody Article article){
        articleHashMap.put(Long.valueOf(groupId),article);
        return Result.success("上传成功");
    }
    @PostMapping("/getArticle")
    public Result uploadArticle(@RequestParam String groupId){
        return Result.success("获取成功").setResult(articleHashMap.get(Long.valueOf(groupId)));
    }
}
