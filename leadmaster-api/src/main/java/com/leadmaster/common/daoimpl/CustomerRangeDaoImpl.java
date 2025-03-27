package com.leadmaster.common.daoimpl;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.leadmaster.common.converter.CustomerRangeConverter;
import com.leadmaster.common.dao.CustomerRangeDao;
import com.leadmaster.common.domain.CustomerRange;
import com.leadmaster.common.dto.CustomerRangeDTO;
import com.leadmaster.common.exception.ResourceNotFoundException;
import com.leadmaster.common.repository.CustomerRangeRepository;

@Transactional
@Service("CustomerRangeDaoImpl")
public class CustomerRangeDaoImpl implements CustomerRangeDao {

	@PersistenceContext
	private EntityManager entityManager;

	@Autowired
	private CustomerRangeRepository customerRangeRepository;

	@Override
	public CustomerRange saveCustomerRange(CustomerRangeDTO customerRangeDTO) {
		CustomerRange customerRanges = CustomerRangeConverter.getCustomerRangeByCustomerRangeDTO(customerRangeDTO);
		return customerRangeRepository.save(customerRanges);
	}

	@Override
	public List<CustomerRange> getAllCustomerRange(CustomerRangeDTO customerRangeDTO) {
		List<CustomerRange> returnList = null;
		StringBuffer sqlQuery = new StringBuffer("from CustomerRange a where 1=1");

		if (null != customerRangeDTO.getId())
			sqlQuery.append(" AND a.id = :id");
		if (null != customerRangeDTO.getStatus())
			sqlQuery.append(" AND a.status = :status");
		if (null != customerRangeDTO.getCustomerRange())
			sqlQuery.append(" AND a.customerRange = :customerRange");

		sqlQuery.append(" ORDER BY a.id DESC");
		Query query = entityManager.createQuery(sqlQuery.toString());

		if (null != customerRangeDTO.getId())
			query.setParameter("id", customerRangeDTO.getId());
		if (null != customerRangeDTO.getStatus())
			query.setParameter("status", customerRangeDTO.getStatus());
		if (null != customerRangeDTO.getCustomerRange())
			query.setParameter("customerRange", customerRangeDTO.getCustomerRange());

		returnList = query.getResultList();

		return returnList;
	}

	@Override
	public CustomerRange getCustomerRangeById(Long id) {
		Optional<CustomerRange> customerRanges = customerRangeRepository.findById(id);
		if (!customerRanges.isPresent())
			throw new ResourceNotFoundException("The customerRange is not found in the system. id:" + id);
		return customerRanges.get();
	}

	@Override
	public List<CustomerRange> getCustomerRangeByRange(String range) {
		return customerRangeRepository.getCustomerRangeByRange(range);
	}

}
