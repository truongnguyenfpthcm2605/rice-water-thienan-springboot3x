package org.website.thienan.ricewaterthienan.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.website.thienan.ricewaterthienan.security.jwt.JWTFilter;
import org.website.thienan.ricewaterthienan.security.jwtoauth2.JWTOAuth2ServerProvider;
import org.website.thienan.ricewaterthienan.security.userprincal.AccountDetailService;

import javax.crypto.spec.SecretKeySpec;
import java.util.Collections;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
@Slf4j
public class SecurityConfiguration {

    private final AccountDetailService accountDetailService;
    private final JWTFilter jwtAuthFilter;
    private final JWTOAuth2ServerProvider jwtoAuth2ServerProvider;

    @Bean
    PasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder(10);
    }

    @Bean
    AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(accountDetailService);
        authenticationProvider.setPasswordEncoder(getPasswordEncoder());
        return authenticationProvider;
    }

    // Manager Authentication -> so many AuthenticationProvider in config spring
    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
                .cors(cr -> cr.configurationSource(corsConfigurationSource()))
                .authorizeHttpRequests(auth -> {
                    auth.anyRequest().permitAll();
                })
                .logout(httpSecurityLogoutConfigurer ->
                        httpSecurityLogoutConfigurer.invalidateHttpSession(true)
                                .deleteCookies("JSESSIONID")
                                .clearAuthentication(true)
                                .logoutRequestMatcher(new AntPathRequestMatcher("/api/v1/auth/logout"))
                                .logoutSuccessUrl("/api/v1/auth/logout/success")

                )
        // config login social media with oauth2 in spring config
                .oauth2Login(oauth2 ->
                        oauth2.authorizationEndpoint(authorizationEndpointConfig -> authorizationEndpointConfig.baseUri("/oauth2/authorization"))
                                .defaultSuccessUrl("/api/v1/auth/oauth2/success")
                                .failureUrl("/api/v1/auth/oauth2/fail")
                )
        // throw exception , endpoint denied
                .exceptionHandling(ex -> ex.accessDeniedHandler((request, response, accessDeniedException) -> response.sendRedirect("/api/v1/auth/denied")))
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        // jwt oauth2ResourceServer in Spring Security -> get default token headers "Bearer"
                .oauth2ResourceServer(oauth2 ->
                        oauth2.jwt(jwtConfigurer -> jwtConfigurer.decoder(jwtDecoder()))
                );
        // Jwt Filter
//                .authenticationProvider(authenticationProvider()).addFilterBefore(
//                        jwtAuthFilter, UsernamePasswordAuthenticationFilter.class
//                );

        return http.build();
    }

    // Bean decode token valid or invalid
    @Bean
    JwtDecoder jwtDecoder() {
        // Algorithm the same key your created
        SecretKeySpec secretKeySpec = new SecretKeySpec(jwtoAuth2ServerProvider.SECRET_KEY.getBytes(), "HS512");
        return NimbusJwtDecoder
                .withSecretKey(secretKeySpec)
                .macAlgorithm(MacAlgorithm.HS512)
                .build();
    }


    // Config Cors api allow from client
    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        log.info("Config Cors API");
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowCredentials(true);
        configuration.setAllowedOrigins(Collections.singletonList("*"));
        configuration.setAllowedMethods(Collections.singletonList("*"));
        configuration.setAllowedHeaders(Collections.singletonList("*"));
        configuration.setExposedHeaders(Collections.singletonList("Authorization"));
        configuration.addAllowedOrigin("*");
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
