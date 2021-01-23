package com.akfly.hzz;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@MapperScan("com.akfly.hzz.mapper")
@EnableCaching
@EnableSwagger2
//@EnableRedisHttpSession
public class HzzApplication {

	public static void main(String[] args) {
		SpringApplication.run(HzzApplication.class, args);
	}

}
