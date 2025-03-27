package com.leadmaster.common.dao;

import java.util.List;

import com.leadmaster.common.domain.CustomerType;
import com.leadmaster.common.dto.CustomerTypeDTO;

public interface CustomerTypeDao {

	public CustomerType saveCustomerType(CustomerTypeDTO customerTypeDTO);

	public List<CustomerType> getAllCustomerType(CustomerTypeDTO customerTypeDTO);

	public CustomerType getCustomerTypeById(Long id);

	public List<CustomerType> getCustomerTypeByType(String type);

}
