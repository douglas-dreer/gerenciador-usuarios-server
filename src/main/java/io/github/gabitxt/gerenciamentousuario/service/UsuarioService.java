package io.github.gabitxt.gerenciamentousuario.service;

import io.github.gabitxt.gerenciamentousuario.model.UsuarioDTO;

import java.util.List;

// interface que vai implementar a criação de UsuarioDTO
//


public interface UsuarioService {
    /**
     * Cria um novo usuário.
     * @param usuarioDTO
     * @return UsuarioDTO criado
     */
    UsuarioDTO criarUsuario(UsuarioDTO usuarioDTO);

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
     * @param usuarioDTO
     * @return
     * @throws Exception
     */
    UsuarioDTO atualizarUsuario(Long id, UsuarioDTO usuarioDTO) throws Exception;

    /**
     * Deleta um usuário por ID.
     * @param id
     * @throws Exception
     */
    void deletarUsuario(Long id) throws Exception;
}
