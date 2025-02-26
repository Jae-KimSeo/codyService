package org.cody.codyservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties
public class CodyServiceApplication {	

	public static void main(String[] args) {
		SpringApplication.run(CodyServiceApplication.class, args);
	}

}
