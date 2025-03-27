package com.leadmaster.common.converter;

import org.springframework.stereotype.Component;

import com.leadmaster.common.domain.CustomerType;
import com.leadmaster.common.dto.CustomerTypeDTO;

@Component
public class CustomerTypeConverter {

	public static CustomerType getCustomerTypeByCustomerTypeDTO(CustomerTypeDTO customerTypeDTO) {
		CustomerType customerType = new CustomerType();
		customerType.setId(customerTypeDTO.getId());
		customerType.setType(customerTypeDTO.getType());
		customerType.setStatus(customerTypeDTO.getStatus());
		customerType.setCreatedDate(customerTypeDTO.getCreatedDate());
		customerType.setCreatedBy(customerTypeDTO.getCreatedBy());
		customerType.setUpdatedDate(customerTypeDTO.getUpdatedDate());
		customerType.setUpdatedBy(customerTypeDTO.getUpdatedBy());

		return customerType;
	}

	public static CustomerTypeDTO getCustomerTypeDTOByCustomerType(CustomerType customerType) {
		CustomerTypeDTO dto = new CustomerTypeDTO();

		dto.setId(customerType.getId());
		dto.setType(customerType.getType());
		dto.setStatus(customerType.getStatus());
		dto.setCreatedDate(customerType.getCreatedDate());
		dto.setCreatedBy(customerType.getCreatedBy());
		dto.setUpdatedDate(customerType.getUpdatedDate());
		dto.setUpdatedBy(customerType.getUpdatedBy());

		return dto;
	}

}
