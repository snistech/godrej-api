package com.leadmaster.common.converter;

import org.springframework.stereotype.Component;

import com.leadmaster.common.domain.Otp;
import com.leadmaster.common.dto.OtpDTO;

@Component
public class OtpConverter {

	public static Otp getOtpByOtpDTO(OtpDTO otpDTO) {
		Otp otp = new Otp();

		otp.setId(otpDTO.getId());
		otp.setPhoneNumber(otpDTO.getPhoneNumber());
		otp.setOtp(otpDTO.getOtp());
		otp.setCreatedDate(otpDTO.getCreatedDate());

		return otp;
	}

	public static OtpDTO getOtpDTOByOtp(Otp otp) {
		OtpDTO dto = new OtpDTO();

		dto.setId(otp.getId());
		dto.setPhoneNumber(otp.getPhoneNumber());
		dto.setOtp(otp.getOtp());
		dto.setCreatedDate(otp.getCreatedDate());

		return dto;
	}

}
