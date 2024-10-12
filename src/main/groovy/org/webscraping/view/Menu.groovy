package org.webscraping.view

import org.webscraping.service.UserService
import org.webscraping.util.InputHelpers

class Menu {
    static void iniciar(UserService userService) {
        Scanner sc = new Scanner(System.in)

        while (true) {
            gerarMenuInicial()
            try {
                int opcaoSelecionada = InputHelpers.getIntInput(0, 6, "Selecione a opção desejada: ", sc)

                switch (opcaoSelecionada) {
                    case 0:
                        println "Até logo!"
                        return
                    case 1:
                        RelatorioView.gerarRelatorios(userService)
                        break
                    case 2:
                        UserView.listarUsuarios(userService)
                        break
                    case 3:
                        UserView.adicionarUsuario(sc, userService)
                        break
                    case 4:
                        UserView.editarUsuario(sc, userService)
                        break
                    case 5:
                        UserView.deletarUsuario(sc, userService)
                        break
                    default: println "Você escolheu a opção " + opcaoSelecionada
                }

            } catch (Exception e) {
                println 'la no menu'
                e.getMessage()
                e.printStackTrace()
                return
            }
        }
    }


    static void gerarMenuInicial(){
        println '''---------------------------------------------
        |Bem vindo ao Tiss WebScraper
               
        |Selecione uma das opções abaixo:
        |----- Relatórios -----
        |1. Gerar Relatórios
        |-----   Usuários    -----
        |2. Listar usuários
        |3. Adicionar usuário
        |4. Editar usuário
        |5. Deletar usuário
        |--------------------------------------------
        |0. Sair do Sistema
        |---------------------------------------------
        '''.stripMargin()
    }
}
