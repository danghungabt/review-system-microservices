package com.microservice.reviewservice.paging;


import com.microservice.reviewservice.sort.Sorter;

public interface Pageable {
	Integer getPage();
	Integer getOffset();
	Integer getLimit();
	Sorter getSorter();
}
