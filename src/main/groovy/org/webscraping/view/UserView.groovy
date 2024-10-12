package org.webscraping.view

import org.webscraping.exception.UsuarioNotFoundException
import org.webscraping.model.User
import org.webscraping.service.UserService
import org.webscraping.util.InputHelpers

import java.sql.SQLException

class UserView {
    static void adicionarUsuario(Scanner sc, UserService userService) {
        println "Registrar Usuário"
        println "-----------------"

        String email = InputHelpers.obterString('Digite o email', sc)
        String userName = InputHelpers.obterString('Digite o nome do usuário', sc)

        User user = new User(email: email, name: userName)

        try {
            userService.addUser(user)
        } catch (SQLException e) {
            println "Não foi possível adicionar o usuário"
            println e.getCause()
            println e.getMessage()
        }
    }

    static void listarUsuarios(UserService userService) {
        try {
            println "Lista de Usuários Registrados"
            println "-----------------"

            List<User> usuarios = userService.listarUsuarios()

            usuarios.each {usuario ->
                println "Id: $usuario.id | Nome: $usuario.name | Email: $usuario.email"
            }

        } catch (SQLException e) {
            println 'Houve um problema ao listar os usuários'
            e.printStackTrace()
        }
    }

    static void editarUsuario(Scanner sc, UserService userService) {
        try {
            println "Editar Usuário"
            println "-----------------"

            Integer id = InputHelpers.getIntInput(0, 1000, 'Digite o id do usuário', sc)
            String email = InputHelpers.obterString('Digite o email', sc)
            String userName = InputHelpers.obterString('Digite o nome do usuário', sc)

            User updatedUser = new User(id: id, email: email, name: userName)

            userService.updateUsuario(updatedUser)

            println "Usuário editado com sucesso"
        } catch (SQLException e) {
            println 'Houve um problema ao editar o usuário'
            e.printStackTrace()
        } catch (UsuarioNotFoundException e) {
            println e.getMessage()
        }
    }

    static void deletarUsuario(Scanner sc, UserService userService) {
        try {
            println "Deletar Usuário"
            println "-----------------"

            Integer id = InputHelpers.getIntInput(0, 1000, 'Digite o id do usuário', sc)

            userService.deletarUsuario(id)

            println "Usuário deletado com sucesso"
        } catch (SQLException e) {
            println 'Houve um problema ao deletar o usuário'
            e.printStackTrace()
        } catch (UsuarioNotFoundException e) {
            println 'N'
            println e.getMessage()
        }
    }
}
