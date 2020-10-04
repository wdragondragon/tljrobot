package com.jdragon.tljrobot.tlj.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;

@Controller
@RequestMapping("/")@ApiIgnore
public class Upload {
    /**
     * 提取上传方法为公共方法
     * @param uploadDir 上传文件目录
     * @param file 上传对象
     * @throws Exception
     */
    private void executeUpload(String uploadDir, MultipartFile file) throws Exception
    {
        //文件后缀名
        System.out.println(file.getOriginalFilename());
        String suffix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("/")+1);
        //上传文件名
        String filename = file.getOriginalFilename();
        //服务器端保存的文件对象

        File serverFile = new File(uploadDir + filename);
        //将上传的文件写入到服务器端文件内
        file.transferTo(serverFile);
    }
    @GetMapping(value="/uploadCiku")
    public String loadCiku(){
        return "uploadCiku";
    }
    @PostMapping(value = "/uploadCiku")
    public void loadCiku(HttpServletResponse response, @RequestParam("file")MultipartFile file) throws IOException {
        String uploadDir;
//        if(TljApplication.isTest()) uploadDir = "D:/WEB/ciku/";
//        else
            uploadDir = "/var/java/编码文件/输入法编码/";
        upload(uploadDir,file);
        response.sendRedirect("/ciku/"+file.getOriginalFilename());
    }
    @GetMapping(value="/uploadImg")
    public String uploads(){
        return "uploadHeadImg";
    }
    @PostMapping(value = "/uploadImg")
    public String uploads(HttpServletRequest request, @RequestParam("file")MultipartFile file)
    {
        String uploadDir;
//        if(TljApplication.isTest()) uploadDir = "D:/WEB/";
//        else
            uploadDir = "images/";
        upload(uploadDir,file);

        return "showHeadImg";
    }
    public void upload(String uploadDir,MultipartFile file){
        try {
            //上传目录地址
            // 随意

//            String uploadDir= ResourceUtils.getURL("classpath:").getPath()+"/static/up/";
            System.out.println(uploadDir);
            //如果目录不存在，自动创建文件夹
            File dir = new File(uploadDir);
            if(!dir.exists())
            {
                dir.mkdir();
            }
            //遍历文件数组执行上传
            if(file != null) {
                //调用上传方法
                executeUpload(uploadDir, file);
            }
        }catch (Exception e)
        {
            //打印错误堆栈信息
            e.printStackTrace();
            System.out.println("失败");
        }
        System.out.println("成功");
    }
}
