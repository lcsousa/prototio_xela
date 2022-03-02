package br.com.samsung.wms.latam.cellowmsestore.config.security;

import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import br.com.samsung.wms.latam.cellowmsestore.filter.security.AuthorizationCustomFilter;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	
	/*
	 * @Autowired private UserDetailsService userDetailsService;
	 */
	 
    
	String[] whiteList ={"/v1/auth/login"
			            , "/v1/auth/refreshToken"
						,"/h2-console"
						,"/v2/api-docs",
                        "/configuration/ui",
                        "/swagger-resources/**",
                        "/configuration/security",
                        "/swagger-ui.html",
                        "/webjars/**"};
	

	/*
	 * @Autowired public void configureAuthentication(AuthenticationManagerBuilder
	 * auth) throws Exception {
	 * auth.userDetailsService(this.userDetailsService).passwordEncoder(
	 * passwordEncoder()); }
	 */
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
				.authorizeRequests().antMatchers(HttpMethod.OPTIONS, "/**").permitAll()
				.antMatchers(whiteList).permitAll()
				.anyRequest().authenticated()
				.and().exceptionHandling().authenticationEntryPoint(authenticationEntryPoint());
		http.addFilterBefore(authenticationTokenFilterBean(),UsernamePasswordAuthenticationFilter.class);
		http.headers().cacheControl();
	}

	@Bean
	public AuthorizationCustomFilter authenticationTokenFilterBean() {
		return new AuthorizationCustomFilter();
	}
	
	@Bean
	SecurityExceptionHandleEntryPoint authenticationEntryPoint() {
		return new SecurityExceptionHandleEntryPoint();
	}


	/*
	 * @Bean RestAccessDeniedHandler accessDeniedHandler() { return new
	 * RestAccessDeniedHandler(); }
	 */

}
