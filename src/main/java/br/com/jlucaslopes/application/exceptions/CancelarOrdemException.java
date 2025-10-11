package br.com.jlucaslopes.application.exceptions;

public class CancelarOrdemException extends RuntimeException{

    public CancelarOrdemException(String message) {
        super(message);
    }

    public CancelarOrdemException(String message, Throwable cause) {
        super(message, cause);
    }
}
