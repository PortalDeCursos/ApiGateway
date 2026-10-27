package PortalCursos.ApiGateway;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

@ConfigurationProperties(prefix = "app.rotas")
public record Rotas(
        List<RegraRota> publicas,
        List<RegraRota> regras) {

    public record RegraRota(
            String metodo,         // GET, POST, ... ou "*" para qualquer um
            String caminho,        // ex: /api/usuarios/alunos/**
            List<String> papeis) { // ex: ["ALUNO"]; vazio = só autenticado
    }
}
