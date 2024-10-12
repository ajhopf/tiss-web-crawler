package org.webscraping.service

import org.webscraping.exception.UsuarioNotFoundException
import org.webscraping.model.User
import org.webscraping.repository.UserRepository

import java.sql.SQLException

class UserService {
    UserRepository repository

    UserService(UserRepository repository) {
        this.repository = repository
    }

    Integer addUser(User user) throws SQLException {
        return repository.adicionarUsuario(user)
    }

    List<User> listarUsuarios() throws SQLException {
        return repository.listarUsuarios()
    }

    void updateUsuario(User updatedUser) throws UsuarioNotFoundException, SQLException {
        repository.editarUsuario(updatedUser)
    }

    void deletarUsuario(Integer id) throws UsuarioNotFoundException, SQLException {
        repository.deletarUsuario(id)
    }
}
