package org.webscraping.view

import org.apache.commons.mail.EmailException
import org.webscraping.model.User
import org.webscraping.service.tasks.DocumentacaoTissService
import org.webscraping.service.EmailSenderService
import org.webscraping.service.tasks.HistoricoVersoesService
import org.webscraping.service.tasks.TabelasRelacionadasService
import org.webscraping.service.UserService
import org.webscraping.util.MainPageUrlFetcher

import javax.mail.AuthenticationFailedException
import javax.mail.MessagingException
import javax.mail.internet.AddressException

class RelatorioView {
    static void gerarRelatorios(UserService userService) {
        String mainPageUrl = MainPageUrlFetcher.tissPageUrl

        if (mainPageUrl == null) {
            return
        }

        executarComTratamento("download da Documentação Tiss") {
            DocumentacaoTissService documentacaoTiss = new DocumentacaoTissService(mainPageUrl)
            documentacaoTiss.obterDocumentacaoTiss()
        }

        executarComTratamento("download do Histórico de Versões") {
            HistoricoVersoesService historicoVersoes = new HistoricoVersoesService(mainPageUrl)
            historicoVersoes.obterHistoricoDeVersoes()
        }

        executarComTratamento("download das Tabelas Relacionadas") {
            TabelasRelacionadasService tabelasRelacionadas = new TabelasRelacionadasService(mainPageUrl)
            tabelasRelacionadas.obterTabelasRelacionadas()
        }

        println "----------------------------------------------"
        println "Enviando relatórios para os emails cadastrados"
        List<User> usuarios = userService.listarUsuarios()
        enviarEmailParaUsuarios(usuarios)
    }

    static void executarComTratamento(String descricao, Closure operacao) {
        try {
            println "----------------------------------------------"
            println "Iniciando: $descricao"
            operacao.call()
        } catch (Exception e) {
            println "Erro ao executar: $descricao"
            println e.getMessage()
            e.printStackTrace()
        }
    }


    static void enviarEmailParaUsuarios(List<User> usuarios) {
        usuarios.each { usuario ->
            try {
                EmailSenderService.sendEmail(usuario.email, usuario.name)
            } catch (AddressException e) {
                println "--------"
                println "Endereço de e-mail inválido para ${usuario.name}: ${usuario.email}."
                println e.getMessage()
            } catch (AuthenticationFailedException e) {
                println "--------"
                println "Falha de autenticação no servidor SMTP. Verifique usuário e senha."
                println e.getMessage()
            } catch (EmailException e) {
                println "--------"
                println "Erro ao enviar e-mail para ${usuario.name}."
                println e.getMessage()
            } catch (MessagingException e) {
                println "--------"
                println "Problema de comunicação com o servidor de e-mail."
                println e.getMessage()
            } catch (IOException e) {
                println "--------"
                println "Erro ao acessar anexos ou arquivo de configuração."
                println e.getMessage()
            } catch (Exception e) {
                println "--------"
                println "Erro inesperado ao enviar e-mail para ${usuario.name}."
                println e.getMessage()
                e.printStackTrace()
            }
        }
    }
}
