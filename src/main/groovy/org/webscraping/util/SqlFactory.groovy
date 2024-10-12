package org.webscraping.util

import groovy.sql.Sql
import org.webscraping.config.Config

import java.sql.SQLException

class SqlFactory {
    static Sql newInstance() throws SQLException, ClassNotFoundException {
        final String url = Config.jdbcUrl

        final String user = Config.jdbcUser

        final String password = Config.jdbcPassword

        final String driver = 'org.postgresql.Driver'

        return Sql.newInstance(url, user, password, driver)
    }
}
