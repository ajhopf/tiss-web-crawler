package org.webscraping.util

import groovy.sql.Sql

import java.sql.SQLException

class SqlFactory {
    static Sql newInstance() throws SQLException, ClassNotFoundException {
        final String url = 'jdbc:postgresql://localhost:5432/tiss'

        final String user = 'andre'

        final String password = '020917'

        final String driver = 'org.postgresql.Driver'

        return Sql.newInstance(url, user, password, driver)
    }
}
