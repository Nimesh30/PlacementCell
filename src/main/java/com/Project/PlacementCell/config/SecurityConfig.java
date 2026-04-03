package com.Project.PlacementCell.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;  
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {
    private final JwtFilter jwtFilter;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }   

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                .csrf(csrf -> csrf.disable())
//                .cors(httpSecurityCorsConfigurer -> {
//                })
//                .formLogin(form -> form.disable())
//                .httpBasic(basic -> basic.disable())

                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )

                .authorizeHttpRequests(auth -> auth
                                //authetication for student
//                        .requestMatchers("/api/auth/**").permitAll()
                                //authentication for admin
                                .requestMatchers("api/auth/**").permitAll()
                                .requestMatchers("/admin/login").permitAll()

                                .requestMatchers("/admin/**").hasRole("ADMIN")
                                .requestMatchers("/students/**").hasRole("STUDENT")
                                .requestMatchers("/api/auth/change-password").permitAll()
                                .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll() //
                                // 🔒 all other APIs require token
                                //      .anyRequest().permitAll()
                                .anyRequest().authenticated()
                )
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }


}