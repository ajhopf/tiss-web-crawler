package org.webscraping.util

import org.webscraping.exception.OpcaoInvalidaException

class InputHelpers {
    static String obterString(String title, Scanner sc) {
        println title
        return sc.nextLine()
    }

    static int getIntInput(int min, int max, String title, Scanner sc) {
        println title

        while (true) {
            try {
                String stringValue = sc.nextLine()

                int value = Integer.parseInt(stringValue)

                if (value < min || value > max) {
                    throw new OpcaoInvalidaException("Escolha um número entre $min e $max")
                }
                return value
            } catch (OpcaoInvalidaException e) {
                println e.getMessage()
            } catch (InputMismatchException | NumberFormatException e) {
                println "Você deve escolher utilizando um número de $min a $max"
            }
        }
    }
}
