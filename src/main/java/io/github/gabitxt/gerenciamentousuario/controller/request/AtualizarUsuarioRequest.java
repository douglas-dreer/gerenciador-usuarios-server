package io.github.gabitxt.gerenciamentousuario.controller.request;

import io.github.gabitxt.gerenciamentousuario.enums.TipoDocumento;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDate;

@Schema(description = "Dados para atualização de um usuário existente")
public record AtualizarUsuarioRequest(
        @Schema(description = "Nome completo do usuário", example = "João Silva Santos")
        String nome,

        @Schema(description = "Email do usuário", example = "joao.santos@email.com")
        String email,

        @Schema(description = "Data de nascimento", example = "1990-05-15")
        LocalDate dataNascimento,

        @Schema(description = "Tipo do documento", example = "CPF")
        TipoDocumento tipoDocumento,

        @Schema(description = "Número do documento", example = "12345678901")
        String numeroDocumento
) {}
