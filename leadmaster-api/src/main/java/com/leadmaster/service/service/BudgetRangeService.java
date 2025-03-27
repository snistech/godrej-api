package com.leadmaster.service.service;

import java.util.List;

import com.leadmaster.common.domain.BudgetRange;
import com.leadmaster.common.dto.BudgetRangeDTO;

public interface BudgetRangeService {

	public void saveBudgetRange(BudgetRangeDTO budgetRangeDTO);

	public List<BudgetRange> getAllBudgetRanges(BudgetRangeDTO budgetRangeDTO);

	public BudgetRange getBudgetRangeById(BudgetRangeDTO budgetRangeDTO);

	public void updateBudgetRange(BudgetRangeDTO budgetRangeDTO);

	public BudgetRange deleteBudgetRange(BudgetRangeDTO budgetRangeDTO);

}
