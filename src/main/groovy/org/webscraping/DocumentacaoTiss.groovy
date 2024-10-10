package org.webscraping

import groovyx.net.http.HttpBuilder
import org.jsoup.nodes.Document
import org.jsoup.nodes.Element
import org.jsoup.select.Elements

import groovyx.net.http.optional.Download

class DocumentacaoTiss {
    HttpRequester httpRequester

    DocumentacaoTiss(HttpRequester httpRequester) {
        this.httpRequester = httpRequester
    }

    void obterDocumentacaoTiss() {
        String firstPageLink = getFirstPageLink()
        String secondPageLink = getSecondPageLink(firstPageLink)
        String thirdPageLink = getThirdPageLink(secondPageLink)
        downloadComunicationComponent(thirdPageLink)
    }

    String getFirstPageLink() {
        Document initialPage = httpRequester.getPageDocument('https://www.gov.br/ans/pt-br')

        Elements espacoDoPrestadorAnchorElement = initialPage.select('div#ce89116a-3e62-47ac-9325-ebec8ea95473 a')

        String espacoDoPrestadorDeSaudeHref = espacoDoPrestadorAnchorElement.attr('href')

        return espacoDoPrestadorDeSaudeHref
    }

    String getSecondPageLink(String url) {
        Document prestadorDeSaudePage = httpRequester.getPageDocument(url)

        Elements cards = prestadorDeSaudePage.select('div.govbr-cards div.card a')

        String linkUrl = ''

        cards.each {card ->
            String spanText = card.select('span.titulo').text()

            if (spanText.contains('TISS')) {
                linkUrl = card.attr('href')
            }
        }

        return linkUrl
    }

    String getThirdPageLink(String url) {
        Document tissPage = httpRequester.getPageDocument(url)

        Elements a = tissPage.select('p.callout > a')

        String linkUrl = ''

        a.each {
            String aText = it.text()

            if (aText.contains('Clique aqui para acessar a versão')) {
                linkUrl = it.attr('href')
            }
        }

        return linkUrl
    }

    void downloadComunicationComponent(String url) {
        Document padraoTissPage = httpRequester.getPageDocument(url)

        Elements rows = padraoTissPage.select('tr')

        String downloadUrl = ''

        rows.each {row ->
            Element rowTitle = row.selectFirst('td')

            if (rowTitle && rowTitle.text() == 'Componente de Comunicação' ){
                downloadUrl = row.select('a').attr('href')
            }
        }

        File saved = new File('../../../../../downloads/PadraoTiss.zip')

        File file = httpRequester.downloadFile(downloadUrl, saved)

        println "File downloaded to: ${file.absolutePath}"
    }

}
