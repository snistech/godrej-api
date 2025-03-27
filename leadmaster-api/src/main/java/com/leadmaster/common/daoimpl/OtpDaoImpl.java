package com.leadmaster.common.daoimpl;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.leadmaster.common.converter.OtpConverter;
import com.leadmaster.common.dao.OtpDao;
import com.leadmaster.common.domain.Otp;
import com.leadmaster.common.dto.OtpDTO;
import com.leadmaster.common.repository.OtpRepository;

@Transactional
@Service("OtpDaoImpl")
public class OtpDaoImpl implements OtpDao {

	private Logger LOGGER = LoggerFactory.getLogger(OtpDaoImpl.class);

	@PersistenceContext
	private EntityManager entityManager;

	@Autowired
	private OtpRepository otpRepository;

	@Override
	public Otp saveOtp(OtpDTO otpsDTO) {
		Otp otps = OtpConverter.getOtpByOtpDTO(otpsDTO);
		return otpRepository.save(otps);
	}

	@Override
	public Otp getOtpByPhoneNumber(String phoneNumber, String otp) {
		return otpRepository.getOtpByPhoneNumber(phoneNumber, otp);
	}

}
