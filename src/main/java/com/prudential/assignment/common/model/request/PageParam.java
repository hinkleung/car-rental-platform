package com.prudential.assignment.common.model.request;

import lombok.Data;

/**
 * page params
 */
@Data
public class PageParam {
    
    /**
     * current page
     */
    private Integer currentPage;
    
    /**
     * page size
     */
    private Integer pageSize;

}
