package org.webscraping.util

import org.webscraping.config.Config

class FileWriter {
    String createCsvFile(List<List<String>> stringList, String fileName) {
        new File(Config.getReportFolderPath(), fileName + ".csv").withWriter('utf-8') { writer ->
            stringList.each {line ->
                writer.writeLine line.join(',')
            }
        }
    }
}
