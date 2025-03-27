package com.leadmaster.common.daoimpl;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.leadmaster.common.converter.PropertyValuesConverter;
import com.leadmaster.common.dao.PropertyValuesDao;
import com.leadmaster.common.domain.PropertyValues;
import com.leadmaster.common.dto.PropertyValuesDTO;
import com.leadmaster.common.exception.ResourceNotFoundException;
import com.leadmaster.common.repository.PropertyValuesRepository;

@Transactional
@Service("PropertyValuesDaoImpl")
public class PropertyValuesDaoImpl implements PropertyValuesDao {

	@PersistenceContext
	private EntityManager entityManager;

	@Autowired
	private PropertyValuesRepository propertyValuesRepository;

	@Override
	public PropertyValues savePropertyValue(PropertyValuesDTO propertyValuesDTO) {
		PropertyValues propertyValues = PropertyValuesConverter.getPropertyValueByPropertyValueDTO(propertyValuesDTO);
		return propertyValuesRepository.save(propertyValues);
	}

	@Override
	public List<PropertyValues> getAllPropertyValue(PropertyValuesDTO propertyValuesDTO) {
		List<PropertyValues> returnList = null;
		StringBuffer sqlQuery = new StringBuffer("from PropertyValues a where 1=1");

		if (null != propertyValuesDTO.getId())
			sqlQuery.append(" AND a.id = :id");
		if (null != propertyValuesDTO.getStatus())
			sqlQuery.append(" AND a.status = :status");
		if (null != propertyValuesDTO.getProjectName())
			sqlQuery.append(" AND a.projectName = :projectName");
		if (null != propertyValuesDTO.getLocation())
			sqlQuery.append(" AND a.location = :location");
		if (null != propertyValuesDTO.getSubLocation())
			sqlQuery.append(" AND a.subLocation = :subLocation");
		if (null != propertyValuesDTO.getCategory())
			sqlQuery.append(" AND a.category = :category");
		if (null != propertyValuesDTO.getType())
			sqlQuery.append(" AND a.type = :type");
		if (null != propertyValuesDTO.getApartmentType())
			sqlQuery.append(" AND a.apartmentType = :apartmentType");
		if (null != propertyValuesDTO.getLookingFor())
			sqlQuery.append(" AND a.lookingFor = :lookingFor");

		sqlQuery.append(" ORDER BY LOWER(a.projectName) ASC");

		Query query = entityManager.createQuery(sqlQuery.toString());

		if (null != propertyValuesDTO.getId())
			query.setParameter("id", propertyValuesDTO.getId());
		if (null != propertyValuesDTO.getStatus())
			query.setParameter("status", propertyValuesDTO.getStatus());
		if (null != propertyValuesDTO.getProjectName())
			query.setParameter("projectName", propertyValuesDTO.getProjectName());
		if (null != propertyValuesDTO.getLocation())
			query.setParameter("location", propertyValuesDTO.getLocation());
		if (null != propertyValuesDTO.getSubLocation())
			query.setParameter("subLocation", propertyValuesDTO.getSubLocation());
		if (null != propertyValuesDTO.getCategory())
			query.setParameter("category", propertyValuesDTO.getCategory());
		if (null != propertyValuesDTO.getType())
			query.setParameter("type", propertyValuesDTO.getType());
		if (null != propertyValuesDTO.getApartmentType())
			query.setParameter("apartmentType", propertyValuesDTO.getApartmentType());
		if (null != propertyValuesDTO.getLookingFor())
			query.setParameter("lookingFor", propertyValuesDTO.getLookingFor());

		returnList = query.getResultList();

		return returnList;
	}

	@Override
	public PropertyValues getPropertyValueById(Long id) {
		Optional<PropertyValues> propertyValues = propertyValuesRepository.findById(id);
		if (!propertyValues.isPresent())
			throw new ResourceNotFoundException("The propertyValue is not found in the system. id:" + id);
		return propertyValues.get();
	}

	@Override
	public List<PropertyValues> getPropertyValueData(PropertyValuesDTO propertyValuesDTO) {
		List<PropertyValues> returnList = null;
		StringBuffer sqlQuery = new StringBuffer("from PropertyValues a where 1=1");

		if (null != propertyValuesDTO.getId())
			sqlQuery.append(" AND a.id = :id");
		if (null != propertyValuesDTO.getStatus())
			sqlQuery.append(" AND a.status = :status");
		if (null != propertyValuesDTO.getProjectName())
			sqlQuery.append(" AND a.projectName = :projectName");
		if (null != propertyValuesDTO.getLocation())
			sqlQuery.append(" AND a.location = :location");
		if (null != propertyValuesDTO.getSubLocation())
			sqlQuery.append(" AND a.subLocation = :subLocation");
		if (null != propertyValuesDTO.getCategory())
			sqlQuery.append(" AND a.category = :category");
		if (null != propertyValuesDTO.getType())
			sqlQuery.append(" AND a.type = :type");
		if (null != propertyValuesDTO.getApartmentType())
			sqlQuery.append(" AND a.apartmentType = :apartmentType");
		if (null != propertyValuesDTO.getLookingFor())
			sqlQuery.append(" AND a.lookingFor = :lookingFor");

		sqlQuery.append(" GROUP BY a.projectName");
		sqlQuery.append(" ORDER BY LOWER(a.projectName) ASC");

		Query query = entityManager.createQuery(sqlQuery.toString());

		if (null != propertyValuesDTO.getId())
			query.setParameter("id", propertyValuesDTO.getId());
		if (null != propertyValuesDTO.getStatus())
			query.setParameter("status", propertyValuesDTO.getStatus());
		if (null != propertyValuesDTO.getProjectName())
			query.setParameter("projectName", propertyValuesDTO.getProjectName());
		if (null != propertyValuesDTO.getLocation())
			query.setParameter("location", propertyValuesDTO.getLocation());
		if (null != propertyValuesDTO.getSubLocation())
			query.setParameter("subLocation", propertyValuesDTO.getSubLocation());
		if (null != propertyValuesDTO.getCategory())
			query.setParameter("category", propertyValuesDTO.getCategory());
		if (null != propertyValuesDTO.getType())
			query.setParameter("type", propertyValuesDTO.getType());
		if (null != propertyValuesDTO.getApartmentType())
			query.setParameter("apartmentType", propertyValuesDTO.getApartmentType());
		if (null != propertyValuesDTO.getLookingFor())
			query.setParameter("lookingFor", propertyValuesDTO.getLookingFor());

		returnList = query.getResultList();

		return returnList;
	}

