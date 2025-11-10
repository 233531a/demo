package com.example.demo.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {

    @Bean
    public UserDetailsService userDetailsService(UserRepository repo) {
        return username -> repo.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        // статика и страницы входа/регистрации — всем
                        .requestMatchers("/css/**", "/js/**", "/images/**").permitAll()
                        .requestMatchers("/", "/login", "/register").permitAll()

                        // Пользователь (USER) видит список проектов (GET), но не может править
                        .requestMatchers(HttpMethod.GET, "/projects", "/projects/").hasAnyRole("USER","ADMIN")
                        // Страницы/действия редактирования — только ADMIN
                        .requestMatchers(HttpMethod.GET, "/projects/edit/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.POST, "/projects/add").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.POST, "/projects/edit/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.POST, "/projects/delete/**").hasRole("ADMIN")

                        .anyRequest().authenticated()
                )
                .formLogin(login -> login
                        .loginPage("/login")
                        .defaultSuccessUrl("/projects", true)
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/")
                        .permitAll()
                )
        // Оставляем CSRF включённым — формы ниже получают токен
        ;
        return http.build();
    }
}
