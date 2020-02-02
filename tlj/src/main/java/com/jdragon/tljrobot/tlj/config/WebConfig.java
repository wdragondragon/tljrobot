package com.jdragon.tljrobot.tlj.config;

import com.jdragon.tljrobot.tlj.interceptor.LoginInterceptor;
import com.jdragon.tljrobot.tljutils.SystemUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
@PropertySource("classpath:application.yml")
public class WebConfig extends WebMvcConfigurationSupport{
    /*
    swagger-ui配置
     */
    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.jdragon.tljrobot.tlj"))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("TLJ API 文档")
                .description("TLJ API 网关接口")
                .version("1.0.0")
                .build();
    }
    @Override
    protected void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/**").addResourceLocations("classpath:/static/");
        registry.addResourceHandler("swagger-ui.html")
                .addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");
        if(SystemUtil.isWindows()) {
            registry.addResourceHandler("/static/UEditor/**")
                    .addResourceLocations("file:D:/WEB/UEditor/");
            registry.addResourceHandler("/static/MEditor/**")
                    .addResourceLocations("file:D:/WEB/MEditor/");
            registry.addResourceHandler("/static/tlj/**")
                    .addResourceLocations("file:D:/WEB/tlj/");
        }
        else {
            registry.addResourceHandler("/static/UEditor/**")
                    .addResourceLocations("file:/var/java/UEditor/");
            registry.addResourceHandler("/static/MEditor/**")
                    .addResourceLocations("file:/var/java/MEditor/");
            registry.addResourceHandler("/static/tlj/**")
                    .addResourceLocations("file:/var/java/tlj/");
        }
        super.addResourceHandlers(registry);
    }

    @Override
    protected void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }
    //拦截器
    @Autowired
    LoginInterceptor loginInterceptor;
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // addPathPatterns("/**") //表示拦截所有的请求，
        // excludePathPatterns("/login", "/register") //表示除了登陆与注册之外，因为登陆注册不需要登陆也可以访问
        registry.addInterceptor(loginInterceptor).addPathPatterns("/**").excludePathPatterns("/login", "/home");
//        super.addInterceptors(registry);    //较新Spring Boot的版本中这里可以直接去掉，否则会报错
    }


}