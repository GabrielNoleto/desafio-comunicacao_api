package com.luizalebs.comunicacao_api.infraestructure.exceptions;

public class InfrastructureException extends RuntimeException {
    public InfrastructureException(String message) {
        super(message);
    }

    public InfrastructureException(String message, Throwable throwable){
        super(message, throwable);
    }
}

