package com.leadmaster.service.validator;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.leadmaster.common.contant.Constant;
import com.leadmaster.common.dto.ChatHistoryDTO;
import com.leadmaster.common.utils.DateUtils;
import com.leadmaster.common.utils.UserUtils;
import com.leadmaster.common.validator.CustomValidator;

public class CommunicationValidator implements Validator {

	private static final String BAD_REQUEST_ERROR_CD = Constant.BAD_REQUEST_ERROR_CD;
	private static final List<String> VALID_UPDATE_STATUS = Arrays.asList(Constant.STATUS_ACTIVE,
			Constant.STATUS_DELEATED, Constant.STATUS_PENDING_APPROVAL);

	@Autowired
	private UserUtils userUtils;

	@Override
	public boolean supports(Class<?> clazz) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void validate(Object target, Errors errors) {
		// TODO Auto-generated method stub

	}

	// chat history manager
	public void saveChatHistory(ChatHistoryDTO chatHistoryDTO, Errors errors) {
		Long logedUserid = userUtils.getLogedInUser();
		String createdTime = DateUtils.getAsiaLocalDateTimeInCustomFormat();
		String readStatus = "Unread";

		if (CustomValidator.isEmpty(chatHistoryDTO.getText()))
			errors.rejectValue("text", BAD_REQUEST_ERROR_CD, "is an empty or not in valid format");

		if (CustomValidator.isEmpty(chatHistoryDTO.getSendTo()))
			errors.rejectValue("sendTo", BAD_REQUEST_ERROR_CD, "is an empty or not in valid format");

		chatHistoryDTO.setReceiverId(chatHistoryDTO.getSendTo());
		chatHistoryDTO.setSenderId(logedUserid);
		chatHistoryDTO.setReadStatus(readStatus);
		chatHistoryDTO.setStatus(Constant.STATUS_ACTIVE);
		chatHistoryDTO.setCreatedDate(createdTime);
		chatHistoryDTO.setCreatedBy(logedUserid);

	}

	public void getAllChatHistory(ChatHistoryDTO chatHistoryDTO, Errors errors) {
		Long logedUserid = userUtils.getLogedInUser();
		String createdTime = DateUtils.getAsiaLocalDateTimeInCustomFormat();

		if (null == chatHistoryDTO.getStatus())
			chatHistoryDTO.setStatus(Constant.STATUS_ACTIVE);

		chatHistoryDTO.setUpdatedBy(logedUserid);
		chatHistoryDTO.setUpdatedDate(createdTime);
	}

	public void getMyChats(ChatHistoryDTO chatHistoryDTO, Errors errors) {
		Long logedUserid = userUtils.getLogedInUser();
		String createdTime = DateUtils.getAsiaLocalDateTimeInCustomFormat();

		if (null == chatHistoryDTO.getStatus())
			chatHistoryDTO.setStatus(Constant.STATUS_ACTIVE);

		chatHistoryDTO.setUpdatedBy(logedUserid);
		chatHistoryDTO.setUpdatedDate(createdTime);
	}

	public void getUserChats(ChatHistoryDTO chatHistoryDTO, Errors errors) {
		Long logedUserid = userUtils.getLogedInUser();
		String createdTime = DateUtils.getAsiaLocalDateTimeInCustomFormat();

		if (CustomValidator.isEmpty(chatHistoryDTO.getContactUserId()))
			errors.rejectValue("contactUserId", BAD_REQUEST_ERROR_CD, "is an empty or not in valid format");

		if (null == chatHistoryDTO.getStatus())
			chatHistoryDTO.setStatus(Constant.STATUS_ACTIVE);

		chatHistoryDTO.setUpdatedBy(logedUserid);
		chatHistoryDTO.setUpdatedDate(createdTime);
	}

	public void getTotalUnreadCount(ChatHistoryDTO chatHistoryDTO, Errors errors) {
		Long logedUserid = userUtils.getLogedInUser();
		String createdTime = DateUtils.getAsiaLocalDateTimeInCustomFormat();

		if (null == chatHistoryDTO.getStatus())
			chatHistoryDTO.setStatus(Constant.STATUS_ACTIVE);

		chatHistoryDTO.setUpdatedBy(logedUserid);
		chatHistoryDTO.setUpdatedDate(createdTime);
	}

	public void updateChat(ChatHistoryDTO chatHistoryDTO, Errors errors) {
		Long logedUserid = userUtils.getLogedInUser();
		String createdTime = DateUtils.getAsiaLocalDateTimeInCustomFormat();

		if (CustomValidator.isEmpty(chatHistoryDTO.getId()))
			errors.rejectValue("id", BAD_REQUEST_ERROR_CD, "is an empty or not in valid format");

		if (null != chatHistoryDTO.getStatus() && CustomValidator.isEmpty(chatHistoryDTO.getStatus()))
			errors.rejectValue("status", BAD_REQUEST_ERROR_CD, "is an empty or not in valid format");

		if (null != chatHistoryDTO.getReadStatus() && CustomValidator.isEmpty(chatHistoryDTO.getReadStatus()))
			errors.rejectValue("readStatus", BAD_REQUEST_ERROR_CD, "is an empty or not in valid format");

		chatHistoryDTO.setUpdatedBy(logedUserid);
		chatHistoryDTO.setUpdatedDate(createdTime);

	}

}
