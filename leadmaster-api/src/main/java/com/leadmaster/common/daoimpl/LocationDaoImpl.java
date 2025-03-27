package com.leadmaster.common.daoimpl;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.leadmaster.common.converter.LocationConverter;
import com.leadmaster.common.dao.LocationDao;
import com.leadmaster.common.domain.Location;
import com.leadmaster.common.dto.LocationDTO;
import com.leadmaster.common.exception.ResourceNotFoundException;
import com.leadmaster.common.repository.LocationRepository;

@Transactional
@Service("LocationDaoImpl")
public class LocationDaoImpl implements LocationDao {

	@PersistenceContext
	private EntityManager entityManager;

	@Autowired
	private LocationRepository locationRepository;

	@Override
	public Location saveLocation(LocationDTO locationDTO) {
		Location locations = LocationConverter.getLocationByLocationDTO(locationDTO);
		return locationRepository.save(locations);
	}

	@Override
	public List<Location> getAllLocation(LocationDTO locationDTO) {
		List<Location> returnList = null;
		StringBuffer sqlQuery = new StringBuffer("from Location a where 1=1");

		if (null != locationDTO.getId())
			sqlQuery.append(" AND a.id = :id");
		if (null != locationDTO.getStatus())
			sqlQuery.append(" AND a.status = :status");
		if (null != locationDTO.getLocation())
			sqlQuery.append(" AND a.location = :location");

		sqlQuery.append(" ORDER BY LOWER(a.location) ASC");
		Query query = entityManager.createQuery(sqlQuery.toString());

		if (null != locationDTO.getId())
			query.setParameter("id", locationDTO.getId());
		if (null != locationDTO.getStatus())
			query.setParameter("status", locationDTO.getStatus());
		if (null != locationDTO.getLocation())
			query.setParameter("location", locationDTO.getLocation());

		returnList = query.getResultList();

		return returnList;
	}

	@Override
	public Location getLocationById(Long id) {
		Optional<Location> locations = locationRepository.findById(id);
		if (!locations.isPresent())
			throw new ResourceNotFoundException("The location is not found in the system. id:" + id);
		return locations.get();
	}

	@Override
	public List<Location> getLocationByName(String location) {
		return locationRepository.getLocationByName(location);
	}

	@Override
	public Map<String, Object> getLocationsCategoryWise(LocationDTO locationDTO) {
		Map<String, Object> response = new LinkedHashMap<>();
		List<Map<String, Object>> locationsList = new ArrayList<>();
		StringBuilder sqlQuery = new StringBuilder("SELECT a.id, a.location, a.sub_location FROM location a WHERE 1=1");

		if (null != locationDTO.getId()) {
			sqlQuery.append(" AND a.id = :id");
		}
		if (null != locationDTO.getLocation()) {
			sqlQuery.append(" AND a.location = :location");
		}

		sqlQuery.append(" ORDER BY LOWER(a.sub_location) ASC");

		Query query = entityManager.createNativeQuery(sqlQuery.toString());

		if (null != locationDTO.getId()) {
			query.setParameter("id", locationDTO.getId());
		}
		if (null != locationDTO.getLocation()) {
			query.setParameter("location", locationDTO.getLocation());
		}

		List<Object[]> resultList = query.getResultList();
		Map<String, Set<String>> locationMap = new LinkedHashMap<>();

		for (Object[] result : resultList) {
			String locationName = (String) result[1];
			String subLocationName = (String) result[2];

			locationMap.computeIfAbsent(locationName, k -> new LinkedHashSet<>()).add(subLocationName);
		}

		for (Map.Entry<String, Set<String>> entry : locationMap.entrySet()) {
			Map<String, Object> locationData = new LinkedHashMap<>();
			locationData.put("locationName", entry.getKey());
			locationData.put("subLocations", new ArrayList<>(entry.getValue()));
			locationsList.add(locationData);
		}

		response.put("locations", locationsList);

		return response;
	}

}
