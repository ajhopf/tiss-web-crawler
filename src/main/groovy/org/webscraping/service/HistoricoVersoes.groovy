package org.webscraping.service

import org.jsoup.nodes.Document
import org.jsoup.select.Elements
import org.webscraping.config.Config
import org.webscraping.model.Task
import org.webscraping.util.HttpRequester

class HistoricoVersoes extends Task{

    HistoricoVersoes(String mainPageUrl) {
        super(mainPageUrl)
    }

    void obterHistoricoDeVersoes() {
        String historicoDeVersoes = super.getLink('Clique aqui para acessar todas as versões dos Componentes')

        getHistoricoDeVersoes(historicoDeVersoes)
    }

    private static void getHistoricoDeVersoes(String url) {
        Document versoesPage = HttpRequester.getPageDocument(url)

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

        createCsvFile(rowElementsList, 'historico_versoes')
    }

    private static void createCsvFile(List<List<String>> stringList, String fileName) {
        new File(Config.getReportFolderPath(), fileName + ".csv")
                .withWriter('utf-8') { writer ->
                    stringList.each {line ->
                        writer.writeLine line.join(',')
                    }
                }
    }


}
