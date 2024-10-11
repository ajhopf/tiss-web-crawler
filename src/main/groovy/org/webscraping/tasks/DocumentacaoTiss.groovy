package org.webscraping.tasks

import org.jsoup.nodes.Document
import org.jsoup.nodes.Element
import org.jsoup.select.Elements
import org.webscraping.config.Config
import org.webscraping.util.HttpRequester

class DocumentacaoTiss {
    HttpRequester httpRequester
    String tissUrl

    DocumentacaoTiss(HttpRequester httpRequester, String tissUrl) {
        this.httpRequester = httpRequester
        this.tissUrl = tissUrl
    }

    void obterDocumentacaoTiss() {
        String padraoTissUrl = getPadraoTissLink(tissUrl)
        downloadComponenteComunicacao(padraoTissUrl)
    }

    private String getPadraoTissLink(String url) {
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

    private void downloadComponenteComunicacao(String url) {
        Document padraoTissPage = httpRequester.getPageDocument(url)

        Elements rows = padraoTissPage.select('tr')

        String downloadUrl = ''

        rows.each {row ->
            Element rowTitle = row.selectFirst('td')

            if (rowTitle && rowTitle.text() == 'Componente de Comunicação' ){
                downloadUrl = row.select('a').attr('href')
            }
        }

        String comunicacaoFolderPath = "$Config.reportFolderPath/componente_comunicacao"
        File reportDir = new File(comunicacaoFolderPath)

        reportDir.mkdir()

        File saved = new File("$comunicacaoFolderPath/PadraoTiss.zip")

        File file = httpRequester.downloadFile(downloadUrl, saved)

        println "File downloaded to: ${file.absolutePath}"
    }

}
