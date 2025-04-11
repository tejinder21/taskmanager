package com.example.taskmanager;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;

@Configuration
public class SecurityConfig {

    @Autowired
    private UserDetailsService userDetailsService;

    @Bean
    public SecurityFilterChain configure(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/css/**", "/signup", "/saveuser", "/error", "/h2-console/**").permitAll()  // Lisää H2 Console pääsy ilman kirjautumista
                .anyRequest().authenticated()  // Muu liikenne vaatii kirjautumisen
            )
            .formLogin(form -> form
                .loginPage("/login")  // Määritellään kirjautumissivu
                .defaultSuccessUrl("/tasklist", true)  // Kirjautumisen jälkeen siirrytään tasklist-sivulle
                .permitAll()  // Sallitaan kirjautumissivulle pääsy
            )
            .logout(logout -> logout.permitAll());  // Sallitaan uloskirjautuminen

        http.csrf(csrf -> csrf.ignoringRequestMatchers("/h2-console/**")); // Poistaa CSRF-suojauksen H2 Consolelta

        // H2 Consolein avaaminen samassa ikkunassa
        http.headers(headers -> headers.frameOptions(frameOptions -> frameOptions.sameOrigin()));

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();  // Varmistaa, että BCryptPasswordEncoder on määritelty oikein
    }

    @Bean
    public AuthenticationManagerBuilder authenticationManagerBuilder(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder builder = http.getSharedObject(AuthenticationManagerBuilder.class);
        builder.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
        return builder;
    }
}
