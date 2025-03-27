package com.leadmaster.common.daoimpl;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.leadmaster.common.converter.CustomerTypeConverter;
import com.leadmaster.common.dao.CustomerTypeDao;
import com.leadmaster.common.domain.CustomerType;
import com.leadmaster.common.dto.CustomerTypeDTO;
import com.leadmaster.common.exception.ResourceNotFoundException;
import com.leadmaster.common.repository.CustomerTypeRepository;

@Transactional
@Service("CustomerTypeDaoImpl")
public class CustomerTypeDaoImpl implements CustomerTypeDao {

	@PersistenceContext
	private EntityManager entityManager;

	@Autowired
	private CustomerTypeRepository customerTypeRepository;

	@Override
	public CustomerType saveCustomerType(CustomerTypeDTO customerTypeDTO) {
		CustomerType customerTypes = CustomerTypeConverter.getCustomerTypeByCustomerTypeDTO(customerTypeDTO);
		return customerTypeRepository.save(customerTypes);
	}

	@Override
	public List<CustomerType> getAllCustomerType(CustomerTypeDTO customerTypeDTO) {
		List<CustomerType> returnList = null;
		StringBuffer sqlQuery = new StringBuffer("from CustomerType a where 1=1");

		if (null != customerTypeDTO.getId())
			sqlQuery.append(" AND a.id = :id");
		if (null != customerTypeDTO.getStatus())
			sqlQuery.append(" AND a.status = :status");
		if (null != customerTypeDTO.getType())
			sqlQuery.append(" AND a.type = :type");

		sqlQuery.append(" ORDER BY LOWER(a.type) ASC");
		Query query = entityManager.createQuery(sqlQuery.toString());

		if (null != customerTypeDTO.getId())
			query.setParameter("id", customerTypeDTO.getId());
		if (null != customerTypeDTO.getStatus())
			query.setParameter("status", customerTypeDTO.getStatus());
		if (null != customerTypeDTO.getType())
			query.setParameter("type", customerTypeDTO.getType());

		returnList = query.getResultList();

		return returnList;
	}

	@Override
	public CustomerType getCustomerTypeById(Long id) {
		Optional<CustomerType> customerTypes = customerTypeRepository.findById(id);
		if (!customerTypes.isPresent())
			throw new ResourceNotFoundException("The customerType is not found in the system. id:" + id);
		return customerTypes.get();
	}

	@Override
	public List<CustomerType> getCustomerTypeByType(String type) {
		return customerTypeRepository.getCustomerTypeByType(type);
	}

}
