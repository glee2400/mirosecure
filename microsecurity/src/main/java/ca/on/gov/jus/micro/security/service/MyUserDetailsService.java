package ca.on.gov.jus.micro.security.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import ca.on.gov.jus.micro.security.model.MyUserDetails;
import ca.on.gov.jus.micro.security.model.User;

@Service
public class MyUserDetailsService implements UserDetailsService{
	
	@Autowired
	UserRepository userRepository;
	
	@Override
	public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException{
		
		//Some logic here, does not have to be JPA
		//return new MyUserDetails(userName);
		
		//Or invoke JPA methods
		Optional<User> user = userRepository.findByUserName(userName);
		
		//If user is not found from JPA
		user.orElseThrow(() -> new UsernameNotFoundException("Not Found: "+userName));
		
		return user.map(MyUserDetails::new).get();
		
	}

}
