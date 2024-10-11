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

    void addUser(User user) {
        try {
            Integer id = repository.adicionarUsuario(user)
            println "Email registrado com sucesso. Id: $id"
        } catch (SQLException e) {
            throw e
        }
    }

    List<User> listarUsuarios() {
        try {
            return repository.listarUsuarios()
        } catch (SQLException e) {
            throw e
        }
    }

    void updateUsuario(User updatedUser) {
        try {
            repository.editarUsuario(updatedUser)
        } catch (UsuarioNotFoundException e) {
            throw e
        } catch (SQLException e) {
            throw e
        }
    }

    void deletarUsuario(Integer id) {
        try {
            repository.deletarUsuario(id)
        } catch (UsuarioNotFoundException e) {
            throw e
        } catch (SQLException e) {
            throw e
        }
    }
}
