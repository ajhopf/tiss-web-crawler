package org.webscraping.util

import org.jsoup.nodes.Document
import org.jsoup.select.Elements

class MainPageUrlFetcher {
    HttpRequester httpRequester

    MainPageUrlFetcher(HttpRequester httpRequester) {
        this.httpRequester = httpRequester
    }

    String getTissPageUrl() {
        String espacoDoPrestadorHref = getEspacoDoPrestadorLink()
        String tissHref = getTissLink(espacoDoPrestadorHref)

        return tissHref
    }

    private String getEspacoDoPrestadorLink() {
        Document initialPage = httpRequester.getPageDocument('https://www.gov.br/ans/pt-br')

        Elements espacoDoPrestadorAnchorElement = initialPage.select('div#ce89116a-3e62-47ac-9325-ebec8ea95473 a')

        String espacoDoPrestadorDeSaudeHref = espacoDoPrestadorAnchorElement.attr('href')

        return espacoDoPrestadorDeSaudeHref
    }

    private String getTissLink(String url) {
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
}
