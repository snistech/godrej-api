package com.leadmaster.common.dao;

import java.util.List;

import com.leadmaster.common.domain.PropertyApproval;
import com.leadmaster.common.dto.PropertyApprovalDTO;

public interface PropertyApprovalDao {

	public PropertyApproval savePropertyApproval(PropertyApprovalDTO propertyApprovalDTO);

	public List<PropertyApproval> getAllPropertyApproval(PropertyApprovalDTO propertyApprovalDTO);

	public PropertyApproval getPropertyApprovalById(Long id);

	public List<PropertyApproval> getPropertyByApproval(String approval);

}
