package org.webscraping.view

import org.webscraping.model.User
import org.webscraping.service.DocumentacaoTiss
import org.webscraping.service.EmailSenderService
import org.webscraping.service.HistoricoVersoes
import org.webscraping.service.TabelasRelacionadas
import org.webscraping.service.UserService
import org.webscraping.util.MainPageUrlFetcher

class RelatorioView {
    static void gerarRelatorios(UserService userService) {
        try {
            String mainPageUrl = MainPageUrlFetcher.tissPageUrl

            println "Fazendo download da Documentação Tiss"
            DocumentacaoTiss documentacaoTiss = new DocumentacaoTiss(mainPageUrl)
            documentacaoTiss.obterDocumentacaoTiss()

            println "Fazendo download do Histórico de Versões"
            HistoricoVersoes historicoVersoes = new HistoricoVersoes(mainPageUrl)
            historicoVersoes.obterHistoricoDeVersoes()

            println "Fazendo download das Tabelas Relacionadas"
            TabelasRelacionadas tabelasRelacionadas = new TabelasRelacionadas(mainPageUrl)
            tabelasRelacionadas.obterTabelasRelacionadas()

            println "Enviando relatórios para os emails cadastrados"
            List<User> usuarios = userService.listarUsuarios()

            usuarios.each {usuario ->
                EmailSenderService.sendEmail(usuario.email, usuario.name)
            }

       } catch (UnknownHostException e) {
            println "Unknown host: ${e.message}"
       } catch (SocketTimeoutException e) {
            println "Connection timed out"
       } catch (IOException e) {
            println "IO Error: ${e.message}"
       } catch (IllegalArgumentException e) {
            println "Error parsing document: ${e.message}"
       } catch (Exception e) {
          e.printStackTrace()
            println "An unexpected error occurred: ${e.message}"
       }
    }
}
