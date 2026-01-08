package io.github.gabitxt.gerenciamentousuario.controller.request;

import io.github.gabitxt.gerenciamentousuario.enums.TipoDocumento;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDate;

@Schema(description = "Dados para criação de um novo usuário")
public record CriarUsuarioRequest(
        @Schema(description = "Nome completo do usuário", example = "João Silva", requiredMode = Schema.RequiredMode.REQUIRED)
        String nome,

        @Schema(description = "Email único do usuário", example = "joao@email.com", requiredMode = Schema.RequiredMode.REQUIRED)
        String email,

        @Schema(description = "Data de nascimento", example = "1990-05-15")
        LocalDate dataNascimento,

        @Schema(description = "Tipo do documento", example = "CPF")
        TipoDocumento tipoDocumento,

        @Schema(description = "Número do documento", example = "12345678901", requiredMode = Schema.RequiredMode.REQUIRED)
        String numeroDocumento,

        @Schema(description = "Endereço do usuário (opcional)")
        CriarEnderecoRequest endereco
) {}
