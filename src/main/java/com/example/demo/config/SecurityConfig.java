package com.example.demo.config;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import javax.crypto.spec.SecretKeySpec;

@Configuration
@EnableWebSecurity
@FieldDefaults(level = AccessLevel.PRIVATE)
@EnableMethodSecurity
public class SecurityConfig {
    @Value("${jwt.signerKey}")
    private String signerKey;
    private final String[] PUBLIC_URLS = {"/auth/token", "/auth/introspect","/auth/register","/token"};
    private final String[] PRIVATE_URLS = {"/users","worktime/checkin"};
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.authorizeHttpRequests(request ->
                        request.requestMatchers(HttpMethod.POST, PUBLIC_URLS).permitAll()
//                        .requestMatchers("/favicon.ico").permitAll()
                                .requestMatchers(HttpMethod.GET,"/loginui").permitAll()
                                .requestMatchers(HttpMethod.GET,"/members").permitAll()
                                .requestMatchers(HttpMethod.GET,"/members/create").permitAll()
                                .requestMatchers(HttpMethod.POST,"/members/create").permitAll()
                                .requestMatchers(HttpMethod.GET,"members/edit/**").permitAll()
                                .requestMatchers(HttpMethod.POST,"members/edit/**").permitAll()
                                .requestMatchers(HttpMethod.GET,"/departments").permitAll()
                                .requestMatchers(HttpMethod.GET,"/departments/**").permitAll()
                                .requestMatchers(HttpMethod.POST,"/departments/**").permitAll()
                                .requestMatchers(HttpMethod.GET,"/worktimes").permitAll()
                                .requestMatchers(HttpMethod.GET,"/worktimes/**").permitAll()
                                .requestMatchers(HttpMethod.POST,"/worktimes/**").permitAll()
                                .requestMatchers(HttpMethod.GET,"/static/**", "/js/**", "/css/**", "/images/**").permitAll()
                                .requestMatchers(HttpMethod.GET,"/logout").permitAll()
                                .requestMatchers(HttpMethod.GET,"/members/delete/**").permitAll()
                                .anyRequest().authenticated())
                .logout(logout ->
                        logout.logoutUrl("/logout") // Endpoint để xử lý logout
                                .logoutSuccessUrl("/loginui?logout") // Chuyển hướng sau khi logout thành công
                                .invalidateHttpSession(true) // Xóa session
                                .deleteCookies("token") // Xóa cookie token (nếu có)
                                .permitAll() // Cho phép tất cả người dùng truy cập
                );

        httpSecurity.oauth2ResourceServer(auth2 ->
                auth2.jwt(jwtConfigurer -> jwtConfigurer.decoder(jwtDecoder())
                                .jwtAuthenticationConverter(jwtAuthenticationConverter()))
                        .authenticationEntryPoint(new JwtAuthenticationEntryPoint()));
        httpSecurity.csrf(AbstractHttpConfigurer::disable);
        httpSecurity.cors(cors -> cors.configurationSource(corsConfigurationSource()));
        return httpSecurity.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.addAllowedOrigin("*");
        configuration.addAllowedMethod("*");
        configuration.addAllowedHeader("*");
        configuration.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource source = new
                UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean
    protected JwtDecoder jwtDecoder() {
        SecretKeySpec signingKey = new SecretKeySpec(signerKey.getBytes(), "HS512");
        return NimbusJwtDecoder
                .withSecretKey(signingKey)
                .macAlgorithm(MacAlgorithm.HS512)
                .build();
    }
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(10);
    }
    @Bean
    JwtAuthenticationConverter jwtAuthenticationConverter() {
        JwtGrantedAuthoritiesConverter converter = new JwtGrantedAuthoritiesConverter();
        converter.setAuthorityPrefix("ROLE_");
        JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(converter);
        return jwtAuthenticationConverter;
    }
}