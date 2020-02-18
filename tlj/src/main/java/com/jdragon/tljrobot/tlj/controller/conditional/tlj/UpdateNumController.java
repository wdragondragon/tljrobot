package com.jdragon.tljrobot.tlj.controller.conditional.tlj;

import com.jdragon.tljrobot.tlj.service.TljService;
import com.jdragon.tljrobot.tljutils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * Create by Jdragon on 2020.01.14
 */
@Controller
@RequestMapping("/num")
@Api(tags = "上传字数（需登录）")
public class UpdateNumController {
    @Autowired
    TljService tljService;

    @GetMapping("/changeNum/{userId}")
    @ApiOperation("更改字数")
    @ResponseBody
    public Result changeNum(@PathVariable String userId, @RequestParam int num,
                             @RequestParam int rightNum,@RequestParam int misNum,
                             @RequestParam int dateNum,@RequestParam String numKey){
        if(!tljService.checkNumSecret(num, rightNum, misNum, dateNum, numKey)){
            return Result.error("密钥错误");
        }
        else if(tljService.changeNum(userId,num,rightNum,misNum,dateNum)) {
            return Result.success("更新成功");
        }else {
            return Result.error("更新失败");
        }
    }
}
