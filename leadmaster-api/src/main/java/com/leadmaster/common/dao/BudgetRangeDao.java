package com.leadmaster.common.dao;

import java.util.List;

import com.leadmaster.common.domain.BudgetRange;
import com.leadmaster.common.dto.BudgetRangeDTO;

public interface BudgetRangeDao {

	public BudgetRange saveBudgetRange(BudgetRangeDTO budgetRangeDTO);

	public List<BudgetRange> getAllBudgetRange(BudgetRangeDTO budgetRangeDTO);

	public BudgetRange getBudgetRangeById(Long id);

	public List<BudgetRange> getBudgetRangeByRange(String range);

}
