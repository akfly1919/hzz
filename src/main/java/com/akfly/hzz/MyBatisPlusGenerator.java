package com.akfly.hzz;


/**
 * @program: hzz
 * @description:
 * @author: wangfei171
 * @create: 2021-01-18 19:07
 **/
import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
public class MyBatisPlusGenerator {

    //main函数
    public static void main(String[] args) {

        AutoGenerator autoGenerator = new AutoGenerator();

        //全局配置
        GlobalConfig gc = new GlobalConfig();

        //得到当前项目的路径
        String oPath = System.getProperty("user.dir");

        //生成文件输出根目录
        gc.setOutputDir(oPath + "/src/main/java");

        //生成完成后不弹出文件框
        gc.setOpen(false);

        //文件覆盖
        gc.setFileOverride(true);

        // 不需要ActiveRecord特性的请改为false
        gc.setActiveRecord(false);

        // XML 二级缓存
        gc.setEnableCache(false);

        // XML ResultMap
        gc.setBaseResultMap(true);

        // XML columList
        gc.setBaseColumnList(false);

        // 作者
        gc.setAuthor("wangfei");
        gc.setSwagger2(false);

        // 自定义文件命名，注意 %s 会自动填充表实体属性！
        gc.setControllerName("%sController");
        gc.setServiceName("%sService");
        gc.setServiceImplName("%sServiceImpl");
        gc.setMapperName("%sMapper");
        gc.setXmlName("%sMapper");
        gc.setEntityName("%sVo");
        autoGenerator.setGlobalConfig(gc);

        // 数据源配置
        DataSourceConfig dsc = new DataSourceConfig();

        //设置数据库类型
        dsc.setDbType(DbType.MYSQL);
        dsc.setUrl("jdbc:mysql://192.144.230.48:3306/hzzdev?useUnicode=true&useSSL=false&characterEncoding=utf8");
        dsc.setDriverName("com.mysql.jdbc.Driver");
        dsc.setUsername("root");
        dsc.setPassword("3eufds983d");
        autoGenerator.setDataSource(dsc);

        // 策略配置
        StrategyConfig strategy = new StrategyConfig();

        // 表名生成策略
        strategy.setNaming(NamingStrategy.underline_to_camel);

        // 需要生成的表
        strategy.setInclude(new String[]{"customerpickupdetail"});
        //strategy.setSuperServiceClass(null);
        //strategy.setSuperServiceImplClass(null);
        //strategy.setSuperMapperClass(null);
        strategy.setEntityLombokModel(true);

        //去除表前缀
        strategy.setTablePrefix("an_");
        //去除字段前缀
        strategy.setFieldPrefix("");
        autoGenerator.setStrategy(strategy);

        // 包配置
        PackageConfig pc = new PackageConfig();
        //父包路径
        pc.setParent("com.qianxian.api");
        pc.setController("controller");
        pc.setService("service");
        pc.setServiceImpl("service.impl");
        pc.setMapper("mapper");
        pc.setEntity("vo");
        pc.setXml("mapper");
        autoGenerator.setPackageInfo(pc);
        // 执行生成
        autoGenerator.execute();
    }
}


