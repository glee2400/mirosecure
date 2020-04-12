package ca.on.gov.jus.micro.security.service;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * 
 * Spring AuthenticationManagerBuilder -> Configuration 
 * --> AuthenticationManager instance
 * In Memory or JDBC Authentication 
 * Add H2 in memory database, point Spring security point to H2 database
 * Spring security will create USER and AUTHORIZE tables in fresh database as default schema
 *
 */

/* To tell the Spring this is the configuration class */
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter{
	
	/* Use H2 database, but it could be MySQL, Oracle... etc.  */
	@Autowired
	DataSource dataSource;
	@Autowired
	UserDetailsService userDetailsService;
	
	
	/* --Set up Authentication-- */
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception{
		//Set your configuration on the auth object
		/*
		 * Version 1: in memory Authentication
		 * version 2: JDBC Authentication
		 * 
		auth.inMemoryAuthentication()
			.withUser("blah")
			.password("blah")
			.roles("USER")
			.and()
			.withUser("foo")
			.password("foo")
			.roles("ADMIN");
		*/
		
		/*
		 * Version 2: populate the database on the fly with default schema
		 * 
		auth.jdbcAuthentication()
			.dataSource(dataSource)
			.withDefaultSchema()
			.withUser(User.withUsername("user")
							.password("pass")
							.roles("USER")
				)
			.withUser(User.withUsername("admin")
					.password("pass")
					.roles("ADMIN")
				);
		*/
		
		/*
		 * Version 3: populate the database form SQL files
		 * 		   a: In-Memory DB H2 - on the fly
		 * 		   b: JDBC connection 
		auth.jdbcAuthentication()
			.dataSource(dataSource);
		*/
		
		/*
		 * Version 4: use MySQL
		 * 		Configure SpringSecurity to use userDeatilsService
		 * 		inside userDetailsService, it load user info. from JPA
		 */
		auth.userDetailsService(userDetailsService);
		
		
		/*
		 *   Or from a costumized table schema, not from the deault one
		 *   Or  configure the datasource database ( Oracle, Db2, MySQL) in the application.properties
		 *       spring.datasource.url=? 
		 *       spring.datasource.username=?
		 *       spring.datasource.password=?
		 *   
		 *   .usersByUsernameQuery("select username, password, enabled from my_users where username = ?")
		 *   .authoritiesByUsernameQuery("select username, authority from my_authorities where username = ?");
		 */

	}
	
	/* --Set up Authorization-- */
	@Override
	protected void configure(HttpSecurity http) throws Exception {

		http.authorizeRequests()
			//.antMatchers("/", "static/css", "static/js").permitAll()
			.antMatchers("/user").hasAnyRole("USER","ADMIN")
			.antMatchers("/admin").hasRole("ADMIN")
			.antMatchers("/").permitAll()
			.and().formLogin();
	}

	@Bean
	public PasswordEncoder getPasswordEncoder() {
		return NoOpPasswordEncoder.getInstance();
	}
}
