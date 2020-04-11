package ca.on.gov.jus.micro.security;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Spring Boot security
 * OAuth + JWT
 * Defaut "user" with random password; or configured in the application.properties file
 * 
*/

@SpringBootApplication
public class MicrosecurityApplication {

	public static void main(String[] args) {
		SpringApplication.run(MicrosecurityApplication.class, args);
	}

}
