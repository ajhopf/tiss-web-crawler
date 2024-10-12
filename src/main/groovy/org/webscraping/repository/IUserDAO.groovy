package org.webscraping.repository

import org.webscraping.model.User

interface IUserDAO {
    Integer adicionarUsuario(User user)
    List<User> listarUsuarios()
    void deletarUsuario(Integer id)
    void editarUsuario(User updatedUser)
    void criarTabela()
}