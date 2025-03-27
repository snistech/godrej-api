package com.leadmaster.common.daoimpl;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.leadmaster.common.converter.AlertConverter;
import com.leadmaster.common.dao.AlertDao;
import com.leadmaster.common.domain.Alert;
import com.leadmaster.common.dto.AlertDTO;
import com.leadmaster.common.exception.ResourceNotFoundException;
import com.leadmaster.common.repository.AlertRepository;

@Transactional
@Service("AlertDaoImpl")
public class AlertDaoImpl implements AlertDao {

	@PersistenceContext
	private EntityManager entityManager;

	@Autowired
	private AlertRepository alertRepository;

	@Override
	public Alert saveAlert(AlertDTO alertDTO) {
		Alert alerts = AlertConverter.getAlertByAlertDTO(alertDTO);
		return alertRepository.save(alerts);
	}

	@Override
	public List<Alert> getAllAlert(AlertDTO alertDTO) {
		List<Alert> returnList = null;
		StringBuffer sqlQuery = new StringBuffer("from Alert a where 1=1");

		if (null != alertDTO.getId())
			sqlQuery.append(" AND a.id = :id");
		if (null != alertDTO.getAlertStatus())
			sqlQuery.append(" AND a.alertStatus = :alertStatus");
		if (null != alertDTO.getStatus())
			sqlQuery.append(" AND a.status = :status");
		if (null != alertDTO.getBranch())
			sqlQuery.append(" AND a.branch LIKE CONCAT('%', :branch, '%')");
		if (null != alertDTO.getRole())
			sqlQuery.append(" AND a.role LIKE CONCAT('%', :role, '%')");
		if (null != alertDTO.getCreatedBy())
			sqlQuery.append(" AND a.createdBy = :createdBy");

		sqlQuery.append(" ORDER BY a.id DESC");
		Query query = entityManager.createQuery(sqlQuery.toString());

		if (null != alertDTO.getId())
			query.setParameter("id", alertDTO.getId());
		if (null != alertDTO.getAlertStatus())
			query.setParameter("alertStatus", alertDTO.getAlertStatus());
		if (null != alertDTO.getStatus())
			query.setParameter("status", alertDTO.getStatus());
		if (null != alertDTO.getBranch())
			query.setParameter("branch", alertDTO.getBranch());
		if (null != alertDTO.getRole())
			query.setParameter("role", alertDTO.getRole());
		if (null != alertDTO.getCreatedBy())
			query.setParameter("createdBy", alertDTO.getCreatedBy());

		returnList = query.getResultList();

		return returnList;
	}

	@Override
	public Alert getAlertById(Long id) {
		Optional<Alert> alerts = alertRepository.findById(id);
		if (!alerts.isPresent())
			throw new ResourceNotFoundException("The alert is not found in the system. id:" + id);
		return alerts.get();
	}

}
