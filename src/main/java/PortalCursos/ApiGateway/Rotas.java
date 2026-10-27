package PortalCursos.ApiGateway;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

@ConfigurationProperties(prefix = "app.rotas")
public record Rotas(
        List<String> publicas,
        List<String> aluno,
        List<String> professor,
        List<String> admin,
        List<String> coordenador) {
}