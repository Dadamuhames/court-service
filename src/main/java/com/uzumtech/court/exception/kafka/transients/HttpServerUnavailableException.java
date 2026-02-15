package com.uzumtech.court.exception.kafka.transients;

public class HttpServerUnavailableException extends TransientException {
    public HttpServerUnavailableException(Exception ex) {
        super(ex);
    }
}
