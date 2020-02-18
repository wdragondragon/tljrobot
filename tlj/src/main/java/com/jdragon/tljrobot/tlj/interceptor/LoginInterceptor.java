package com.jdragon.tljrobot.tlj.interceptor;

import com.jdragon.tljrobot.tljutils.Local;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class LoginInterceptor implements HandlerInterceptor {
    //这个方法是在访问接口之前执行的，我们只需要在这里写验证登陆状态的业务逻辑，就可以在用户调用指定接口之前验证登陆状态了
    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler)throws Exception{
        String requestURI = request.getRequestURI();
//        System.out.println(requestURI);
        /*
           如果为允许访问的地址或携带已登录userId则通过
         */
        List allow = Arrays.asList("error","home","swagger-ui.html","webjars","swagger-resources","test","editor","ueditor","UEditorConfig","static","robot","tljVersion","fonts");
        List allow2 = Arrays.asList("img","css","js","png","jpg","txt","ico");
        String pattern = "(?<=/)(.*?)(?=/)|(?<=/)(.*)";
        Pattern r = Pattern.compile(pattern);
        Matcher m = r.matcher(requestURI);
        System.out.println(requestURI);
        String lastPath = requestURI.substring(requestURI.lastIndexOf("/")+1);
        if(requestURI.equals("/")){
//            System.out.println("放行home");
        }else if(allow2.contains(requestURI.substring(requestURI.lastIndexOf(".")+1))){
//            System.out.println("放行制定后缀文件");
        }else if(m.find()&&allow.contains(m.group())){
            System.out.println(allow.contains(m.group()));
        }else if(Local.isLogin(lastPath)){
//            System.out.println("放行登录用户" + lastPath);
        }else{
            System.out.println("拦截"+requestURI);
            request.getRequestDispatcher("/home/login").forward(request,response);
            return false;
        }
        return true;
    }
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable ModelAndView modelAndView) throws Exception {
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable Exception ex) throws Exception {
    }
}
