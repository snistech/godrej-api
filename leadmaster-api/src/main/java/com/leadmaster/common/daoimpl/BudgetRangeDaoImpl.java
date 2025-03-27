package com.leadmaster.common.daoimpl;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.leadmaster.common.converter.BudgetRangeConverter;
import com.leadmaster.common.dao.BudgetRangeDao;
import com.leadmaster.common.domain.BudgetRange;
import com.leadmaster.common.dto.BudgetRangeDTO;
import com.leadmaster.common.exception.ResourceNotFoundException;
import com.leadmaster.common.repository.BudgetRangeRepository;

@Transactional
@Service("BudgetRangeDaoImpl")
public class BudgetRangeDaoImpl implements BudgetRangeDao {

	@PersistenceContext
	private EntityManager entityManager;

	@Autowired
	private BudgetRangeRepository budgetRangeRepository;

	@Override
	public BudgetRange saveBudgetRange(BudgetRangeDTO budgetRangeDTO) {
		BudgetRange budgetRanges = BudgetRangeConverter.getBudgetRangeByBudgetRangeDTO(budgetRangeDTO);
		return budgetRangeRepository.save(budgetRanges);
	}

	@Override
	public List<BudgetRange> getAllBudgetRange(BudgetRangeDTO budgetRangeDTO) {
		List<BudgetRange> returnList = null;
		StringBuffer sqlQuery = new StringBuffer("from BudgetRange a where 1=1");

		if (null != budgetRangeDTO.getId())
			sqlQuery.append(" AND a.id = :id");
		if (null != budgetRangeDTO.getStatus())
			sqlQuery.append(" AND a.status = :status");
		if (null != budgetRangeDTO.getBudgetRange())
			sqlQuery.append(" AND a.budgetRange = :budgetRange");

		sqlQuery.append(" ORDER BY a.id DESC");
		Query query = entityManager.createQuery(sqlQuery.toString());

		if (null != budgetRangeDTO.getId())
			query.setParameter("id", budgetRangeDTO.getId());
		if (null != budgetRangeDTO.getStatus())
			query.setParameter("status", budgetRangeDTO.getStatus());
		if (null != budgetRangeDTO.getBudgetRange())
			query.setParameter("budgetRange", budgetRangeDTO.getBudgetRange());

		returnList = query.getResultList();

		return returnList;
	}

	@Override
	public BudgetRange getBudgetRangeById(Long id) {
		Optional<BudgetRange> budgetRanges = budgetRangeRepository.findById(id);
		if (!budgetRanges.isPresent())
			throw new ResourceNotFoundException("The budgetRange is not found in the system. id:" + id);
		return budgetRanges.get();
	}

	@Override
	public List<BudgetRange> getBudgetRangeByRange(String range) {
		return budgetRangeRepository.getBudgetRangeByRange(range);
	}

}
