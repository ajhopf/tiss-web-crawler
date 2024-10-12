package org.webscraping.config

class Config {
    static final String reportFolderPath = System.getProperty("user.dir").split('/src')[0] + "/report"
    static final String envPath = System.getProperty("user.dir").split('/src')[0]

    //Config for Database
    static final String jdbcUrl = 'jdbc:postgresql://localhost:5432/tiss'
    static final String jdbcUser = 'andre'
    static final String jdbcPassword = '020917'

}
