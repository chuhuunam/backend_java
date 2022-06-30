package com.example.backend_java.domain.response;

import java.util.List;

public class PageResponse<T> {
    private Integer pageIndex;
    private Integer pageSize;
    private Long allRow;
    private List<T> data;

    public PageResponse(Integer pageIndex, Integer pageSize, Long allRow, List<T> data) {
        super();
        this.pageIndex = pageIndex;
        this.pageSize = pageSize;
        this.allRow = allRow;
        this.data = data;
    }

    public Integer getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(Integer pageIndex) {
        this.pageIndex = pageIndex;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Long getAllRow() {
        return allRow;
    }

    public void setAllRow(Long allRow) {
        this.allRow = allRow;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }
}
