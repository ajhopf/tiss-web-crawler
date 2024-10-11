package org.webscraping

import org.webscraping.service.DocumentacaoTiss
import org.webscraping.service.EmailService
import org.webscraping.service.HistoricoVersoes
import org.webscraping.service.TabelasRelacionadas
import org.webscraping.util.MainPageUrlFetcher

static void main(String[] args) {
//   try {
//      String mainPageUrl = MainPageUrlFetcher.tissPageUrl
//
//      DocumentacaoTiss documentacaoTiss = new DocumentacaoTiss(mainPageUrl)
//      documentacaoTiss.obterDocumentacaoTiss()
//
//      HistoricoVersoes historicoVersoes = new HistoricoVersoes(mainPageUrl)
//      historicoVersoes.obterHistoricoDeVersoes()
//
//      TabelasRelacionadas tabelasRelacionadas = new TabelasRelacionadas(mainPageUrl)
//      tabelasRelacionadas.obterTabelasRelacionadas()
//   } catch (UnknownHostException e) {
//      println "Unknown host: ${e.message}"
//   } catch (SocketTimeoutException e) {
//      println "Connection timed out"
//   } catch (IOException e) {
//      println "IO Error: ${e.message}"
//   } catch (IllegalArgumentException e) {
//      println "Error parsing document: ${e.message}"
//   } catch (Exception e) {
//      e.printStackTrace()
//      println "An unexpected error occurred: ${e.message}"
//   }
   EmailService.sendEmail()
}

