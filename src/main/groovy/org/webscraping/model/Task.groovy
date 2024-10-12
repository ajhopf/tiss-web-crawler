package org.webscraping.model

import org.jsoup.nodes.Document
import org.jsoup.select.Elements
import org.webscraping.config.Config
import org.webscraping.util.HttpRequester

class Task {
    String mainPageUrl

    Task(String mainPageUrl) {
        this.mainPageUrl = mainPageUrl
    }

    String getLink(String lookUpText, String url = this.mainPageUrl){
        Document tissPage = HttpRequester.getPageDocument(url)

        Elements a = tissPage.select('p.callout > a')

        String linkUrl = ''

        a.each {
            String aText = it.text()

            if (aText.contains(lookUpText)) {
                linkUrl = it.attr('href')
            }
        }

        return linkUrl
    }

    void downloadToFolder(String folder, String fileName, String downloadUrl){
        String folderPath = "$Config.reportFolderPath/$folder"
        def reportDir = new File(folderPath)
        reportDir.mkdirs()

        File filePath = new File("$folderPath/$fileName")

        File file = HttpRequester.downloadFile(downloadUrl, filePath)

        println "File downloaded to: ${file.absolutePath}"
    }
}
