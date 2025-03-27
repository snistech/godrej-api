package com.leadmaster.common.converter;

import org.springframework.stereotype.Component;

import com.leadmaster.common.domain.CustomerRange;
import com.leadmaster.common.dto.CustomerRangeDTO;

@Component
public class CustomerRangeConverter {

	public static CustomerRange getCustomerRangeByCustomerRangeDTO(CustomerRangeDTO customerRangeDTO) {
		CustomerRange customerRange = new CustomerRange();
		customerRange.setId(customerRangeDTO.getId());
		customerRange.setCustomerRange(customerRangeDTO.getCustomerRange());
		customerRange.setStatus(customerRangeDTO.getStatus());
		customerRange.setCreatedDate(customerRangeDTO.getCreatedDate());
		customerRange.setCreatedBy(customerRangeDTO.getCreatedBy());
		customerRange.setUpdatedDate(customerRangeDTO.getUpdatedDate());
		customerRange.setUpdatedBy(customerRangeDTO.getUpdatedBy());

		return customerRange;
	}

	public static CustomerRangeDTO getCustomerRangeDTOByCustomerRange(CustomerRange customerRange) {
		CustomerRangeDTO dto = new CustomerRangeDTO();

		dto.setId(customerRange.getId());
		dto.setCustomerRange(customerRange.getCustomerRange());
		dto.setStatus(customerRange.getStatus());
		dto.setCreatedDate(customerRange.getCreatedDate());
		dto.setCreatedBy(customerRange.getCreatedBy());
		dto.setUpdatedDate(customerRange.getUpdatedDate());
		dto.setUpdatedBy(customerRange.getUpdatedBy());

		return dto;
	}

}
