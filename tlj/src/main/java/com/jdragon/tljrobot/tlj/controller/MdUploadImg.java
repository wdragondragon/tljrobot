package com.jdragon.tljrobot.tlj.controller;

import com.alibaba.fastjson.JSONObject;
import com.jdragon.tljrobot.tljutils.FileUtil;
import com.jdragon.tljrobot.tljutils.SystemUtil;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.beans.factory.annotation.Value;
import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/static")
@PropertySource("classpath:application.yml")
public class MdUploadImg {
    @Value("${editor.windowsPath}")
    String windowsPath;
    @Value("${editor.linuxPath}")
    String linuxPath;
    @Value("${editor.MdPrefixUrl}")
    String MdPrefixUrl;
    @PostMapping("/uploadImg")
    @ResponseBody
    public JSONObject uploadImg(@RequestParam(value = "editormd-image-file", required = false) MultipartFile file, HttpServletRequest request) {
        // 使用自定义的上传路径
        String path;
        if(SystemUtil.isWindows())
            path = this.windowsPath;
        else
            path = this.linuxPath;
        // 调用上传图片的方法
        JSONObject res = FileUtil.uploadFileReturnUrl(request,path,file,MdPrefixUrl);
        System.out.println(res.toString());
        return res;
    }
    @GetMapping("/uploadImg")
    @ResponseBody
    public String d(){
        return "sss";
    }
}
