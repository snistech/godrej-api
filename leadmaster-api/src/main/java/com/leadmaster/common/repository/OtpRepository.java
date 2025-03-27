package com.leadmaster.common.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.leadmaster.common.domain.Otp;

@Repository
public interface OtpRepository extends JpaRepository<Otp, Long> {

	@Query("select u from Otp u where u.phoneNumber=:phoneNumber AND u.otp=:otp order by u.id desc")
	List<Otp> findLatestOtpByPhoneNumber(@Param("phoneNumber") String phoneNumber, @Param("otp") String otp);

	default Otp getOtpByPhoneNumber(String phoneNumber, String otp) {
		List<Otp> otps = findLatestOtpByPhoneNumber(phoneNumber, otp);
		return otps.isEmpty() ? null : otps.get(0);
	}

}
