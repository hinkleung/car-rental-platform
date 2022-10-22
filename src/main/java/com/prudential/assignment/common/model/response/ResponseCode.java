package com.prudential.assignment.common.model.response;

public enum ResponseCode implements ErrorCode {

    SUCCESS(200, "success"),

    FAIL(400, "fail"),

    UNAUTHORIZED(401, "request unauthorized"),

    INVALID_PARAM(402, "invalid param"),

    FORBIDDEN(403, "request not permitted"),

    NOT_FOUND(404, "not found"),

    UNKNOWN_ERROR(405, "unknown error"),

    INTERNAL_SERVER_ERROR(500, "server internal error"),

    USERNAME_IS_EXIST(60001, "username is exist"),

    USERNAME_IS_NOT_EXIST(60002, "username is not exist"),

    PASSWORD_ERROR(60003, "wrong password"),

    CAR_STOCK_IS_NOT_ENOUGH_FOR_APPLY(60004, "car stock is not enough to apply");


    ResponseCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    /**
     * response code
     */
    final private int code;

    /**
     * response message
     */
    final private String message;

    @Override
    public int getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
