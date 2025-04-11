package com.leadmaster.common.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.leadmaster.common.domain.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

	@Query("select u from User u where u.email=:email AND u.status = 'Active'")
	User getUserByEmail(@Param("email") String email);

//	@Query("select u.id from User u where u.leadLocation = :location and u.status = 'Active'")
//	List<String> findUserIdsByLocation(@Param("location") String location);

//	@Query("SELECT u.id FROM User u JOIN Role r ON u.id = r.userId WHERE u.leadLocation LIKE CONCAT('%', :location, '%') AND u.branch = :branch AND u.status = 'Active' AND u.activeStatus = 'Active' AND r.role = 'LevelThree'")
//	List<String> findUserIdsByLocationAndBranch(@Param("location") String location, @Param("branch") String branch);

//	@Query("SELECT u.branch FROM User u WHERE u.id = :createdBy")
//	String findUserBranchByCreatedBy(@Param("createdBy") Long createdBy);

//	@Query("SELECT u.id FROM User u WHERE LOWER(u.assignedAsset) = LOWER(:assignedAsset) AND u.status = 'Active' AND u.activeStatus = 'Active'")
//	List<String> getUserIdsByAssignedAsset(@Param("assignedAsset") String assignedAsset);
//
//	@Query("SELECT u.id FROM User u WHERE LOWER(u.branch) = LOWER(:branch) AND LOWER(u.assignedAsset) = LOWER(:assignedAsset) AND u.status = 'Active' AND u.activeStatus = 'Active'")
//	List<String> getUserIdsByBranchAndAssignedAsset(@Param("branch") String branch,
//			@Param("assignedAsset") String assignedAsset);

	@Query("select u from User u where u.phoneNumber=:phoneNumber and u.status = 'Active'")
	User getUserByPhoneNumber(@Param("phoneNumber") String phoneNumber);

}
