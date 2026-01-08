package io.github.gabitxt.gerenciamentousuario.model;

import java.time.LocalDateTime;

public record ErroResponse(
                long codigo,
                String mensagem,
                String detalhes,
                LocalDateTime datahora) {
}
