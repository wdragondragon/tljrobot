package com.jdragon.tljrobot.tlj.test;

import com.alibaba.nacos.common.util.Md5Utils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jdragon.tljrobot.tlj.mappers.QQUnionMatchAchMapper;
import com.jdragon.tljrobot.tlj.mappers.QQUnionMatchMapper;
import com.jdragon.tljrobot.tlj.pojo.QQUnionMatch;
import com.jdragon.tljrobot.tlj.pojo.QQUnionMatchAch;
import com.jdragon.tljrobot.tlj.service.BestTypingService;
import com.jdragon.tljrobot.tljutils.DateUtil;
import com.jdragon.tljrobot.tljutils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

@RestController
@RequestMapping("/test")
@PropertySource("classpath:application.yml")
@Api(tags = "测试接口")
public class test {
    @Autowired
    QQUnionMatchMapper QQUnionMatchMapper;

    @Autowired
    QQUnionMatchAchMapper QQUnionMatchAchMapper;

    @Autowired
    BestTypingService bestTypingService;
    @Value("${server.secret}")
    String secret;
    @ApiIgnore
    @RequestMapping("/testDate")
    public String testDate() {
        return new DateUtil().now().toString();
    }

    @GetMapping("/bestTyping/{str}")
    @ApiOperation(value = "获取最佳编码详细json")
    public Result testBestTyping(@ApiParam(name = "str",value = "字符串")@PathVariable String str) {
        return Result.success("获取成功").setResult(bestTypingService.readyCreate(str));
    }
    @GetMapping("/shortCodes/{str}")
    @ApiOperation(value = "获取最佳编码")
    public Result testShortCodes(@ApiParam(name = "str",value = "字符串")@PathVariable String str) {
        return Result.success("获取成功").setResult(bestTypingService.readyCreate(str).getArticleCodes());
    }
    @GetMapping("/getCompetitionArticle")
    @ApiOperation(value = "获取今日联赛赛文")
    public Result testCompetitionArticle() {
        return Result.success("获取成功").
                setResult(new QQUnionMatch().selectById(DateUtil.now().toString()));
    }
    @GetMapping("/uploadAchievement/{username}/{userQQ}/{speed}/{keySpeed}/{keyLength}/{encrypt}")
    @ApiOperation(value = "上传成绩")
    public Result testUploadAchievement(@ApiParam(name = "username",value = "用户名")@PathVariable String username,
                                        @ApiParam(name = "userQQ",value = "用户QQ")@PathVariable long userQQ,
                                        @ApiParam(name = "speed",value = "速度") @PathVariable double speed,
                                        @ApiParam(name = "keySpeed",value = "击键") @PathVariable double keySpeed,
                                        @ApiParam(name = "keyLength",value = "码长")@PathVariable double keyLength,
                                        @ApiParam(name = "encrypt",value = "加密串") @PathVariable String encrypt){

        String systemEncrypt = Md5Utils.getMD5((speed+this.secret).getBytes());
        if(!systemEncrypt.equals(encrypt))return Result.error("密钥错误");
        QQUnionMatchAch QQUnionMatchAch = new QQUnionMatchAch(username,userQQ,speed,keySpeed,keyLength);
        if(QQUnionMatchAch.insert()){
            return Result.success("成绩上传成功");
        }else{
            return Result.error("成绩上传失败");
        }
    }
    @GetMapping("/getAllAchievement")
    @ApiOperation(value = "获取今日所有人成绩")
    public Result testGetAllAchievement(){
        return Result.success("获取成功")
                .setResult(QQUnionMatchAchMapper.selectList(new QueryWrapper<QQUnionMatchAch>()
                        .eq(QQUnionMatchAch.Def.SAIWEN_DATE, DateUtil.now().toString())));
    }
    @GetMapping("/test")
    public Result testTljMatch(){

        return Result.success("成功").setResult("");
    }
}