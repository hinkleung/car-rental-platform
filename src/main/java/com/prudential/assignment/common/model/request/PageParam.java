package com.prudential.assignment.common.model.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * page params
 */
@Data
@ApiModel("分页参数")
public class PageParam {

    /**
     * current page
     */
    @ApiModelProperty(value = "当前页数，默认1")
    private Integer currentPage;

    /**
     * page size
     */
    @ApiModelProperty("页大小，默认10")
    private Integer pageSize;

}
