package com.leadmaster.common.daoimpl;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.leadmaster.common.converter.PlotSizeConverter;
import com.leadmaster.common.dao.PlotSizeDao;
import com.leadmaster.common.domain.PlotSize;
import com.leadmaster.common.dto.PlotSizeDTO;
import com.leadmaster.common.exception.ResourceNotFoundException;
import com.leadmaster.common.repository.PlotSizeRepository;

@Transactional
@Service("PlotSizeDaoImpl")
public class PlotSizeDaoImpl implements PlotSizeDao {

	@PersistenceContext
	private EntityManager entityManager;

	@Autowired
	private PlotSizeRepository plotSizeRepository;

	@Override
	public PlotSize savePlotSize(PlotSizeDTO plotSizeDTO) {
		PlotSize plotSizes = PlotSizeConverter.getPlotSizeByPlotSizeDTO(plotSizeDTO);
		return plotSizeRepository.save(plotSizes);
	}

	@Override
	public List<PlotSize> getAllPlotSize(PlotSizeDTO plotSizeDTO) {
		List<PlotSize> returnList = null;
		StringBuffer sqlQuery = new StringBuffer("from PlotSize a where 1=1");

		if (null != plotSizeDTO.getId())
			sqlQuery.append(" AND a.id = :id");
		if (null != plotSizeDTO.getStatus())
			sqlQuery.append(" AND a.status = :status");
		if (null != plotSizeDTO.getSize())
			sqlQuery.append(" AND a.size = :size");

		sqlQuery.append(" ORDER BY a.size ASC");
		Query query = entityManager.createQuery(sqlQuery.toString());

		if (null != plotSizeDTO.getId())
			query.setParameter("id", plotSizeDTO.getId());
		if (null != plotSizeDTO.getStatus())
			query.setParameter("status", plotSizeDTO.getStatus());
		if (null != plotSizeDTO.getSize())
			query.setParameter("size", plotSizeDTO.getSize());

		returnList = query.getResultList();

		return returnList;
	}

	@Override
	public PlotSize getPlotSizeById(Long id) {
		Optional<PlotSize> plotSizes = plotSizeRepository.findById(id);
		if (!plotSizes.isPresent())
			throw new ResourceNotFoundException("The plotSize is not found in the system. id:" + id);
		return plotSizes.get();
	}

	@Override
	public List<PlotSize> getPlotBySize(String size) {
		return plotSizeRepository.getPlotBySize(size);
	}

}
