package rs.ac.singidunum.novisad.server.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true,prePostEnabled = true)
public class SecurityConfiguration {

    @Autowired
    UserDetailsService userDetailsService;

    @Bean
    AuthenticationManager authenticationManagerBean(AuthenticationConfiguration configuration) throws Exception{
        return configuration.getAuthenticationManager();
    }

    @Bean
    PasswordEncoder getPaswordEncoder(){
        Map<String,PasswordEncoder> enconders=new HashMap<>();
        enconders.put("bcrypt",new BCryptPasswordEncoder());

        DelegatingPasswordEncoder passwordEncoder= new DelegatingPasswordEncoder("bcrypt",enconders);
        passwordEncoder.setDefaultPasswordEncoderForMatches(enconders.get("bcrypt"));

        return passwordEncoder;
    }

    @Bean
    AuthTokenFilter authTokenFilterBean(AuthenticationConfiguration configuration) throws Exception{
        AuthTokenFilter authTokenFilter=new AuthTokenFilter();
        authTokenFilter.setAuthenticationManager(configuration.getAuthenticationManager());

        return authTokenFilter;
    }

    @Bean
    SecurityFilterChain configure(HttpSecurity httpSecurity, AuthenticationConfiguration configuration) throws Exception {
        return httpSecurity
                .csrf(csrf -> csrf.disable())  // Disable CSRF for APIs
                .cors(cors -> cors.configurationSource(corsConfigurationSource())) // Enable CORS with custom settings
                .sessionManagement(management -> management.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // Stateless session for JWT
                .addFilterBefore(authTokenFilterBean(configuration), UsernamePasswordAuthenticationFilter.class) // Add JWT filter
                .build();
    }

    // CORS configuration bean
    @Bean
    public UrlBasedCorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.addAllowedOrigin("http://192.168.0.11:4200");
        corsConfiguration.addAllowedOrigin("http://localhost:4200");// Add Angular app's URL
        corsConfiguration.addAllowedHeader("*"); // Allow all headers+
        corsConfiguration.addAllowedMethod("*"); // Allow all methods (GET, POST, etc.)
        corsConfiguration.setAllowCredentials(true); // Allow credentials

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/api/**", corsConfiguration); // Apply CORS settings to API paths

        return source;
    }
}
