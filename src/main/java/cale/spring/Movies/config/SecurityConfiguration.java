package cale.spring.Movies.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http
                .antMatcher("/**").authorizeRequests()
                .antMatchers("/","/h2-console/**","/about","/styles/**","/scripts/**","/imgs/**").permitAll()
                .anyRequest().authenticated().and().oauth2Login();
        http.csrf().disable();
        http.headers().frameOptions().disable();
    }

}
