package br.fpu.guilherme.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configurers.provisioning.InMemoryUserDetailsManagerConfigurer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.servlet.configuration.EnableWebMvcSecurity;

@Configuration
@EnableWebMvcSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
			.antMatchers("/", "/home","/css/*", "/js/*", "/fonts/*", "/imagens/*","/bootstrap-3.3.5-dist/*","/login")
				.permitAll()
				.antMatchers("/movimentacao","/produto").hasRole("USER")
				
			.anyRequest()
				.authenticated()
			.and()
				.formLogin()
					.loginPage("/login")
					.permitAll()
			.and()
				.logout()
				.permitAll();
	}

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		InMemoryUserDetailsManagerConfigurer<AuthenticationManagerBuilder> inMemoryAuthentication = auth.inMemoryAuthentication();
			inMemoryAuthentication.withUser("admin").password("adminadmin").roles("USER");
			inMemoryAuthentication.withUser("leo").password("pass").roles("USER");
	}

}