	@Override
	public List<Map<String, Object>> getPropertyValues(PropertyValuesDTO propertyValuesDTO) {
		List<Map<String, Object>> returnList = new ArrayList<>();
		StringBuilder sqlQuery = new StringBuilder(
				"SELECT a.id, a.category, a.type, a.project_name, a.location, a.sub_location, a.apartment_type, a.looking_for, "
						+ "a.budget_from, a.budget_to, a.possession, a.property_link "
						+ "FROM property_values a WHERE 1=1");

		if (propertyValuesDTO.getId() != null) {
			sqlQuery.append(" AND a.id = :id");
		}
		if (propertyValuesDTO.getStatus() != null) {
			sqlQuery.append(" AND a.status = :status");
		}
		if (propertyValuesDTO.getProjectName() != null) {
			sqlQuery.append(" AND a.project_name = :projectName");
		}
		if (propertyValuesDTO.getLocation() != null) {
			sqlQuery.append(" AND a.location = :location");
		}
		if (propertyValuesDTO.getSubLocation() != null) {
			sqlQuery.append(" AND a.sub_location = :subLocation");
		}
		if (propertyValuesDTO.getCategory() != null) {
			sqlQuery.append(" AND a.category = :category");
		}
		if (propertyValuesDTO.getType() != null) {
			sqlQuery.append(" AND a.type = :type");
		}
		if (propertyValuesDTO.getApartmentType() != null) {
			sqlQuery.append(" AND a.apartment_type = :apartmentType");
		}
		if (propertyValuesDTO.getLookingFor() != null) {
			sqlQuery.append(" AND a.looking_for = :lookingFor");
		}
		if (propertyValuesDTO.getPossession() != null) {
			sqlQuery.append(" AND a.possession = :possession");
		}

		// Handle budget conditions
		if (propertyValuesDTO.getBudgetFrom() != null) {
			sqlQuery.append(" AND a.budget_from >= :budgetFrom");
		}
		if (propertyValuesDTO.getBudgetTo() != null) {
			sqlQuery.append(" AND a.budget_to <= :budgetTo");
		}

		sqlQuery.append(" ORDER BY LOWER(a.project_name) ASC");

		Query query = entityManager.createNativeQuery(sqlQuery.toString());

		if (propertyValuesDTO.getId() != null) {
			query.setParameter("id", propertyValuesDTO.getId());
		}
		if (propertyValuesDTO.getStatus() != null) {
			query.setParameter("status", propertyValuesDTO.getStatus());
		}
		if (propertyValuesDTO.getProjectName() != null) {
			query.setParameter("projectName", propertyValuesDTO.getProjectName());
		}
		if (propertyValuesDTO.getLocation() != null) {
			query.setParameter("location", propertyValuesDTO.getLocation());
		}
		if (propertyValuesDTO.getSubLocation() != null) {
			query.setParameter("subLocation", propertyValuesDTO.getSubLocation());
		}
		if (propertyValuesDTO.getLookingFor() != null) {
			query.setParameter("lookingFor", propertyValuesDTO.getLookingFor());
		}
		if (propertyValuesDTO.getApartmentType() != null) {
			query.setParameter("apartmentType", propertyValuesDTO.getApartmentType());
		}
		if (propertyValuesDTO.getCategory() != null) {
			query.setParameter("category", propertyValuesDTO.getCategory());
		}
		if (propertyValuesDTO.getType() != null) {
			query.setParameter("type", propertyValuesDTO.getType());
		}
		if (propertyValuesDTO.getPossession() != null) {
			query.setParameter("possession", propertyValuesDTO.getPossession());
		}

		// Set budget parameters
		if (propertyValuesDTO.getBudgetFrom() != null) {
			query.setParameter("budgetFrom", propertyValuesDTO.getBudgetFrom());
		}
		if (propertyValuesDTO.getBudgetTo() != null) {
			query.setParameter("budgetTo", propertyValuesDTO.getBudgetTo());
		}

		List<Object[]> resultList = query.getResultList();

		for (Object[] result : resultList) {
			Map<String, Object> propertyValueMap = new LinkedHashMap<>();
			propertyValueMap.put("id", result[0]);
			propertyValueMap.put("category", result[1]);
			propertyValueMap.put("type", result[2]);
			propertyValueMap.put("projectName", result[3]);
			propertyValueMap.put("location", result[4]);
			propertyValueMap.put("subLocation", result[5]);
			propertyValueMap.put("apartmentType", result[6]);
			propertyValueMap.put("lookingFor", result[7]);
			propertyValueMap.put("budgetFrom", result[8]);
			propertyValueMap.put("budgetTo", result[9]);
			propertyValueMap.put("possession", result[10]);
			propertyValueMap.put("propertyLink", result[11]);

			returnList.add(propertyValueMap);
		}

		return returnList;
	}

}
