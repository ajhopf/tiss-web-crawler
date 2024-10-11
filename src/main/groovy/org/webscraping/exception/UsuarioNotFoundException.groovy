package org.webscraping.exception

class UsuarioNotFoundException extends RuntimeException {
    UsuarioNotFoundException(String mensagem) {
        super(mensagem)
    }
}
