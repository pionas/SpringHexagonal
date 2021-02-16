package info.pionas.rental.application.password;

import lombok.extern.java.Log;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
@Order(1)
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
@Log
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Bean
    @SuppressWarnings("checkstyle:MagicNumber")
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(10);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.anonymous().principal("guest").authorities("GUEST_ROLE");
        http.cors().and().csrf().disable();
    }
}
