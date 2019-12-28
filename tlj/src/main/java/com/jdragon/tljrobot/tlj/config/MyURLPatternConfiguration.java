//package com.jdragon.tljrobot.tlj.config;
//
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.http.converter.HttpMessageConverter;
//import org.springframework.http.converter.StringHttpMessageConverter;
//import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
//import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
//
//import java.nio.charset.Charset;
//import java.util.List;
//
//@Configuration
//public class MyURLPatternConfiguration extends WebMvcConfigurationSupport {
//    @Override
//    public void addResourceHandlers(ResourceHandlerRegistry registry) {
////        registry.addResourceHandler("/fileData/**").addResourceLocations("file:D:/WEB/");
//        registry.addResourceHandler("/**").addResourceLocations("classpath:/static/");
//        registry.addResourceHandler("/ciku/**").addResourceLocations("file:/var/java/编码文件/输入法编码/");
//        registry.addResourceHandler("/images/**").addResourceLocations("file:/var/java/images/");
//        super.addResourceHandlers(registry);
//    }
//    @Bean
//    public HttpMessageConverter<String> responseBodyConverter(){
//        return new StringHttpMessageConverter(Charset.forName("UTF-8"));
//    }
//    @Override
//    public void configureMessageConverters(List<HttpMessageConverter<?>> converterList){
//        converterList.add(responseBodyConverter());
//        addDefaultHttpMessageConverters(converterList);
//    }
//    @Override
//    public void configureContentNegotiation(ContentNegotiationConfigurer configurer){
//        configurer.favorPathExtension(false);
//    }
//}