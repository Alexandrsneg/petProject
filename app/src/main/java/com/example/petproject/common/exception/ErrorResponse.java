package com.example.petproject.common.exception;

import java.util.ArrayList;

/**
 * Created by andreyka on 09.10.2016.
 */

public class ErrorResponse {
    public static final String ERROR_CODE_4 = "4";
    public static final String ERROR_CODE_3 = "3";
    public static final String INVALID_TOKEN = "-3";
    public static final String ERROR_CODE_10 = "10";
    public static final String ERROR_CODE_5 = "5";
    public static final String ERROR_CODE_6 = "6";
    public static final String ERROR_CODE_1 = "1";
    public static final String NEED_LOGOUT = "-2";
    public static final String ERROR_CODE_8 = "8";
    public static final String ERROR_CODE_9 = "9";

    public static final String ERROR_500 = "500";
    public static final String ERROR_NETWORK = "NETWORK_ERROR";

    public static ErrorResponse createBackendError() {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setCode(ERROR_500);
        return errorResponse;
    }

    public static ErrorResponse createBackendError(String message) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setCode(ERROR_500);
        errorResponse.setMessage(message);
        return errorResponse;
    }

    public static ErrorResponse createBackendError(String message, String httpCode) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setCode(httpCode);
        errorResponse.setMessage(message);
        return errorResponse;
    }

    public static ErrorResponse createNetworkError() {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setCode(ERROR_NETWORK);
        return errorResponse;
    }


    private String code;
    private String message;
    private ArrayList<String> extra;

    public ArrayList<String> getExtra() {
        return extra;
    }

    public void setExtra(ArrayList<String> extra) {
        this.extra = extra;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "ErrorResponse{" +
                "code='" + code + '\'' +
                ", message='" + message + '\'' +
                ", extra=" + extra +
                '}';
    }
}
