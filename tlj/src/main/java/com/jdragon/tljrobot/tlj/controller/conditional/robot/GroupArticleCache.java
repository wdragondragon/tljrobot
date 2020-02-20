package com.jdragon.tljrobot.tlj.controller.conditional.robot;

import com.jdragon.tljrobot.tlj.dto.SendRobotMessageDto;
import com.jdragon.tljrobot.tlj.pojo.Article;
import com.jdragon.tljrobot.tlj.pojo.User;
import com.jdragon.tljrobot.tljutils.Local;
import com.jdragon.tljrobot.tljutils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;

/**
 * @author 10619
 */
@RestController
@RequestMapping("/robot/groupArticleCache")
@Api(tags = "网络载文")
public class GroupArticleCache {
    public static HashMap<Long,Article> articleHashMap = new HashMap<>();
    @PostMapping("/uploadArticle")
    @ApiOperation("保存机器人收集的发文")
    public Result uploadArticle(@RequestParam String groupId, @RequestBody Article article){
        articleHashMap.put(Long.valueOf(groupId),article);
        return Result.success("上传成功");
    }
    @PostMapping("/getArticle")
    @ApiOperation("跟打器获取缓存的发文")
    public Result uploadArticle(@RequestParam String groupId){
        return Result.success("获取成功").setResult(articleHashMap.get(Long.valueOf(groupId)));
    }
    @PostMapping("/sendRobotArticle")
    @ApiOperation("收集跟打器发文且机器人分享发文到群")
    public Result sendRobotArticle(@RequestBody SendRobotMessageDto sendRobotMessageDto){
        Article article = new Article(sendRobotMessageDto.getParagraph(),sendRobotMessageDto.getTitle(),sendRobotMessageDto.getContent());
        articleHashMap.put(sendRobotMessageDto.getGroupId(),article);
        String username = ((User) Local.getSession(sendRobotMessageDto.getToken())).getUsername();
        try{
            String message = URLEncoder.encode(article.getTitle() +
                    System.lineSeparator()+article.getContent() +
                    System.lineSeparator()+"-----"+"第"+ article.getId() +"段"+"-来自"+username,"utf8");
            getRobotHttpSendMessage(message,sendRobotMessageDto.getGroupId());
            return Result.success("发送成功");
        }catch(Exception e){
            e.printStackTrace();
            return Result.success("发送失败");
        }
    }
    @PostMapping("/sendRobotAch")
    @ApiOperation("跟打器发送成绩")
    public Result sendRobotAch(@RequestBody SendRobotMessageDto sendRobotMessageDto){
        String username = ((User) Local.getSession(sendRobotMessageDto.getToken())).getUsername();
        try{
            String message =  URLEncoder.encode(sendRobotMessageDto.getContent()+" 来自"+username,"utf8");
            getRobotHttpSendMessage(message,sendRobotMessageDto.getGroupId());
            return Result.success("发送成功");
        }catch (Exception e){
            e.printStackTrace();
            return Result.success("发送失败");
        }
    }
    private void getRobotHttpSendMessage(String message,long groupId) throws IOException {
        String URL = "http://127.0.0.1:5700/send_group_msg?message=" + message + "&group_id="+groupId;
        System.out.println(URL);
        java.net.URL url = new URL(URL);
        URLConnection urlcon = url.openConnection();
        InputStreamReader in = new InputStreamReader(urlcon.getInputStream(), StandardCharsets.UTF_8);
        BufferedReader bufferRead = new BufferedReader(in);
    }
}
