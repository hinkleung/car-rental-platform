package com.prudential.assignment.common.model.response;

import lombok.Data;
import org.springframework.util.StringUtils;

/**
 * common response object
 *
 * @param <T> data type
 */
@Data
public class Response<T> {

    private int code;
    private String message;
    T data;

    public Response() {
        this.code = ResponseCode.SUCCESS.getCode();
        this.message = ResponseCode.SUCCESS.getMessage();
    }

    public Response(int code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public boolean ifSuccess() {
        return this.code == ResponseCode.SUCCESS.getCode();
    }

    /**
     * return success
     *
     * @param <T>
     * @return
     */
    public static <T> Response<T> success() {
        return new Response<T>(ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getMessage(), null);
    }

    /**
     * return success
     *
     * @param data
     * @param <T>
     * @return
     */
    public static <T> Response<T> success(T data) {
        return new Response<T>(ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getMessage(), data);
    }

    /**
     * return failure
     *
     * @param <T>
     * @return
     */
    public static <T> Response<T> error() {
        return new Response<T>(ResponseCode.FAIL.getCode(), ResponseCode.FAIL.getMessage(), null);
    }

    /**
     * return failure
     *
     * @param errorCode
     * @param <T>
     * @return
     */
    public static <T> Response<T> error(ErrorCode errorCode) {
        return new Response<T>(errorCode.getCode(), errorCode.getMessage(), null);
    }

    /**
     * return failure
     *
     * @param errorCode
     * @param message
     * @param <T>
     * @return
     */
    public static <T> Response<T> error(ErrorCode errorCode, String message) {
        return new Response<T>(errorCode.getCode(), message, null);
    }

    /**
     * return failure
     *
     * @param message
     * @param <T>
     * @return
     */
    public static <T> Response<T> error(String message) {
        if (StringUtils.isEmpty(message)) {
            return Response.error();
        }
        return new Response<T>(ResponseCode.FAIL.getCode(), message, null);
    }

    /**
     * invalid param
     *
     * @param <T>
     * @return
     */
    public static <T> Response<T> invalidParam() {
        return new Response<T>(ResponseCode.INVALID_PARAM.getCode(), ResponseCode.INVALID_PARAM.getMessage(), null);
    }

    /**
     * invalid param
     *
     * @param message
     * @param <T>
     * @return
     */
    public static <T> Response<T> invalidParam(String message) {
        if (StringUtils.isEmpty(message)) {
            message = ResponseCode.INVALID_PARAM.getMessage();
        }
        return new Response<T>(ResponseCode.INVALID_PARAM.getCode(), message, null);
    }


}
