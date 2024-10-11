package org.webscraping.tasks

import org.jsoup.nodes.Document
import org.jsoup.nodes.Element
import org.jsoup.select.Elements
import org.webscraping.config.Config
import org.webscraping.util.FileWriter
import org.webscraping.util.HttpRequester

class TabelasRelacionadas {
    HttpRequester httpRequester
    String tissUrl

    TabelasRelacionadas(HttpRequester httpRequester, String tissUrl) {
        this.httpRequester = httpRequester
        this.tissUrl = tissUrl
    }

    void obterTabelaseRelacionadas() {
        String tabelasRelacionadas = getTabelasRelacionadasLink(tissUrl)
        getTabelasRelacionadas(tabelasRelacionadas)
    }

    private String getTabelasRelacionadasLink(String url) {
        Document tissPage = httpRequester.getPageDocument(url)

        Elements a = tissPage.select('p.callout > a')

        String linkUrl = ''

        a.each {
            String aText = it.text()

            if (aText.contains('Clique aqui para acessar as planilhas')) {
                linkUrl = it.attr('href')
            }
        }

        return linkUrl
    }

    private void getTabelasRelacionadas(String url) {
        Document tabelasRelacionadasPage = httpRequester.getPageDocument(url)

        Elements a = tabelasRelacionadasPage.select('p.callout > a')

        String downloadUrl = ''

        a.each {
            if (it.text() == "Clique aqui para baixar a tabela de erros no envio para a ANS (.xlsx)"){
                downloadUrl = it.attr('href')
            }
        }

        String tabelaDeErrosFolderPath = "$Config.reportFolderPath/tabela_de_erros"
        def reportDir = new File(tabelaDeErrosFolderPath)
        reportDir.mkdirs()

        File saved = new File("$tabelaDeErrosFolderPath/tabela_de_erros.xlsx")

        File file = httpRequester.downloadFile(downloadUrl, saved)

        println "File downloaded to: ${file.absolutePath}"
    }

}
