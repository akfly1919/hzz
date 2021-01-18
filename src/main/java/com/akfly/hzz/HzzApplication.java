package com.akfly.hzz;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.akfly.hzz.mapper")
public class HzzApplication {

	public static void main(String[] args) {
		SpringApplication.run(HzzApplication.class, args);
	}

}
