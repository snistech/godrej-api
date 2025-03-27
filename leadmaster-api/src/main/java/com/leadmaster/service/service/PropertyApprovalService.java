package com.leadmaster.service.service;

import java.util.List;

import com.leadmaster.common.domain.PropertyApproval;
import com.leadmaster.common.dto.PropertyApprovalDTO;

public interface PropertyApprovalService {

	public void savePropertyApproval(PropertyApprovalDTO propertyApprovalDTO);

	public List<PropertyApproval> getAllPropertyApprovals(PropertyApprovalDTO propertyApprovalDTO);

	public PropertyApproval getPropertyApprovalById(PropertyApprovalDTO propertyApprovalDTO);

	public void updatePropertyApproval(PropertyApprovalDTO propertyApprovalDTO);

	public PropertyApproval deletePropertyApproval(PropertyApprovalDTO propertyApprovalDTO);

}
