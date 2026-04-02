package com.servicios.sppp.back_end_sppp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
public class BackEndSpppApplication {

	public static void main(String[] args) {
		//soy la rama develop
		//soy la nueva api
		SpringApplication.run(BackEndSpppApplication.class, args);
	}

}
