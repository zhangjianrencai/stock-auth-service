package com.stock.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.filter.CorsFilter;

import com.stock.entity.UserType;
import com.stock.security.JwtAccessDeniedHandler;
import com.stock.security.JwtAuthenticationEntryPoint;
import com.stock.security.jwt.JWTConfigurer;
import com.stock.security.jwt.TokenProvider;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	
	private final Logger log = LoggerFactory.getLogger(WebSecurityConfig.class);

	private final TokenProvider tokenProvider;
	private final CorsFilter corsFilter;
	private final JwtAuthenticationEntryPoint authenticationErrorHandler;
	private final JwtAccessDeniedHandler jwtAccessDeniedHandler;

	public WebSecurityConfig(TokenProvider tokenProvider, CorsFilter corsFilter,
			JwtAuthenticationEntryPoint authenticationErrorHandler, JwtAccessDeniedHandler jwtAccessDeniedHandler) {
		this.tokenProvider = tokenProvider;
		this.corsFilter = corsFilter;
		this.authenticationErrorHandler = authenticationErrorHandler;
		this.jwtAccessDeniedHandler = jwtAccessDeniedHandler;
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Override
	public void configure(WebSecurity web) {
		web.ignoring()
			.antMatchers(HttpMethod.OPTIONS, "/**");
	}

	@Override
	protected void configure(HttpSecurity httpSecurity) throws Exception {
		httpSecurity
			// we don't need CSRF because our token is invulnerable
			.csrf().disable()

			.addFilterBefore(corsFilter, UsernamePasswordAuthenticationFilter.class)

			.exceptionHandling().authenticationEntryPoint(authenticationErrorHandler)
			.accessDeniedHandler(jwtAccessDeniedHandler)

			// enable h2-console
			.and().headers().frameOptions().sameOrigin()

			// create no session
			.and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)

			// ignore some api auth
			.and().authorizeRequests()
			.antMatchers("/authenticate").permitAll()
			.antMatchers("/regist").permitAll()

			// role validate for special api
			.antMatchers("/user/*").hasAuthority(UserType.USER.name())
			.antMatchers("/admin/*").hasAuthority(UserType.ADMIN.name())

			.anyRequest().authenticated()

			.and().apply(securityConfigurerAdapter());
	}

	private JWTConfigurer securityConfigurerAdapter() {
		return new JWTConfigurer(tokenProvider);
	}
}
