package com.baidu.gcrm.common.page;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

/**
 * 分页类，符合json规范
 * @param <T>
 * @param <T>
 *
 */
public class Page<T> extends PageWrapper<T> implements Pageable{
    
    private static final long serialVersionUID = 391439701675288793L;

    private long totalCount;
    
    private List<T> result = new ArrayList<T>();
    
    private long totalPages;
    
    public Page() {}
    
    public Page(int page, int size) {
        super(page, size);
    }


    @Override
    public Sort getSort() {
        return null;
    }

	public long getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(long totalCount) {
		this.totalCount = totalCount;
	}

	public List<T> getResult() {
		return result;
	}

	public void setResult(List<T> result) {
		this.result = result;
	}

	public long getTotalPages() {
		totalPages =  (totalCount+this.getPageSize()-1) / this.getPageSize();
		return totalPages;
	}

	public void setTotalPages(long totalPages) {
		this.totalPages = totalPages;
	}
	
}
