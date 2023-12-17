package com.asmt.ssu.repository.custom;

import java.util.List;

import com.asmt.ssu.domain.Menu;
import com.asmt.ssu.domain.School;

public interface RankRepositoryCustom {
	List<Menu> findRankTopN(int n, School school);

	void save(Long menuId);
}
