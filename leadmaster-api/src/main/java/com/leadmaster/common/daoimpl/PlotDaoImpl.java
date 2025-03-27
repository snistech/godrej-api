package com.leadmaster.common.daoimpl;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.leadmaster.common.converter.PlotConverter;
import com.leadmaster.common.dao.PlotDao;
import com.leadmaster.common.domain.Plot;
import com.leadmaster.common.dto.PlotDTO;
import com.leadmaster.common.exception.ResourceNotFoundException;
import com.leadmaster.common.repository.PlotRepository;

@Transactional
@Service("PlotDaoImpl")
public class PlotDaoImpl implements PlotDao {

	@PersistenceContext
	private EntityManager entityManager;

	@Autowired
	private PlotRepository plotRepository;

	@Override
	public Plot savePlot(PlotDTO plotDTO) {
		Plot plots = PlotConverter.getPlotByPlotDTO(plotDTO);
		return plotRepository.save(plots);
	}

	@Override
	public List<Plot> getAllPlot(PlotDTO plotDTO) {
		List<Plot> returnList = null;
		StringBuffer sqlQuery = new StringBuffer("from Plot a where 1=1");

		if (null != plotDTO.getId())
			sqlQuery.append(" AND a.id = :id");
		if (null != plotDTO.getStatus())
			sqlQuery.append(" AND a.status = :status");
		if (null != plotDTO.getPlotName())
			sqlQuery.append(" AND a.plot_name = :plotName");

		sqlQuery.append(" ORDER BY LOWER(a.plotName) ASC");
		Query query = entityManager.createQuery(sqlQuery.toString());

		if (null != plotDTO.getId())
			query.setParameter("id", plotDTO.getId());
		if (null != plotDTO.getStatus())
			query.setParameter("status", plotDTO.getStatus());
		if (null != plotDTO.getPlotName())
			query.setParameter("plotName", plotDTO.getPlotName());

		returnList = query.getResultList();

		return returnList;
	}

	@Override
	public Plot getPlotById(Long id) {
		Optional<Plot> plots = plotRepository.findById(id);
		if (!plots.isPresent())
			throw new ResourceNotFoundException("The plot is not found in the system. id:" + id);
		return plots.get();
	}

	@Override
	public List<Plot> getPlotByName(String plotName) {
		return plotRepository.getPlotByName(plotName);
	}

}
