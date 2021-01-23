package com.akfly.hzz;


import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import springfox.documentation.service.Parameter;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
@MapperScan("com.akfly.hzz.mapper")
@EnableCaching
@EnableSwagger2
//@EnableRedisHttpSession
public class HzzApplication {

	public static void main(String[] args) {
		SpringApplication.run(HzzApplication.class, args);
	}
	@Bean
	public Docket createRestApi() {
		//添加header参数
		ParameterBuilder ticketPar = new ParameterBuilder();
		List<Parameter> pars = new ArrayList<>();
		ticketPar.name("token").description("user token")
				.modelRef(new ModelRef("string")).parameterType("header")
				.required(true).build(); //header中的ticket参数非必填，传空也可以
		pars.add(ticketPar.build());    //根据每个方法名也知道当前方法在设置什么参数


		return new Docket(DocumentationType.SWAGGER_2)
				//.apiInfo(apiInfo())
				.select()
				//.apis(RequestHandlerSelectors.basePackage("com.springboot.test.springboot"))
				.paths(PathSelectors.any())
				.build()
				.globalOperationParameters(pars);
	}
}
