package com.jdragon.tljrobot.tlj.test;

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
@Api(tags = "词提接口")
public class test {

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
}