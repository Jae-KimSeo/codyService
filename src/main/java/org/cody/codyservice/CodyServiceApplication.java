package org.cody.codyservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class CodyServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(CodyServiceApplication.class, args);
	}

}
