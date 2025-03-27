package com.leadmaster.common.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.leadmaster.common.domain.PlotSize;

public interface PlotSizeRepository extends JpaRepository<PlotSize, Long> {

	@Query("SELECT u FROM PlotSize u WHERE LOWER(u.size) = LOWER(:size) AND status = 'Active'")
	List<PlotSize> getPlotBySize(@Param("size") String size);

}
