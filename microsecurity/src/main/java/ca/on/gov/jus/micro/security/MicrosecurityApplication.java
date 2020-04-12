package ca.on.gov.jus.micro.security;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import ca.on.gov.jus.micro.security.service.UserRepository;

/**
 * Spring Boot security
 * OAuth + JWT
 * Default "user" with random password; or configured in the application.properties file
 * Enable JPA with UserRepository interface
 * 
*/

@SpringBootApplication
@EnableJpaRepositories(basePackageClasses = UserRepository.class)
public class MicrosecurityApplication {

	public static void main(String[] args) {
		SpringApplication.run(MicrosecurityApplication.class, args);
	}

}
