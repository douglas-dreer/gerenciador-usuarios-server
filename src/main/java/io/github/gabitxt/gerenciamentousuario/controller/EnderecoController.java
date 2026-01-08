package io.github.gabitxt.gerenciamentousuario.controller;

import io.github.gabitxt.gerenciamentousuario.controller.request.CriarEnderecoRequest;
import io.github.gabitxt.gerenciamentousuario.model.EnderecoDTO;
import io.github.gabitxt.gerenciamentousuario.service.EnderecoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/enderecos")
public class EnderecoController {
    private final EnderecoService service;

    public EnderecoController(EnderecoService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<EnderecoDTO> criarEndereco(@Valid @RequestBody CriarEnderecoRequest request) {
        EnderecoDTO enderecoCriado = service.criarEndereco(request);
        return ResponseEntity.ok(enderecoCriado);
    }
    
}
