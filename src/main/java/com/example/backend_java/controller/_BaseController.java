package com.example.backend_java.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class _BaseController {
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    protected Integer validPageIndex(Integer pageIndex) {
        if (pageIndex == null || pageIndex < 1) {
            pageIndex = 1;
        }
        return pageIndex;
    }
    protected Integer validPageSize(Integer pageSize) {
        if (pageSize == null || pageSize < 1 || pageSize > 50) {
            pageSize = 10;
        }
        return pageSize;
    }
}
