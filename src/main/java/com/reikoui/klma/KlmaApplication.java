package com.reikoui.klma;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.reikoui.klma.dao")
public class KlmaApplication {

	public static void main(String[] args) {
		SpringApplication.run(KlmaApplication.class, args);
	}
}
