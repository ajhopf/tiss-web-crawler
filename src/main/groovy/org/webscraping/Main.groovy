package org.webscraping

import org.webscraping.tasks.DocumentacaoTiss
import org.webscraping.tasks.HistoricoVersoes
import org.webscraping.tasks.TabelasRelacionadas
import org.webscraping.util.HttpRequester
import org.webscraping.util.MainPageUrlFetcher

static void main(String[] args) {
   try {
      HttpRequester httpRequester = new HttpRequester()

      MainPageUrlFetcher tissPageUrlFetcher = new MainPageUrlFetcher(httpRequester)
      String tissUrl = tissPageUrlFetcher.getTissPageUrl()

      DocumentacaoTiss documentacaoTiss = new DocumentacaoTiss(httpRequester, tissUrl)
      documentacaoTiss.obterDocumentacaoTiss()

      HistoricoVersoes historicoVersoes = new HistoricoVersoes(httpRequester, tissUrl)
      historicoVersoes.obterHistoricoDeVersoes()

      TabelasRelacionadas tabelasRelacionadas = new TabelasRelacionadas(httpRequester, tissUrl)
      tabelasRelacionadas.obterTabelaseRelacionadas()
   } catch (UnknownHostException e) {
      println "Unknown host: ${e.message}"
   } catch (SocketTimeoutException e) {
      println "Connection timed out"
   } catch (IOException e) {
      println "IO Error: ${e.message}"
   } catch (IllegalArgumentException e) {
      println "Error parsing document: ${e.message}"
   } catch (Exception e) {
      println "An unexpected error occurred: ${e.message}"
   }
}

