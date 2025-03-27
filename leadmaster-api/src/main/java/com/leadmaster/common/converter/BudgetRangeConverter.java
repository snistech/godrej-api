package com.leadmaster.common.converter;

import org.springframework.stereotype.Component;

import com.leadmaster.common.domain.BudgetRange;
import com.leadmaster.common.dto.BudgetRangeDTO;

@Component
public class BudgetRangeConverter {

	public static BudgetRange getBudgetRangeByBudgetRangeDTO(BudgetRangeDTO budgetRangeDTO) {
		BudgetRange budgetRange = new BudgetRange();
		budgetRange.setId(budgetRangeDTO.getId());
		budgetRange.setBudgetRange(budgetRangeDTO.getBudgetRange());
		budgetRange.setStatus(budgetRangeDTO.getStatus());
		budgetRange.setCreatedDate(budgetRangeDTO.getCreatedDate());
		budgetRange.setCreatedBy(budgetRangeDTO.getCreatedBy());
		budgetRange.setUpdatedDate(budgetRangeDTO.getUpdatedDate());
		budgetRange.setUpdatedBy(budgetRangeDTO.getUpdatedBy());

		return budgetRange;
	}

	public static BudgetRangeDTO getBudgetRangeDTOByBudgetRange(BudgetRange budgetRange) {
		BudgetRangeDTO dto = new BudgetRangeDTO();

		dto.setId(budgetRange.getId());
		dto.setBudgetRange(budgetRange.getBudgetRange());
		dto.setStatus(budgetRange.getStatus());
		dto.setCreatedDate(budgetRange.getCreatedDate());
		dto.setCreatedBy(budgetRange.getCreatedBy());
		dto.setUpdatedDate(budgetRange.getUpdatedDate());
		dto.setUpdatedBy(budgetRange.getUpdatedBy());

		return dto;
	}

}
