package PortalCursos.ApiGateway;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@AllArgsConstructor

public class SegurancaConfig {

    @Bean
    SecurityFilterChain securityFilterChain(
            HttpSecurity http,
            JwtAuthenticationConverter jwtAuthenticationConverter,
            Rotas rotas) throws Exception {

        return http
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> {
                    rotas.publicas().forEach(r ->
                            auth.requestMatchers(metodo(r), r.caminho()).permitAll());

                    rotas.regras().forEach(r -> {
                        var matcher = auth.requestMatchers(metodo(r), r.caminho());
                        if (r.papeis() == null || r.papeis().isEmpty()) {
                            matcher.authenticated();
                        } else {
                            matcher.hasAnyRole(r.papeis().toArray(String[]::new));
                        }
                    });

                    auth.anyRequest().authenticated();
                })
                .oauth2ResourceServer(oauth2 -> oauth2
                        .jwt(jwt -> jwt.jwtAuthenticationConverter(jwtAuthenticationConverter)))
                .build();
    }

    private HttpMethod metodo(Rotas.RegraRota r) {
        return "*".equals(r.metodo()) ? null : HttpMethod.valueOf(r.metodo());
    }
}
