package ca.on.gov.jus.micro.security.service;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import ca.on.gov.jus.micro.security.model.User;

/**
 * JPA methods Interface
 *
 */
public interface UserRepository extends JpaRepository<User, Integer>{
	
	Optional<User> findByUserName(String userName);

}
