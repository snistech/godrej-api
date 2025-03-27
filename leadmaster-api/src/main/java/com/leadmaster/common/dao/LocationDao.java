package com.leadmaster.common.dao;

import java.util.List;
import java.util.Map;

import com.leadmaster.common.domain.Location;
import com.leadmaster.common.dto.LocationDTO;

public interface LocationDao {

	public Location saveLocation(LocationDTO locationDTO);

	public List<Location> getAllLocation(LocationDTO locationDTO);

	public Location getLocationById(Long id);

	public List<Location> getLocationByName(String location);

	public Map<String, Object> getLocationsCategoryWise(LocationDTO locationDTO);

}
