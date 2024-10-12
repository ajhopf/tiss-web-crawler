package org.webscraping

import groovy.sql.Sql
import org.webscraping.repository.UserRepository
import org.webscraping.service.UserService
import org.webscraping.util.SqlFactory
import org.webscraping.view.Menu

static void main(String[] args) {
   try {
      Sql sql = SqlFactory.newInstance()
      UserRepository userRepository = new UserRepository(sql)
      UserService userService = new UserService(userRepository)
      userService.criarTabelaDeUsuarios()
      Menu.iniciar(userService)
   } catch (Exception e) {
      println "Não foi possível conectar ao banco de dados"
      Menu.iniciar()
   }



}