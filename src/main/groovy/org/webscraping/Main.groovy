package org.webscraping

static void main(String[] args) {
   HttpRequester httpRequester = new HttpRequester()

   DocumentacaoTiss documentacaoTiss = new DocumentacaoTiss(httpRequester)

   documentacaoTiss.obterDocumentacaoTiss()
}

