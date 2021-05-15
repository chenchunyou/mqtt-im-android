package com.uqubang.mqttim.http.exception;

public class GetContactListException extends Exception {
    public GetContactListException() {
    }

    public GetContactListException(String message) {
        super(message);
    }

    public GetContactListException(String message, Throwable cause) {
        super(message, cause);
    }

    public GetContactListException(Throwable cause) {
        super(cause);
    }

    public GetContactListException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
