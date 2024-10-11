package org.webscraping.tasks

import org.jsoup.nodes.Document
import org.jsoup.nodes.Element
import org.jsoup.select.Elements
import org.webscraping.util.FileWriter
import org.webscraping.util.HttpRequester

class HistoricoVersoes {
    HttpRequester httpRequester
    String tissUrl

    HistoricoVersoes(HttpRequester httpRequester, String tissUrl) {
        this.httpRequester = httpRequester
        this.tissUrl = tissUrl
    }

    void obterHistoricoDeVersoes() {
        String historicoDeVersoes = getHistoricoVersoesLink(tissUrl)

        getHistoricoDeVersoes(historicoDeVersoes)
    }

    private String getHistoricoVersoesLink(String url) {
        Document tissPage = httpRequester.getPageDocument(url)

        Elements a = tissPage.select('p.callout > a')

        String linkUrl = ''

        a.each {
            String aText = it.text()

            if (aText.contains('Clique aqui para acessar todas as versões dos Componentes')) {
                linkUrl = it.attr('href')
            }
        }

        return linkUrl
    }

    private void getHistoricoDeVersoes(String url) {
        Document versoesPage = httpRequester.getPageDocument(url)

        Elements rows = versoesPage.select('tbody tr')

        List<List<String>> rowElementsList = [['Competência', 'Publicação', 'Início de Vigência']]

        rows.each {row ->
            Elements tableColumns = row.select('td')

            String competencia = tableColumns[0].text()
            Integer ano = competencia.split( '/' )[1].toInteger()
            String publicacao = tableColumns[1].text()
            String inicioVigencia = tableColumns[2].text()

            if (ano > 2016) {
                rowElementsList << [competencia, publicacao, inicioVigencia]
            }
        }

        FileWriter fileWriter = new FileWriter()
        fileWriter.createCsvFile(rowElementsList, 'historico_versoes')
    }


}
