package org.webscraping.repository

import groovy.sql.Sql
import org.webscraping.exception.UsuarioNotFoundException
import org.webscraping.model.User

class UserRepository implements IUserDAO {
    private Sql sql = null

    UserRepository(Sql sql) {
        this.sql = sql
    }

    @Override
    Integer adicionarUsuario(User user) {
        def stmt = """
            INSERT INTO usuarios (email, nome)
            VALUES ($user.email, $user.name)
        """

        def keys = sql.executeInsert(stmt)

        return keys[0][0] as Integer
    }

    @Override
    List<User> listarUsuarios() {
        def stmt = """
            SELECT * FROM usuarios
        """

        List<User> userList = []

        this.sql.eachRow(stmt) { row ->
            User user = new User()
            user.id = row.getInt('id')
            user.email = row.getString('email')
            user.name = row.getString('nome')

            userList << user
        }

        return userList
    }

    @Override
    void deletarUsuario(Integer id) {
        def stmt = """
            DELETE FROM usuarios
            WHERE id = $id
        """

        int rowsAffected = sql.executeUpdate(stmt)

        if (rowsAffected == 0) {
            throw new UsuarioNotFoundException("Não foi possível encontrar um usuario com o id $id")
        }
    }

    @Override
    void editarUsuario(User updatedUser) {
        def stmt = """
            UPDATE usuarios
            SET email = $updatedUser.email, nome = $updatedUser.name
            WHERE id = $updatedUser.id
        """

        int rowsAffected = sql.executeUpdate(stmt)

        if (rowsAffected == 0) {
            throw new UsuarioNotFoundException("Não foi possível encontrar o usuário com o id $updatedUser.id")
        }
    }
}
