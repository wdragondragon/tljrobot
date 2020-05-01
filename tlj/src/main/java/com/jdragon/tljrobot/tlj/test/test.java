package com.jdragon.tljrobot.tlj.test;

import com.alibaba.nacos.common.util.Md5Utils;
import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.jdragon.tljrobot.tlj.service.BestTypingService;
import com.jdragon.tljrobot.tljutils.DateUtil;
import com.jdragon.tljrobot.tljutils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.junit.Test;
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
        return DateUtil.now().toString();
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
    @Test
    public void mdt(){
        System.out.println(Md5Utils.getMD5("mhs3210".getBytes()));
        String str = "21342";
        str = str.substring(0, Math.min(str.length(), 10));
        System.out.println(str);
    }

    @Test
    public void testMp(){
        //1.全局配置
        GlobalConfig config = new GlobalConfig();
        config.setActiveRecord(true)//是否支持AR模式
                .setAuthor("Jdragon")//作者
                .setOutputDir("D:\\workspaces\\testMp")//生成路径
                .setFileOverride(true)//文件覆盖
                .setIdType(IdType.AUTO)//逐渐策略
                .setServiceName("%sService")//设置生成的service接口名字格式
                .setBaseResultMap(true)
                .setBaseColumnList(true);
        //2.数据源配置
        DataSourceConfig dataSourceConfig = new DataSourceConfig();
        dataSourceConfig.setDbType(DbType.MYSQL)
                        .setDriverName("com.mysql.cj.jdbc.Driver")
                        .setUrl("jdbc:mysql://localhost:3306/tljrobot?serverTimezone=Asia/Shanghai&useUnicode=true&characterEncoding=UTF-8")
                        .setUsername("root")
                        .setPassword("951753");
        //3. 策略配置
        StrategyConfig strategyConfig = new StrategyConfig();
        strategyConfig.setCapitalMode(true)//全局大写
                .setNaming(NamingStrategy.underline_to_camel)
                .setTablePrefix("");
//                .setInclude("tbl_employee");//生成的表
        //4.包名策略配置
        PackageConfig packageConfig = new PackageConfig();
        packageConfig.setParent("src.main.java.com.jdragon.testMp")
                .setMapper("mapper")
                .setService("service")
                .setController("controller")
                .setServiceImpl("service.impl")
                .setEntity("entity")
                .setXml("mapper");
        //5.整合配置
        AutoGenerator autoGenerator = new AutoGenerator();
        autoGenerator.setGlobalConfig(config)
                .setDataSource(dataSourceConfig)
                .setStrategy(strategyConfig)
                .setPackageInfo(packageConfig);

        //6.执行
        autoGenerator.execute();
    }
}