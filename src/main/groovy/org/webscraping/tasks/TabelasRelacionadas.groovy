package org.webscraping.tasks

class TabelasRelacionadas extends Task {

    TabelasRelacionadas(String mainPageUrl) {
        super(mainPageUrl)
    }

    void obterTabelasRelacionadas() {
        String tabelasRelacionadas = super.getLink('Clique aqui para acessar as planilhas')
        String downloadUrl = super.getLink("Clique aqui para baixar a tabela de erros no envio para a ANS (.xlsx)", tabelasRelacionadas)
        super.downloadToFolder('tabela_de_erros', 'tabela_de_erros.xlsx', downloadUrl)
    }

}
