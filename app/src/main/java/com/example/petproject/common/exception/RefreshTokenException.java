package com.example.petproject.common.exception;

import java.io.IOException;

/**
 * Created by f0x
 * on 06.01.17.
 */

public class RefreshTokenException extends Exception {
    private int httpCode;
    private String serverCode;

    public RefreshTokenException(int httpCode, String serverCode) {
        super();
        this.httpCode = httpCode;
        this.serverCode = serverCode;
    }

    public RefreshTokenException(IOException e) {
        super(e);
    }

    public int getHttpCode() {
        return httpCode;
    }

    public void setHttpCode(int httpCode) {
        this.httpCode = httpCode;
    }

    public String getServerCode() {
        return serverCode;
    }

    public void setServerCode(String serverCode) {
        this.serverCode = serverCode;
    }
}
