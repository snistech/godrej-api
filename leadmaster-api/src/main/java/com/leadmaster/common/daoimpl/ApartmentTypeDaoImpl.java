package com.leadmaster.common.daoimpl;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.leadmaster.common.converter.ApartmentTypeConverter;
import com.leadmaster.common.dao.ApartmentTypeDao;
import com.leadmaster.common.domain.ApartmentType;
import com.leadmaster.common.dto.ApartmentTypeDTO;
import com.leadmaster.common.exception.ResourceNotFoundException;
import com.leadmaster.common.repository.ApartmentTypeRepository;

@Transactional
@Service("ApartmentTypeDaoImpl")
public class ApartmentTypeDaoImpl implements ApartmentTypeDao {

	@PersistenceContext
	private EntityManager entityManager;

	@Autowired
	private ApartmentTypeRepository apartmentTypeRepository;

	@Override
	public ApartmentType saveApartmentType(ApartmentTypeDTO apartmentTypeDTO) {
		ApartmentType apartmentTypes = ApartmentTypeConverter.getApartmentTypeByApartmentTypeDTO(apartmentTypeDTO);
		return apartmentTypeRepository.save(apartmentTypes);
	}

	@Override
	public List<ApartmentType> getAllApartmentType(ApartmentTypeDTO apartmentTypeDTO) {
		List<ApartmentType> returnList = null;
		StringBuffer sqlQuery = new StringBuffer("from ApartmentType a where 1=1");

		if (null != apartmentTypeDTO.getId())
			sqlQuery.append(" AND a.id = :id");
		if (null != apartmentTypeDTO.getStatus())
			sqlQuery.append(" AND a.status = :status");
		if (null != apartmentTypeDTO.getType())
			sqlQuery.append(" AND a.type = :type");

		sqlQuery.append(" ORDER BY LOWER(a.type) ASC");
		Query query = entityManager.createQuery(sqlQuery.toString());

		if (null != apartmentTypeDTO.getId())
			query.setParameter("id", apartmentTypeDTO.getId());
		if (null != apartmentTypeDTO.getStatus())
			query.setParameter("status", apartmentTypeDTO.getStatus());
		if (null != apartmentTypeDTO.getType())
			query.setParameter("type", apartmentTypeDTO.getType());

		returnList = query.getResultList();

		return returnList;
	}

	@Override
	public ApartmentType getApartmentTypeById(Long id) {
		Optional<ApartmentType> apartmentTypes = apartmentTypeRepository.findById(id);
		if (!apartmentTypes.isPresent())
			throw new ResourceNotFoundException("The apartmentType is not found in the system. id:" + id);
		return apartmentTypes.get();
	}

	@Override
	public List<ApartmentType> getApartmentTypeByType(String type) {
		return apartmentTypeRepository.getApartmentTypeByType(type);
	}

}
