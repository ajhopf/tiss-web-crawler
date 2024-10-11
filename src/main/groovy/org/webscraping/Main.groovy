package org.webscraping

import org.webscraping.tasks.DocumentacaoTiss
import org.webscraping.tasks.HistoricoVersoes
import org.webscraping.util.HttpRequester
import org.webscraping.util.MainPageUrlFetcher

static void main(String[] args) {
   HttpRequester httpRequester = new HttpRequester()

   MainPageUrlFetcher tissPageUrlFetcher = new MainPageUrlFetcher(httpRequester)
   String tissUrl = tissPageUrlFetcher.getTissPageUrl()

   DocumentacaoTiss documentacaoTiss = new DocumentacaoTiss(httpRequester, tissUrl)
   documentacaoTiss.obterDocumentacaoTiss()

   HistoricoVersoes historicoVersoes = new HistoricoVersoes(httpRequester, tissUrl)
   historicoVersoes.obterHistoricoDeVersoes()
}

