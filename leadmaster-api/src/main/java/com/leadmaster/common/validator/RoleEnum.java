package com.leadmaster.common.validator;

import java.util.Arrays;

public enum RoleEnum {

	SUPER_ADMIN("SuperAdmin"), ADMIN("Admin"), LEVEL_3("LevelThree"), LEVEL_2("LevelTwo"), LEVEL_1("LevelOne"),
	MARKETING_EXECUTIVE("MarketingExecutive"), SUPER_HR("SuperHR"), HR("HR");

	private String role;

	RoleEnum(String role) {
		this.role = role;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public static boolean isInEnum(String value, Class<RoleEnum> enumClass) {
		return Arrays.stream(enumClass.getEnumConstants()).anyMatch(e -> e.getRole().equals(value));
	}
}
