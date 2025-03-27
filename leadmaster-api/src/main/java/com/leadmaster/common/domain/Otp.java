package com.leadmaster.common.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "otp")
public class Otp extends AbstractEntity {

	private static final long serialVersionUID = -8924261433695968011L;

	@Column(name = "phone_number")
	private String phoneNumber;

	@Column(name = "otp")
	private String otp;

	@Column(name = "created_date")
	private String createdDate;

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getOtp() {
		return otp;
	}

	public void setOtp(String otp) {
		this.otp = otp;
	}

	public String getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}

}
