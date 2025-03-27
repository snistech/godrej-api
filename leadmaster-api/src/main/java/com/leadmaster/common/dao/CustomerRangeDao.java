package com.leadmaster.common.dao;

import java.util.List;

import com.leadmaster.common.domain.CustomerRange;
import com.leadmaster.common.dto.CustomerRangeDTO;

public interface CustomerRangeDao {

	public CustomerRange saveCustomerRange(CustomerRangeDTO customerRangeDTO);

	public List<CustomerRange> getAllCustomerRange(CustomerRangeDTO customerRangeDTO);

	public CustomerRange getCustomerRangeById(Long id);

	public List<CustomerRange> getCustomerRangeByRange(String range);

}
