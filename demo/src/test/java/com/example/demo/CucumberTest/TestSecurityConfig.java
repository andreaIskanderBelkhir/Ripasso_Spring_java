package com.example.demo.CucumberTest;

import com.example.demo.Security.UserUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.logout.HeaderWriterLogoutHandler;
import org.springframework.security.web.header.writers.ClearSiteDataHeaderWriter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
public class TestSecurityConfig  {
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        HeaderWriterLogoutHandler clearSiteData = new HeaderWriterLogoutHandler(new ClearSiteDataHeaderWriter(ClearSiteDataHeaderWriter.Directive.COOKIES));
        http.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authorize ->authorize.requestMatchers("/user").//hasAuthority("ADMIN")
                        permitAll()
                        .anyRequest().permitAll())
                .logout((logout)->logout.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                        .logoutSuccessUrl("/logout.done").deleteCookies("JSESSIONID")
                        .invalidateHttpSession(true)  )
                .formLogin(Customizer.withDefaults());


        // .rememberMe(remember->remember);

        return http.build() ;
    }

}