package com.leadmaster.common.daoimpl;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.leadmaster.common.converter.AmenitiesConverter;
import com.leadmaster.common.dao.AmenitiesDao;
import com.leadmaster.common.domain.Amenities;
import com.leadmaster.common.dto.AmenitiesDTO;
import com.leadmaster.common.exception.ResourceNotFoundException;
import com.leadmaster.common.repository.AmenitiesRepository;

@Transactional
@Service("AmenitiesDaoImpl")
public class AmenitiesDaoImpl implements AmenitiesDao {

	@PersistenceContext
	private EntityManager entityManager;

	@Autowired
	private AmenitiesRepository amenitiesRepository;

	@Override
	public Amenities saveAmenities(AmenitiesDTO amenitiesDTO) {
		Amenities amenitiess = AmenitiesConverter.getAmenitiesByAmenitiesDTO(amenitiesDTO);
		return amenitiesRepository.save(amenitiess);
	}

	@Override
	public List<Amenities> getAllAmenities(AmenitiesDTO amenitiesDTO) {
		List<Amenities> returnList = null;
		StringBuffer sqlQuery = new StringBuffer("from Amenities a where 1=1");

		if (null != amenitiesDTO.getId())
			sqlQuery.append(" AND a.id = :id");
		if (null != amenitiesDTO.getStatus())
			sqlQuery.append(" AND a.status = :status");
		if (null != amenitiesDTO.getAmenity())
			sqlQuery.append(" AND a.amenity = :amenity");

		sqlQuery.append(" ORDER BY LOWER(a.amenity) ASC");
		Query query = entityManager.createQuery(sqlQuery.toString());

		if (null != amenitiesDTO.getId())
			query.setParameter("id", amenitiesDTO.getId());
		if (null != amenitiesDTO.getStatus())
			query.setParameter("status", amenitiesDTO.getStatus());
		if (null != amenitiesDTO.getAmenity())
			query.setParameter("amenity", amenitiesDTO.getAmenity());

		returnList = query.getResultList();

		return returnList;
	}

	@Override
	public Amenities getAmenitiesById(Long id) {
		Optional<Amenities> amenitiess = amenitiesRepository.findById(id);
		if (!amenitiess.isPresent())
			throw new ResourceNotFoundException("The amenities is not found in the system. id:" + id);
		return amenitiess.get();
	}

	@Override
	public List<Amenities> getAmenitiesByAmenity(String amenity) {
		return amenitiesRepository.getAmenitiesByAmenity(amenity);
	}

}
