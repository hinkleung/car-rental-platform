package com.prudential.assignment.common.model.response;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;
import java.util.List;

/**
 * page response object
 * @param <T>
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class PageResponse<T> extends Response<T> {

    private long total;

    public PageResponse() {
        super();
        this.total = 0L;
    }

    /**
     * @param list  list data
     * @param total number
     */
    public static <T extends List> PageResponse<T> success(T list, long total) {
        PageResponse<T> pageResponse = new PageResponse<>();
        pageResponse.setData(list);
        pageResponse.setTotal(total);
        return pageResponse;
    }

    public static <T> PageResponse<T> success() {
        return new PageResponse<>();
    }

}
