package com.leadmaster.service.service;

import java.util.List;

import com.leadmaster.common.domain.CustomerRange;
import com.leadmaster.common.dto.CustomerRangeDTO;

public interface CustomerRangeService {

	public void saveCustomerRange(CustomerRangeDTO customerRangeDTO);

	public List<CustomerRange> getAllCustomerRanges(CustomerRangeDTO customerRangeDTO);

	public CustomerRange getCustomerRangeById(CustomerRangeDTO customerRangeDTO);

	public void updateCustomerRange(CustomerRangeDTO customerRangeDTO);

	public CustomerRange deleteCustomerRange(CustomerRangeDTO customerRangeDTO);

}
