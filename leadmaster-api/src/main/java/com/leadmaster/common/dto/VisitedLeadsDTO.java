package com.leadmaster.common.dto;

public class VisitedLeadsDTO {

	private Long id;
	private Long leadId;
	private String property;
	private String visitedDate;
	private String propertyVisitCounsellor;
	private String remarks;
	private String status;
	private String createdDate;
	private Long createdBy;
	private String updatedDate;
	private Long updatedBy;

	private String visitStartDate;
	private String visitEndDate;

	private String branch;
	private String assignedTo;

	private int offset;
	private int limit;

	// additional fields
	private String fullName;
	private String phoneNumber;
	private String location;
	private String subLocation;
	private String leadStatus;

	private Long teamLead;
	private Long marketingExecutive;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getLeadId() {
		return leadId;
	}

	public void setLeadId(Long leadId) {
		this.leadId = leadId;
	}

	public String getProperty() {
		return property;
	}

	public void setProperty(String property) {
		this.property = property;
	}

	public String getVisitedDate() {
		return visitedDate;
	}

	public void setVisitedDate(String visitedDate) {
		this.visitedDate = visitedDate;
	}

	public String getPropertyVisitCounsellor() {
		return propertyVisitCounsellor;
	}

	public void setPropertyVisitCounsellor(String propertyVisitCounsellor) {
		this.propertyVisitCounsellor = propertyVisitCounsellor;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}

	public Long getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(Long createdBy) {
		this.createdBy = createdBy;
	}

	public String getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(String updatedDate) {
		this.updatedDate = updatedDate;
	}

	public Long getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(Long updatedBy) {
		this.updatedBy = updatedBy;
	}

	public String getVisitStartDate() {
		return visitStartDate;
	}

	public void setVisitStartDate(String visitStartDate) {
		this.visitStartDate = visitStartDate;
	}

	public String getVisitEndDate() {
		return visitEndDate;
	}

	public void setVisitEndDate(String visitEndDate) {
		this.visitEndDate = visitEndDate;
	}

	public String getBranch() {
		return branch;
	}

	public void setBranch(String branch) {
		this.branch = branch;
	}

	public int getOffset() {
		return offset;
	}

	public void setOffset(int offset) {
		this.offset = offset;
	}

	public int getLimit() {
		return limit;
	}

	public void setLimit(int limit) {
		this.limit = limit;
	}

	public String getAssignedTo() {
		return assignedTo;
	}

	public void setAssignedTo(String assignedTo) {
		this.assignedTo = assignedTo;
	}

	public String getFullName() {
		return fullName;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public String getLocation() {
		return location;
	}

	public String getSubLocation() {
		return subLocation;
	}

	public String getLeadStatus() {
		return leadStatus;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public void setSubLocation(String subLocation) {
		this.subLocation = subLocation;
	}

	public void setLeadStatus(String leadStatus) {
		this.leadStatus = leadStatus;
	}

	public Long getTeamLead() {
		return teamLead;
	}

	public void setTeamLead(Long teamLead) {
		this.teamLead = teamLead;
	}

	public Long getMarketingExecutive() {
		return marketingExecutive;
	}

	public void setMarketingExecutive(Long marketingExecutive) {
		this.marketingExecutive = marketingExecutive;
	}

}
