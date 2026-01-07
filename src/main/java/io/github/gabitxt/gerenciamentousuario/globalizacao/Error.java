package io.github.gabitxt.gerenciamentousuario.globalizacao;
import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

//classe de reflexo de um único erro

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Error {
    private String property;
   private String message ;





}
