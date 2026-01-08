package io.github.gabitxt.gerenciamentousuario;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class GerenciamentoUsuarioApplication {

    public static void main(String[] args) {
        SpringApplication.run(GerenciamentoUsuarioApplication.class, args);
    }

}
