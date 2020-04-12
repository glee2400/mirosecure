package ca.on.gov.jus.micro.security;

import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter{
	
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception{
        
		auth.ldapAuthentication()
			.userDnPatterns("uid= {0},ou=people")
			.groupSearchBase("ou=groups")
			.contextSource()
			.url("ldap://localhost:8389/dc=springframework,dc=org")
			.and()
			.passwordCompare()
			//.passwordEncoder(new LdapShaPasswordEncoder()) //Old Demo Encoder, deprecated
			.passwordEncoder(new BCryptPasswordEncoder())    //New encoder from https://spring.io/guides/gs/authenticating-ldap/
			.passwordAttribute("userPassword");		
		
	}
	
	
	/* Override Authorization */
	@Override
	protected void configure(HttpSecurity http) throws Exception{
		http.authorizeRequests()
			.anyRequest().fullyAuthenticated()
			.and()
			.formLogin();
	}
	

	/**
	 * My method to fix 
	 * "o.s.l.c.support.AbstractContextSource    : Property 'userDn' not set - anonymous context will be used for read-write operations"
	 * Warn info in the server console log
	 * Other's code example: 
	 *
	 *
	    <security:authentication-manager>
	    	<security:ldap-authentication-provider 
	        	user-search-filter="(uid={0})"
	        	user-search-base="dc=company,dc=com">
	    	</security:ldap-authentication-provider>
		</security:authentication-manager>
		<security:ldap-server url="ldap://mail.company.com" />	
		<security:authentication-manager>
		    <security:authentication-provider ref="appAuthenticationProvider" />
		</security:authentication-manager>
	
		@Service("appAuthenticationProvider")
		public class AppAuthenticationProvider extends DaoAuthenticationProvider  {
		
		    private LdapAuthenticationProvider ldapProvider;
		
		    public AppAuthenticationProvider(){
		        DefaultSpringSecurityContextSource contextSource = new DefaultSpringSecurityContextSource("ldap://mail.company.com");
	            contextSource.afterPropertiesSet();  //***** Very important to fix the warning info *****
	
		        BindAuthenticator authenticator = new BindAuthenticator(contextSource);
		        authenticator.setUserSearch(new FilterBasedLdapUserSearch("dc=company,dc=com", "(uid={0})", contextSource));
		        ldapProvider = new LdapAuthenticationProvider(authenticator);
		    }
		
		    public Authentication authenticate(Authentication authRequest) throws AuthenticationException {
		        return ldapProvider.authenticate(authRequest);
		    }
		
		}
	
    */

}
