package com.baidu.gcrm.common.page;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

/**
 * 客户列表查询条件
 * @param <T>
 * @param <T>
 *
 */
public class PageWrapper<T> extends PageRequest{
    
    

    private static final long serialVersionUID = -370271343965696101L;

    private int pageNumber = 1;
    
    private int pageSize = 20;
    
    private long total;
    
    private List<T> content = new ArrayList<T>();
    
    protected Class<T> resultClass;
    
    private boolean isQueryAll = false;
    
    private List<Long> operatorIdList = new ArrayList<Long>();//数据权限控制列表查询的操作人id列表
    
    public List<Long> getOperatorIdList() {
		return operatorIdList;
	}

	public void setOperatorIdList(List<Long> operatorIdList) {
		this.operatorIdList = operatorIdList;
	}

	public PageWrapper() {
        super(0, 20);
    }
    
    public PageWrapper(int page, int size) {
        super(page, size);
    }

    @Override
    public int getPageNumber() {
        return pageNumber;
    }

    @Override
    public int getPageSize() {
        return pageSize;
    }

    @Override
    public int getOffset() {
        return (pageNumber - 1) * pageSize;
    }

    @Override
    public Sort getSort() {
        return null;
    }
    
    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public List<T> getContent() {
        return content;
    }

    public void setContent(List<T> resultList) {
        this.content = resultList;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public Class<T> getResultClass() {
//        Class <T> entityClass = (Class <T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        return resultClass;
    }

    public void setResultClass(Class<T> resultClass) {
        this.resultClass = resultClass;
    }

    public boolean isQueryAll() {
        return isQueryAll;
    }

    public void setQueryAll(boolean isQueryAll) {
        this.isQueryAll = isQueryAll;
    }

	
}
