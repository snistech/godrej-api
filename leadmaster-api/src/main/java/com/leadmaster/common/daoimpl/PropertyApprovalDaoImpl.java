package com.leadmaster.common.daoimpl;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.leadmaster.common.converter.PropertyApprovalConverter;
import com.leadmaster.common.dao.PropertyApprovalDao;
import com.leadmaster.common.domain.PropertyApproval;
import com.leadmaster.common.dto.PropertyApprovalDTO;
import com.leadmaster.common.exception.ResourceNotFoundException;
import com.leadmaster.common.repository.PropertyApprovalRepository;

@Transactional
@Service("PropertyApprovalDaoImpl")
public class PropertyApprovalDaoImpl implements PropertyApprovalDao {

	@PersistenceContext
	private EntityManager entityManager;

	@Autowired
	private PropertyApprovalRepository propertyApprovalRepository;

	@Override
	public PropertyApproval savePropertyApproval(PropertyApprovalDTO propertyApprovalDTO) {
		PropertyApproval propertyApprovals = PropertyApprovalConverter
				.getPropertyApprovalByPropertyApprovalDTO(propertyApprovalDTO);
		return propertyApprovalRepository.save(propertyApprovals);
	}

	@Override
	public List<PropertyApproval> getAllPropertyApproval(PropertyApprovalDTO propertyApprovalDTO) {
		List<PropertyApproval> returnList = null;
		StringBuffer sqlQuery = new StringBuffer("from PropertyApproval a where 1=1");

		if (null != propertyApprovalDTO.getId())
			sqlQuery.append(" AND a.id = :id");
		if (null != propertyApprovalDTO.getStatus())
			sqlQuery.append(" AND a.status = :status");
		if (null != propertyApprovalDTO.getApproval())
			sqlQuery.append(" AND a.approval = :approval");

		sqlQuery.append(" ORDER BY LOWER(a.approval) ASC");
		Query query = entityManager.createQuery(sqlQuery.toString());

		if (null != propertyApprovalDTO.getId())
			query.setParameter("id", propertyApprovalDTO.getId());
		if (null != propertyApprovalDTO.getStatus())
			query.setParameter("status", propertyApprovalDTO.getStatus());
		if (null != propertyApprovalDTO.getApproval())
			query.setParameter("approval", propertyApprovalDTO.getApproval());

		returnList = query.getResultList();

		return returnList;
	}

	@Override
	public PropertyApproval getPropertyApprovalById(Long id) {
		Optional<PropertyApproval> propertyApprovals = propertyApprovalRepository.findById(id);
		if (!propertyApprovals.isPresent())
			throw new ResourceNotFoundException("The propertyApproval is not found in the system. id:" + id);
		return propertyApprovals.get();
	}

	@Override
	public List<PropertyApproval> getPropertyByApproval(String approval) {
		return propertyApprovalRepository.getPropertyByApproval(approval);
	}

}
