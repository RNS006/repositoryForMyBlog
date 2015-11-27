/**
 * This class authenticates user by comparing the password submitted in a 
 * UsernamePasswordAuthenticationToken against the one loaded by the UserDetailsService
 * On successful login, the the counter will be reset to 0. 
 * For unsuccessful login the counter will be incremented
 */
package com.org.coop.authentication;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

@Component("authenticationProvider")
public class CustomAuthenticationProvider extends DaoAuthenticationProvider {
	private static final Log log = LogFactory
			.getLog(CustomAuthenticationProvider.class);

	@Autowired
	public CustomAuthenticationProvider(UserDetailsService customUserDetailsService) {
		this.setUserDetailsService(customUserDetailsService);
	}

	@Override
	public Authentication authenticate(Authentication authentication)
			throws AuthenticationException {
		// Could assert pre-conditions here, e.g. rate-limiting
		// and throw a custom AuthenticationException if necessary
		try {
			
			Authentication auth = super.authenticate(authentication);
			log.info("Reset counter");
			System.out.println("Reset Counter");
			return auth;
		} catch (BadCredentialsException e) {
			// Will throw a custom exception if too many failed logins have
			// occurred
			log.error("Bad credential: ", e);
			System.out.println("Bad credential");
			throw e;
		}
	}
}