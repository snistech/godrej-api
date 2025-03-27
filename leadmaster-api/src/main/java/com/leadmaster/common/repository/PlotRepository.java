package com.leadmaster.common.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.leadmaster.common.domain.Plot;

public interface PlotRepository extends JpaRepository<Plot, Long> {

	@Query("SELECT u FROM Plot u WHERE LOWER(u.plotName) = LOWER(:plotName) AND status = 'Active'")
	List<Plot> getPlotByName(@Param("plotName") String plotName);

}
