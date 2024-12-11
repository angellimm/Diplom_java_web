package com.diary.demo.Config;

import com.diary.demo.Service.MyUserDetailService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


import java.io.IOException;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    @Autowired
    private MyUserDetailService userDetailService;
    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .csrf(AbstractHttpConfigurer::disable) // CSRF disabled (consider security implications)
                .authorizeHttpRequests(registry -> {
                    registry.requestMatchers("/home", "/register", "/authenticate").permitAll(); // Public endpoints
                    registry.requestMatchers("/admin/**").hasRole("ADMIN"); // Restricted to ADMIN role
                    registry.requestMatchers("/user/**").hasRole("USER"); // Restricted to USER role
                    registry.anyRequest().authenticated(); // All other requests need to be authenticated
                })
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class) // Add JWT filter
                .formLogin(httpSecurityFormLoginConfigurer -> {
                    httpSecurityFormLoginConfigurer
                            .loginPage("/login") // Custom login page
                            .successHandler(new AuthenticationSuccessHandler()) // Custom success handler
                            .permitAll(); // Allow all to access login
                })
                .logout(logout -> {
                    logout.logoutUrl("/logout") // Configured signout URL
                            .logoutSuccessUrl("/home") // Redirect after successful logout
                            .invalidateHttpSession(true) // Invalidate session
                            .deleteCookies("JSESSIONID") // Remove session cookies
                            .permitAll(); // Allow all to access logout
                })
                .build();
    }


//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
//        return httpSecurity
//                .csrf(AbstractHttpConfigurer::disable)
//                .authorizeHttpRequests(registry -> {
//                 registry.requestMatchers("/home", "/register", "/authenticate").permitAll();
//                    registry.requestMatchers("/admin/**").hasRole("ADMIN");
//                    registry.requestMatchers("/user/**").hasRole("USER");
//                    registry.anyRequest().authenticated();
//                })
//                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
//                .formLogin(httpSecurityFormLoginConfigurer -> {
//                    httpSecurityFormLoginConfigurer
//                            .loginPage("/login")
//                            .successHandler(new AuthenticationSuccessHandler())
//                            .permitAll();
//                })
//                .logout(logout -> {
//                    logout.logoutUrl("/signout") // URL to log out
//                            .logoutSuccessUrl("/login?logout=true") // Redirect to login after logout
//                            .invalidateHttpSession(true) // Invalidate the session if you use sessions
//                            .deleteCookies("JSESSIONID") // Delete session cookies
//                            .permitAll();
//                });
//                .build();
//    }


    @Bean
    public UserDetailsService userDetailsService() {
        return userDetailService;
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailService);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    @Bean
    public AuthenticationManager authenticationManager() {
        return new ProviderManager(authenticationProvider());
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}