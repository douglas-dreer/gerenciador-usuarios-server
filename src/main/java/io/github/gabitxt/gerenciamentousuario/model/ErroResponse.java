package io.github.gabitxt.gerenciamentousuario.model;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

@Schema(description = "Resposta de erro da API")
public record ErroResponse(
        @Schema(description = "Código do erro", example = "404")
        long codigo,

        @Schema(description = "Mensagem de erro", example = "Usuário não encontrado")
        String mensagem,

        @Schema(description = "Detalhes adicionais do erro", example = "Usuário com ID 999 não existe")
        String detalhes,

        @Schema(description = "Data e hora do erro", example = "2026-01-08T12:00:00")
        LocalDateTime datahora
) {}
