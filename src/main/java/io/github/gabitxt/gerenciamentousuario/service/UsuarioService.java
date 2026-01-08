package io.github.gabitxt.gerenciamentousuario.service;

import io.github.gabitxt.gerenciamentousuario.controller.request.AtualizarUsuarioRequest;
import io.github.gabitxt.gerenciamentousuario.controller.request.CriarUsuarioRequest;
import io.github.gabitxt.gerenciamentousuario.model.UsuarioDTO;

import java.util.List;

public interface UsuarioService {
    /**
     * Cria um novo usuário.
     * @param request
     * @return UsuarioDTO criado
     */
    UsuarioDTO criarUsuario(CriarUsuarioRequest request);

    /**
     * Lista todos os usuários.
     * @return lista de UsuarioDTO
     */
    List<UsuarioDTO> listarUsuarios();

    /**
     * Busca um usuário por ID.
     * @param id
     * @return {@UsuarioDTO} ou null
     */
    UsuarioDTO buscarUsuarioPorId(Long id);

    /**
     * Atualiza um usuário existente.
     * @param id
     * @param request
     * @return
     * @throws Exception
     */
    UsuarioDTO atualizarUsuario(Long id, AtualizarUsuarioRequest request) throws Exception;

    /**
     * Deleta um usuário por ID.
     * @param id
     * @throws Exception
     */
    void deletarUsuario(Long id) throws Exception;
}
