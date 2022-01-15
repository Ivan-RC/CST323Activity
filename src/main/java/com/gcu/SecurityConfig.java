package com.gcu;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.gcu.business.SecurityBusinessService;

//This class will be used to manage security, and is required by spring security
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter{
	
	@Autowired
	private SecurityBusinessService service;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Bean
	BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	/**
	 * used to write security rules for the application
	 * @param HttpSecurity - used to access methods which will help us write those rules
	 * @return None
	 */
	@Override
	protected void configure(HttpSecurity http) throws Exception{
		//Spring security rules
		http.csrf().disable()
			.httpBasic()
				.and()
				.authorizeRequests()
				.antMatchers("/service/**", "/displayOauthCode").authenticated()
				.and()
			.authorizeRequests()
				.antMatchers("/", "/login/**", "/register/**").permitAll()
				.anyRequest().authenticated()
				.and()
			.formLogin()
				.loginPage("/login/")
				.usernameParameter("username")
				.passwordParameter("password")
				.permitAll()
				.defaultSuccessUrl("/blog/viewMine", true)
				.and()
			.logout()
				.logoutUrl("/logout")
				.invalidateHttpSession(true)
				.clearAuthentication(true)
				.permitAll()
				.logoutSuccessUrl("/");
	}
	
	/**
	 * This method is used to determine how users will be authenticated 
	 * @param auth - used to access user details method
	 * @return None
	 */
	@Autowired
	public void configure(AuthenticationManagerBuilder auth) throws Exception {
		//This is how the user is authenticated 
		auth.userDetailsService(service).passwordEncoder(passwordEncoder);
	}
}
