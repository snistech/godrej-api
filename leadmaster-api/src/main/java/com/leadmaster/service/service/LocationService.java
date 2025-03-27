package com.leadmaster.service.service;

import java.util.List;
import java.util.Map;

import com.leadmaster.common.domain.Location;
import com.leadmaster.common.dto.LocationDTO;

public interface LocationService {

	public void saveLocation(LocationDTO locationDTO);

	public List<Location> getAllLocations(LocationDTO locationDTO);

	public Location getLocationById(LocationDTO locationDTO);

	public void updateLocation(LocationDTO locationDTO);

	public Location deleteLocation(LocationDTO locationDTO);

	public Map<String, Object> getLocationsCategoryWise(LocationDTO locationDTO);

}
