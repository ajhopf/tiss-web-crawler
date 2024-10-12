package org.webscraping.util

import groovyx.net.http.HttpBuilder
import groovyx.net.http.optional.Download
import org.jsoup.nodes.Document

class HttpRequester {
    static Document getPageDocument(String url) {
        try {
            def page = HttpBuilder.configure {
                request.uri = url
                request.headers['Accept-Language'] = "*"
                request.headers['User-Agent'] = 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.110 Safari/537.36'
            }.get()

            return page as Document
        } catch (UnknownHostException e) {
            println "Erro: Domínio não encontrado para URL: $url"
            return null
        } catch (Exception e) {
            println "Erro ao obter a página: $url"
//            e.printStackTrace()
            return null
        }
    }

    static File downloadFile(String url, File file) {
        try {
            return HttpBuilder.configure {
                request.uri = url
            }.get {
                Download.toFile(delegate, file)
            } as File
        } catch (UnknownHostException e) {
            println "Erro: arquivo não encontrado na URL: $url"
            return null
        } catch (Exception e) {
            println "Erro ao fazer downloa do arquivo: $url"
//            e.printStackTrace()
            return null
        }
    }
}
