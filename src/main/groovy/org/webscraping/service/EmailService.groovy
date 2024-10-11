package org.webscraping.service

import io.github.cdimascio.dotenv.Dotenv
import org.apache.commons.mail.DefaultAuthenticator
import org.apache.commons.mail.EmailAttachment
import org.apache.commons.mail.MultiPartEmail
import org.webscraping.config.Config

class EmailService {
    static void sendEmail (String userEmail, String userName) {
        Dotenv dotenv = Dotenv.configure().directory("../../../../../.env").load();

        MultiPartEmail email = new MultiPartEmail()
        email.setHostName(dotenv.get("SMTP_HOST"))
        email.setSmtpPort(Integer.parseInt(dotenv.get("SMTP_PORT")))
        email.setAuthenticator(new DefaultAuthenticator(dotenv.get("SMTP_USER"), dotenv.get("SMTP_PASS")))
        email.setSSLOnConnect(true)
        email.addTo(userEmail, userName)
        email.setFrom(dotenv.get("SMTP_USER"), "André Jaques Hopf")
        email.setSubject("Relatório Tiss")
        email.setMsg("Segue em anexo os documentos obtidos no relatório.")

        email = addAttachmentsTo(email)

        email.send();
    }

    static addAttachmentsTo(MultiPartEmail email) {
        String reportFolderPath = Config.reportFolderPath

        List<Map<String, String>> attachments = [
                [
                        name: 'Componente de Comunicação',
                        fileName: 'componente_comunicacao.zip',
                        folder: 'padrao_tiss/componente_comunicacao/'
                ],
                [
                        name: 'Tabela de Erros',
                        fileName: 'tabela_de_erros.xlsx',
                        folder: 'tabela_de_erros/'
                ],
                [
                        name: 'Historico de Versões',
                        fileName: 'historico_versoes.csv',
                        folder: ''
                ],
        ]

        attachments.each {item ->
            EmailAttachment attachment = new EmailAttachment()

            attachment.setPath("$reportFolderPath/$item.folder$item.fileName")
            attachment.setDisposition(EmailAttachment.ATTACHMENT)
            attachment.setDescription(item.name)
            attachment.setName(item.fileName)

            email.attach(attachment)
        }

        return email
    }
}