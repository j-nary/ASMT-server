package com.asmt.ssu.repository.custom;

import java.util.List;

import com.asmt.ssu.domain.SearchDTO;
import com.asmt.ssu.form.SearchForm;

public interface SearchRepositoryCustom {
	List<SearchDTO> findResultByLowPrice(SearchForm searchForm);
}
