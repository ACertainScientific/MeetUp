package com.acertainscientific.meetup.dto;



import lombok.Data;

import java.util.List;

@Data

/**
 * 页面返回DTO
 * @author Chen Changdi
 * @date 2021/4/9
 */
public class PageResponseDto<T> {
    /**
     * 总数目
     */
    private Integer totalCount;
    /**
     * 总页数
     */
    private Integer pageCount;
    /**
     * 当前页数
     */
    private Integer currentPage;
    /**
     * 页大小
     */
    private Integer perPage;
    /**
     * 内容
     */
    private List<T> list;
}

