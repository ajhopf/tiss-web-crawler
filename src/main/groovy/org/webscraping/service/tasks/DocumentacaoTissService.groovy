package org.webscraping.service.tasks

import org.jsoup.nodes.Document
import org.jsoup.nodes.Element
import org.jsoup.select.Elements
import org.webscraping.model.Task
import org.webscraping.util.HttpRequester

class DocumentacaoTissService extends Task {

    DocumentacaoTissService(String mainPageUrl) {
        super(mainPageUrl)
    }

    void obterDocumentacaoTiss() {
        String padraoTissUrl = super.getLink('Clique aqui para acessar a versão')

        downloadComponente(padraoTissUrl, 'Componente de Comunicação', 'componente_comunicacao.zip')
        downloadComponente(padraoTissUrl, 'Componente Organizacional', 'componente_organizacional.pdf')
        downloadComponente(padraoTissUrl, 'Componente de Conteúdo e Estrutura', 'componente_conteudo.zip')
        downloadComponente(padraoTissUrl, 'Componente de Representação de Conceitos em Saúde (Terminologia Unificada da Saúde Suplementar)', 'componente_representacao.zip')
        downloadComponente(padraoTissUrl, 'Componente de Segurança e Privacidade', 'componente_seguranca.zip')
    }


    private void downloadComponente(String url, String lookUpText, String fileName) {
        Document padraoTissPage = HttpRequester.getPageDocument(url)

        Elements rows = padraoTissPage.select('tbody tr')

        String downloadUrl = ''

        rows.each {row ->
            Element rowTitle = row.selectFirst('td')

            if (rowTitle && rowTitle.text() == lookUpText ){
                downloadUrl = row.select('a').attr('href')
            }
        }

        String folderName = fileName.split(/\./)[0]

        super.downloadToFolder("padrao_tiss/$folderName", fileName, downloadUrl)
    }

}
