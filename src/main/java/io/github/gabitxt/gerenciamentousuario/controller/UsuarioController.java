package io.github.gabitxt.gerenciamentousuario.controller;

import io.github.gabitxt.gerenciamentousuario.model.UsuarioDTO;
import io.github.gabitxt.gerenciamentousuario.service.UsuarioService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {
    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioDTO> obterUsuarioPorId(@PathVariable Long id) {
        UsuarioDTO usuarioDTO = usuarioService.buscarUsuarioPorId(id);
        return ResponseEntity.ok(usuarioDTO);
    }

    @GetMapping
    public ResponseEntity<List<UsuarioDTO>> listarTodosUsuarios() {
        List<UsuarioDTO> resultadoList = usuarioService.listarUsuarios();
        return ResponseEntity.ok(resultadoList);
    }

    @PostMapping
    public ResponseEntity<UsuarioDTO> criarUsuario(@RequestBody UsuarioDTO usuarioDTO) {
        UsuarioDTO usuarioSalvo = usuarioService.criarUsuario(usuarioDTO);
        URI location = URI.create(String.format("/usuarios/%s", usuarioSalvo.getId()));
        return ResponseEntity.created(location).body(usuarioSalvo);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<UsuarioDTO> atualizarUsuario(@PathVariable Long id, @RequestBody UsuarioDTO request) throws Exception {
        UsuarioDTO usuarioAtualizado = usuarioService.atualizarUsuario(id, request);
        return ResponseEntity.ok(usuarioAtualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarUsuario(@PathVariable Long id) throws Exception {
        usuarioService.deletarUsuario(id);
        return ResponseEntity.noContent().build();
    }


}
