package com.baidu.gcrm.ad.statistic.web.vo;

public class BasePageVO {
    
    private int pageNumber;
    private int pageSize;
    private int totalCount;
    
    public int getPageNumber() {
        return pageNumber;
    }
    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }
    public int getPageSize() {
        return pageSize;
    }
    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }
    public int getTotalCount() {
        return totalCount;
    }
    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }
    public int getTotalPages() {
        if (pageSize < 1) {
            return 0;
        }
        return (totalCount + pageSize - 1)/pageSize;
    }
    
}
