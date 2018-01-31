package com.rat.regresapitest.domain.global.exceptions;

public class NoNetworkException extends RuntimeException {

    public NoNetworkException() {
        super();
    }

    public NoNetworkException(String message) {
        super(message);
    }

    public NoNetworkException(String message, Throwable cause) {
        super(message, cause);
    }

}
