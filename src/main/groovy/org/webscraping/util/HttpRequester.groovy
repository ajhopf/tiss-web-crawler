package org.webscraping.util

import groovyx.net.http.HttpBuilder
import groovyx.net.http.optional.Download
import org.jsoup.nodes.Document

class HttpRequester {
    Document getPageDocument(String url) {
        Document page = HttpBuilder.configure {
            request.uri = url
            request.headers['Accept-Language'] = "*"
            request.headers['User-Agent'] = 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.110 Safari/537.36'
        }.get() as Document

        return page
    }

    File downloadFile(String url, File file) {
        return HttpBuilder.configure {
            request.uri = url
        }.get {
            Download.toFile(delegate, file)
        } as File
    }
}
