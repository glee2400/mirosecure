package ca.on.gov.jus.micro.security.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import ca.on.gov.jus.micro.security.model.AuthenticationRequest;
import ca.on.gov.jus.micro.security.model.AuthenticationResponse;
import ca.on.gov.jus.micro.security.service.MyUserDetailsService;
import ca.on.gov.jus.micro.security.util.JwtUtil;

/* !!! --If Not a Restful Controller-- !!!
 * 
 * If use @Controller
 * The method need add ResponseBody,  
 * @RestController = @Controller + @ResponseBody
 * In this Controller File, if I remove @ResponseBody for regular Controller, it will give error: 
 * 					There was an unexpected error (type=Not Found, status=404).
 * 
 * So I changed it to @RestController
 */
@RestController
public class HelloResoruce {

	@Autowired
	private AuthenticationManager authenticationManager;
	@Autowired
	private MyUserDetailsService userDetailsService;
	@Autowired
	private JwtUtil jwtTokenUtil;
	
	
	@RequestMapping ({ "/hello" })
	public String hello() {
		System.out.println("Hit me!");
		return "Hello World!";
	}
	
	/*
	 * Use JWT Token do Authentication
	 */
	@RequestMapping(value = "/authenticate", method = RequestMethod.POST)
	public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest) throws Exception {

		try {
			/* User AuthenticaitonManager to Authenticate the Request*/
			authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword())
			);
		}
		catch (BadCredentialsException e) {
			/* If not Authenticated, throw Exception */
			throw new Exception("Incorrect username or password", e);
		}


		/* Fetch user from UserDetailService */
		final UserDetails userDetails = userDetailsService
				.loadUserByUsername(authenticationRequest.getUsername());

		/* Use JWT Util to get JWT token outof UserDetails */
		final String jwt = jwtTokenUtil.generateToken(userDetails);

		/* Create authentication HTTP response with Token */
		return ResponseEntity.ok(new AuthenticationResponse(jwt));
	}
	
	
	
}
