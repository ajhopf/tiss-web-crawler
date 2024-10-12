package org.webscraping.util

import org.jsoup.nodes.Document
import org.jsoup.select.Elements

class MainPageUrlFetcher {
    static String getTissPageUrl() {
        try {
            String espacoDoPrestadorHref = getEspacoDoPrestadorLink()
            if (!espacoDoPrestadorHref) throw new Exception("Espaço do Prestador não encontrado")

            String tissHref = getTissLink(espacoDoPrestadorHref)
            if (!tissHref) throw new Exception("Link TISS não encontrado")

            return tissHref
        } catch (Exception e) {
            println "Erro ao obter o endereço da página inicial."
            println e.getMessage()

            return null
        }
    }

    private static String getEspacoDoPrestadorLink() {
        Document initialPage = HttpRequester.getPageDocument('https://www.gov.br/ans/pt-br')

        Elements espacoDoPrestadorAnchorElement = initialPage.select('div#ce89116a-3e62-47ac-9325-ebec8ea95473 a')

        String espacoDoPrestadorDeSaudeHref = espacoDoPrestadorAnchorElement.attr('href')

        return espacoDoPrestadorDeSaudeHref
    }

    private static String getTissLink(String url) {
        Document prestadorDeSaudePage = HttpRequester.getPageDocument(url)

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
}
