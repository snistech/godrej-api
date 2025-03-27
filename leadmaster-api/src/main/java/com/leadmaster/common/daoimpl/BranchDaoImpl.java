package com.leadmaster.common.daoimpl;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.leadmaster.common.converter.BranchConverter;
import com.leadmaster.common.dao.BranchDao;
import com.leadmaster.common.domain.Branch;
import com.leadmaster.common.dto.BranchDTO;
import com.leadmaster.common.exception.ResourceNotFoundException;
import com.leadmaster.common.repository.BranchRepository;

@Transactional
@Service("BranchDaoImpl")
public class BranchDaoImpl implements BranchDao {

	@PersistenceContext
	private EntityManager entityManager;

	@Autowired
	private BranchRepository branchRepository;

	@Override
	public Branch saveBranch(BranchDTO branchDTO) {
		Branch branchs = BranchConverter.getBranchByBranchDTO(branchDTO);
		return branchRepository.save(branchs);
	}

	@Override
	public List<Branch> getAllBranch(BranchDTO branchDTO) {
		List<Branch> returnList = null;
		StringBuffer sqlQuery = new StringBuffer("from Branch a where 1=1");

		if (null != branchDTO.getId())
			sqlQuery.append(" AND a.id = :id");
		if (null != branchDTO.getStatus())
			sqlQuery.append(" AND a.status = :status");
		if (null != branchDTO.getBranch())
			sqlQuery.append(" AND a.branch = :branch");

		sqlQuery.append(" ORDER BY LOWER(a.branch) ASC");
		Query query = entityManager.createQuery(sqlQuery.toString());

		if (null != branchDTO.getId())
			query.setParameter("id", branchDTO.getId());
		if (null != branchDTO.getStatus())
			query.setParameter("status", branchDTO.getStatus());
		if (null != branchDTO.getBranch())
			query.setParameter("branch", branchDTO.getBranch());

		returnList = query.getResultList();

		return returnList;
	}

	@Override
	public Branch getBranchById(Long id) {
		Optional<Branch> branchs = branchRepository.findById(id);
		if (!branchs.isPresent())
			throw new ResourceNotFoundException("The branch is not found in the system. id:" + id);
		return branchs.get();
	}

	@Override
	public List<Branch> getBranchByName(String branchName) {
		return branchRepository.getBranchByName(branchName);
	}

}
