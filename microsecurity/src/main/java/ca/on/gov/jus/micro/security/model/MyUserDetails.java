package ca.on.gov.jus.micro.security.model;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class MyUserDetails  implements UserDetails{
	
	private String userName;
	private int id;
	private String password;
	private boolean active;
	private List<GrantedAuthority> authorities;
	

	public MyUserDetails() {}
	
	public MyUserDetails(String userName) {
		super();
		this.userName = userName;
	}

	/* Map db entity to the UserDetail object */
	public MyUserDetails(User user) {
		this.id = user.getId();
		this.userName = user.getUserName();
		this.password = user.getPassword();
		this.active = user.isActive();
		this.authorities = Arrays.stream(user.getRoles().split(","))
								.map(SimpleGrantedAuthority::new)
								.collect(Collectors.toList());
	}

	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		//Version 1; return hardcoded Authority
		//return Arrays.asList(new SimpleGrantedAuthority("ROLE_USER"));
		
		//Version 2: From JPA
		return authorities;
	}

	@Override
	public String getPassword() {
		//Version 1: set Temporary Hardcode here --
		//return "pass";
		
		//Version 2, From JPA
		return password;
	}

	@Override
	public String getUsername() {
		return this.userName;
	}
	
	@Override
	public boolean isEnabled() {
		return active;
	}

	//For other method, still hardcoded for now
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}
	@Override
	public boolean isAccountNonLocked() {
		return true;
	}
	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

}
