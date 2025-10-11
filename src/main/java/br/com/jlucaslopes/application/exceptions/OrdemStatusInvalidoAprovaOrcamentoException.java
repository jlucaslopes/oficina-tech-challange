package br.com.jlucaslopes.application.exceptions;

public class OrdemStatusInvalidoAprovaOrcamentoException extends RuntimeException {

    public OrdemStatusInvalidoAprovaOrcamentoException(String errorMessage) {
        super(errorMessage);
    }
}
