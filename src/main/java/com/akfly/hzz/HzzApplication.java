package com.akfly.hzz;


import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.CommandLineRunner;
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

import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
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
		ticketPar.name("token").description("user token").defaultValue("eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJhdWQiOiIxMDAwOSJ9.1U97a14qqsZdQT7zjI7anOk6SwZSlAoJEv8UjHVq1yk")
				.modelRef(new ModelRef("string")).parameterType("header")
				.required(true).build(); //header中的token参数非必填，传空也可以
		pars.add(ticketPar.build());    //根据每个方法名也知道当前方法在设置什么参数


		//.apiInfo(apiInfo())
		//.apis(RequestHandlerSelectors.basePackage("com.springboot.test.springboot"))
		Docket docket = new Docket(DocumentationType.SWAGGER_2)
				//.apiInfo(apiInfo())
				.select()
				//.apis(RequestHandlerSelectors.basePackage("com.springboot.test.springboot"))
				.paths(PathSelectors.any())
				.build()
				.globalOperationParameters(pars);
		//initdoc();
		return docket;
	}

	/*public void initdoc(){
		CommandLineRunner runner=new CommandLineRunner(){

			@Override
			public void run(String... args) throws Exception {
				URL remoteSwaggerFile = null;
				try {
					remoteSwaggerFile = new URL("http://localhost:8080/v2/api-docs");
				} catch (MalformedURLException e) {
					e.printStackTrace();
				}
				Path outputDirectory = Paths.get("src/docs/markdown/generated");

				Swagger2MarkupConfig config = new Swagger2MarkupConfigBuilder()
						.withMarkupLanguage(MarkupLanguage.MARKDOWN)
						.build();

				Swagger2MarkupConverter.from(remoteSwaggerFile)
						.withConfig(config)
						.build()
						.toFolder(outputDirectory);
			}

		};
	}*/


}
