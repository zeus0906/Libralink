package com.api.gesboo.securite;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.POST;

@Configuration
@EnableWebSecurity
public class ConfigurationSecuriteApplication {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return
                httpSecurity
                        .csrf(AbstractHttpConfigurer :: disable)
                        .authorizeHttpRequests(
                                authorize -> authorize
                                        .requestMatchers(POST,"/inscription").permitAll()
                                        .requestMatchers(POST,"/activation").permitAll()
                                        .requestMatchers(POST,"books/{isbn}").permitAll()
                                        .requestMatchers(POST,"/{isbn}/collection").permitAll()

                                        .requestMatchers(GET,"/{isbn}").permitAll()
                                        .requestMatchers(GET,"/listBooks").permitAll()
                                        .requestMatchers(GET,"/search/").permitAll()
                                        .requestMatchers(GET,"/listBooks").permitAll()
                                        .requestMatchers(GET,"/listBooksWithoutCollections").permitAll()
                                        .requestMatchers(GET,"/search/author").permitAll()
                                        .requestMatchers(GET,"/search/isbn").permitAll()
                                        .requestMatchers(GET,"/search/title").permitAll()
                                        .requestMatchers(GET,"/livre/{isbn}").permitAll()
                                        .anyRequest().authenticated()
                        ).build();
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
