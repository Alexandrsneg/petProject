package com.example.petproject.common.exception;

/**
 * Created by f0x
 * on 13.10.16.
 * Этот ексепшн будет выкидываться при сообщении к репозиторию
 */

public class RepositoryException extends Exception {

    private String message;
    private ErrorResponse errorResponse;

    public RepositoryException(String message) {
        super(message);
    }

    public RepositoryException(ErrorResponse errorResponse) {
        this.errorResponse = errorResponse;
    }

    public RepositoryException(Throwable cause) {
        super(cause);
    }

    @Override
    public String getMessage() {
        return message != null && message.length() > 0
                ? message
                : (errorResponse != null ? errorResponse.getMessage() : "");
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ErrorResponse getErrorResponse() {
        return errorResponse;
    }
}
