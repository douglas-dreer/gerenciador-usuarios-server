package io.github.gabitxt.gerenciamentousuario.service;

import io.github.gabitxt.gerenciamentousuario.model.UsuarioDTO;

import java.util.List;

public interface UsuarioService {
    UsuarioDTO criarUsuario(UsuarioDTO usuarioDTO);
    List<UsuarioDTO> listarUsuarios();
    UsuarioDTO buscarUsuarioPorId(Long id);
    UsuarioDTO atualizarUsuario(Long id, UsuarioDTO usuarioDTO) throws Exception;
    void deletarUsuario(Long id) throws Exception;
}
