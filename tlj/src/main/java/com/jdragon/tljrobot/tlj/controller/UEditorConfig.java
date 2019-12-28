package com.jdragon.tljrobot.tlj.controller;

import com.jdragon.tljrobot.tlj.ueditor.ActionEnter;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

@RestController
@RequestMapping
public class UEditorConfig {
    @RequestMapping("/UEditorConfig")
    @ResponseBody
    public void EDitorConfig(HttpServletRequest request, HttpServletResponse response){
        response.setContentType("application/json");
        String rootPath = this.getClass().getResource("/").getPath();
        try{
            String exec = new ActionEnter(request,rootPath).exec();
            System.out.println(exec);
            PrintWriter printWriter = response.getWriter();
            printWriter.write(exec);
            printWriter.flush();
            printWriter.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
