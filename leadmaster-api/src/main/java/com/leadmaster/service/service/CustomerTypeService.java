package com.leadmaster.service.service;

import java.util.List;

import com.leadmaster.common.domain.CustomerType;
import com.leadmaster.common.dto.CustomerTypeDTO;

public interface CustomerTypeService {

	public void saveCustomerType(CustomerTypeDTO customerTypeDTO);

	public List<CustomerType> getAllCustomerTypes(CustomerTypeDTO customerTypeDTO);

	public CustomerType getCustomerTypeById(CustomerTypeDTO customerTypeDTO);

	public void updateCustomerType(CustomerTypeDTO customerTypeDTO);

	public CustomerType deleteCustomerType(CustomerTypeDTO customerTypeDTO);

}
