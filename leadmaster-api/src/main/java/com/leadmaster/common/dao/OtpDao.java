package com.leadmaster.common.dao;

import com.leadmaster.common.domain.Otp;
import com.leadmaster.common.dto.OtpDTO;

public interface OtpDao {

	public Otp saveOtp(OtpDTO otpDTO);

	public Otp getOtpByPhoneNumber(String phoneNumber, String otp);

}
