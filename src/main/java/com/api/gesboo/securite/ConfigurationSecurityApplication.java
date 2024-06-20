package com.api.gesboo.securite;

import com.api.gesboo.service.UtilisateurService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.springframework.http.HttpMethod.*;

@Configuration
@EnableWebSecurity
public class ConfigurationSecurityApplication {

    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    JwtFilter jwtFilter;

    public ConfigurationSecurityApplication(BCryptPasswordEncoder bCryptPasswordEncoder, JwtFilter jwtFilter) {
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.jwtFilter = jwtFilter;
    }


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(
                        authorize ->
                                authorize
                                        .requestMatchers(POST,"/inscription").permitAll()
                                        .requestMatchers(POST,"/activation").permitAll()
                                        .requestMatchers(POST,"/connexion").permitAll()
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

                                        .requestMatchers(POST,"/ajouterAvis").permitAll()
                                        .requestMatchers(PUT,"/update/{id}").permitAll()
                                        .requestMatchers(DELETE,"/delete/{id}").permitAll()
                                        .requestMatchers(GET,"/book/{bookId}").permitAll()
                                        .requestMatchers(POST,"/reply/add").permitAll()
                                        .anyRequest().authenticated()

                )
                .sessionManagement(httpSecuritySessionManagementConfigurer ->
                        httpSecuritySessionManagementConfigurer.sessionCreationPolicy(SessionCreationPolicy.STATELESS)

                )
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                .build();

    }


    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public UserDetailsService userDetailsService(){
        return new UtilisateurService();
    }

    @Bean
    public AuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(this.userDetailsService());
        daoAuthenticationProvider.setPasswordEncoder(bCryptPasswordEncoder);
        return daoAuthenticationProvider;
    }
}
