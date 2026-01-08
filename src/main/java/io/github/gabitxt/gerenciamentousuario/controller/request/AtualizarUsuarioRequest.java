package io.github.gabitxt.gerenciamentousuario.controller.request;

import io.github.gabitxt.gerenciamentousuario.enums.TipoDocumento;

import java.time.LocalDate;

public record AtualizarUsuarioRequest(
        String nome,
        String email,
        LocalDate dataNascimento,
        TipoDocumento tipoDocumento,
        String numeroDocumento
) {}

