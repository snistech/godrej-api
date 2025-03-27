package com.leadmaster.common.daoimpl;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.leadmaster.common.converter.PropertyTypeConverter;
import com.leadmaster.common.dao.PropertyTypeDao;
import com.leadmaster.common.domain.PropertyType;
import com.leadmaster.common.dto.PropertyTypeDTO;
import com.leadmaster.common.exception.ResourceNotFoundException;
import com.leadmaster.common.repository.PropertyTypeRepository;

@Transactional
@Service("PropertyTypeDaoImpl")
public class PropertyTypeDaoImpl implements PropertyTypeDao {

	@PersistenceContext
	private EntityManager entityManager;

	@Autowired
	private PropertyTypeRepository propertyTypeRepository;

	@Override
	public PropertyType savePropertyType(PropertyTypeDTO propertyTypeDTO) {
		PropertyType propertyTypes = PropertyTypeConverter.getPropertyTypeByPropertyTypeDTO(propertyTypeDTO);
		return propertyTypeRepository.save(propertyTypes);
	}

	@Override
	public List<PropertyType> getAllPropertyType(PropertyTypeDTO propertyTypeDTO) {
		List<PropertyType> returnList = null;
		StringBuffer sqlQuery = new StringBuffer("from PropertyType a where 1=1");

		if (null != propertyTypeDTO.getId())
			sqlQuery.append(" AND a.id = :id");
		if (null != propertyTypeDTO.getStatus())
			sqlQuery.append(" AND a.status = :status");
		if (null != propertyTypeDTO.getPropertyType())
			sqlQuery.append(" AND a.propertyType = :propertyType");

		sqlQuery.append(" ORDER BY a.id DESC");
		Query query = entityManager.createQuery(sqlQuery.toString());

		if (null != propertyTypeDTO.getId())
			query.setParameter("id", propertyTypeDTO.getId());
		if (null != propertyTypeDTO.getStatus())
			query.setParameter("status", propertyTypeDTO.getStatus());
		if (null != propertyTypeDTO.getPropertyType())
			query.setParameter("propertyType", propertyTypeDTO.getPropertyType());

		returnList = query.getResultList();

		return returnList;
	}

	@Override
	public PropertyType getPropertyTypeById(Long id) {
		Optional<PropertyType> propertyTypes = propertyTypeRepository.findById(id);
		if (!propertyTypes.isPresent())
			throw new ResourceNotFoundException("The propertyType is not found in the system. id:" + id);
		return propertyTypes.get();
	}

	@Override
	public List<PropertyType> getPropertyTypeByType(String type) {
		return propertyTypeRepository.getPropertyTypeByType(type);
	}

}
