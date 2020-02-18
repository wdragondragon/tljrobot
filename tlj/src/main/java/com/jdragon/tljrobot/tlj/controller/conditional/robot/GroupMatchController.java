package com.jdragon.tljrobot.tlj.controller.conditional.robot;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jdragon.tljrobot.tlj.mappers.ArticleMapper;
import com.jdragon.tljrobot.tlj.mappers.GroupMatchMapper;
import com.jdragon.tljrobot.tlj.pojo.Article;
import com.jdragon.tljrobot.tlj.pojo.GroupMatch;
import com.jdragon.tljrobot.tljutils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;

/**
 * Create by Jdragon on 2020.02.04
 */
@RestController
@RequestMapping("/robot/groupMatch")
@Api("机器人群赛接口")
public class GroupMatchController {
    @Autowired
    GroupMatchMapper groupMatchMapper;

    @Autowired
    ArticleMapper articleMapper;

    @PostMapping("/uploadGroupMatch")
    @ApiOperation("收集群日赛赛文")
    public Result uploadGroupMatch(@RequestBody GroupMatch groupMatch){
        Article newArticle = groupMatch.getArticle();
        Article article = articleMapper.selectArticleByContent(newArticle.getTitle(),newArticle.getContent());
        int articleId;
        if(article!=null) {
            articleId = article.getId();
        }else{
            newArticle.insert();
            articleId = newArticle.getId();
        }
        QueryWrapper<GroupMatch> queryWrapper = new QueryWrapper<>();
        GroupMatch oldGroupMatch = groupMatchMapper.selectOne(queryWrapper.
                eq(GroupMatch.Def.GROUP_ID, groupMatch.getGroupId()).
                eq(GroupMatch.Def.HOLD_DATE, groupMatch.getHoldDate()));
        if (oldGroupMatch == null) {
            groupMatch.setArticleId(articleId);
            boolean result = groupMatch.insert();
            if (result) {
                return Result.success("上传成功");
            } else {
                return Result.success("上传失败");
            }
        } else {
            return Result.success("重复上传");
        }
    }
    @PostMapping("/getGroupMatch/{groupId}/{holdDate}")
    @ApiOperation("获取某日某群日赛赛文")
    public Result getGroupMatch(@PathVariable long groupId, @PathVariable Date holdDate){
        GroupMatch groupMatch = groupMatchMapper.selectGroupMatchByGroupIdAndDate(groupId,holdDate);
        if(groupMatch==null) {
            return Result.success("今日无赛文");
        } else {
            return Result.success("获取成功").setResult(groupMatch);
        }
    }
}
