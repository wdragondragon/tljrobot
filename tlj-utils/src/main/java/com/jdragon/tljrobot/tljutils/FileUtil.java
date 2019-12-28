package com.jdragon.tljrobot.tljutils;

import com.alibaba.fastjson.JSONObject;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.UUID;

public class FileUtil {

    /**
     * 图片上传 返回editor要求的json串
     * @return JSONObject
     */
    public static JSONObject uploadFileReturnUrl(HttpServletRequest request, String filePath, MultipartFile file ,String prefixUrl) {

        // 获取完整的文件名
        String trueFileName = file.getOriginalFilename();
        // 获取文件后缀名
        String suffix = trueFileName.substring(trueFileName.lastIndexOf("."));
//            // 生成新文件的名字
        String fileName = UUID.randomUUID() + DateUtil.nowStringToS() + suffix;
//            // 获取项目地址
        String itemPath = getItemPath(request);

        // 判断当前要上传的路径是否存在
        File targetFile = new File(filePath, fileName);
        if (!targetFile.exists()) {
            targetFile.getParentFile().mkdirs();
        }

        //保存文件
        try {
            file.transferTo(targetFile);
        } catch (Exception e) {
            e.printStackTrace();
        }

        JSONObject res = new JSONObject();
        // 图片上传后地址
        res.put("url", itemPath + prefixUrl +fileName); ///图片地址和上传后的文件名
        // 图片上传的状态 1成功0失败
        res.put("success", 1);
        // 图片上传回传的信息
        res.put("message", "upload success!");

        return res;
    }
    public static String getItemPath(HttpServletRequest request){

        String scheme = request.getScheme(); // 获取链接协议，http
        String serverName = request.getServerName(); // 获取服务器名称 localhost
        int serverPort = request.getServerPort(); // 获取端口 8080
        String path = scheme+"://"+serverName+":"+serverPort;
        return path;
    }

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
}
