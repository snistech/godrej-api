package com.leadmaster.common.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "assigned_leads")
public class AssignedLead extends AbstractEntity {

	private static final long serialVersionUID = -8924261433695968011L;

	@Column(name = "lead_id")
	private Long leadId;

	@Column(name = "full_name")
	private String fullName;

	@Column(name = "phone_number")
	private String phoneNumber;

	@Column(name = "alternative_number")
	private String alternativeNumber;

	@Column(name = "location")
	private String location;

	@Column(name = "sub_location")
	private String subLocation;

	@Column(name = "looking_for")
	private String lookingFor;

	@Column(name = "tenant_type")
	private String tenantType;

	@Column(name = "apartment_type")
	private String apartmentType;

	@Column(name = "amenities")
	private String amenities;

	@Column(name = "property_approval")
	private String propertyApproval;

	@Column(name = "plot_size")
	private String plotSize;

	@Column(name = "suggested_plot")
	private String suggestedPlot;

	@Column(name = "budget_range")
	private String budgetRange;

	@Column(name = "customer_occupation")
	private String customerOccupation;

	@Column(name = "income_range")
	private String incomeRange;

	@Column(name = "possession")
	private String possession;

	@Column(name = "remarks")
	private String remarks;

	@Column(name = "lead_status")
	private String leadStatus;

	@Column(name = "assigned_to")
	private String assignedTo;

	@Column(name = "assigned_by")
	private Long assignedBy;

	@Column(name = "data_collector")
	private Long dataCollector;

	@Column(name = "visiting_date")
	private String visitingDate;

	@Column(name = "visited_date")
	private String visitedDate;

	@Column(name = "conversion_date")
	private String conversionDate;

	@Column(name = "finalized_counsellor")
	private String finalizedCounsellor;

	@Column(name = "finalized_property")
	private String finalizedProperty;

	@Column(name = "finalized_proof")
	private String finalizedProof;

	@Column(name = "finalized_status")
	private String finalizedStatus;

	@Column(name = "lead_source")
	private String leadSource;

	@Column(name = "lead_flag")
	private String leadFlag;

	@Column(name = "status")
	private String status;

	@Column(name = "created_date")
	private String createdDate;

	@Column(name = "created_by")
	private Long createdBy;

	@Column(name = "updated_date")
	private String updatedDate;

	@Column(name = "updated_by")
	private Long updatedBy;

	public Long getLeadId() {
		return leadId;
	}

	public void setLeadId(Long leadId) {
		this.leadId = leadId;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getAlternativeNumber() {
		return alternativeNumber;
	}

	public void setAlternativeNumber(String alternativeNumber) {
		this.alternativeNumber = alternativeNumber;
	}

	public String getLookingFor() {
		return lookingFor;
	}

	public void setLookingFor(String lookingFor) {
		this.lookingFor = lookingFor;
	}

	public String getTenantType() {
		return tenantType;
	}

	public void setTenantType(String tenantType) {
		this.tenantType = tenantType;
	}

	public String getApartmentType() {
		return apartmentType;
	}

	public void setApartmentType(String apartmentType) {
		this.apartmentType = apartmentType;
	}

	public String getAmenities() {
		return amenities;
	}

	public void setAmenities(String amenities) {
		this.amenities = amenities;
	}

	public String getPropertyApproval() {
		return propertyApproval;
	}

	public void setPropertyApproval(String propertyApproval) {
		this.propertyApproval = propertyApproval;
	}

	public String getPlotSize() {
		return plotSize;
	}

	public void setPlotSize(String plotSize) {
		this.plotSize = plotSize;
	}

	public String getSuggestedPlot() {
		return suggestedPlot;
	}

	public void setSuggestedPlot(String suggestedPlot) {
		this.suggestedPlot = suggestedPlot;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getLeadStatus() {
		return leadStatus;
	}

	public void setLeadStatus(String leadStatus) {
		this.leadStatus = leadStatus;
	}

	public String getAssignedTo() {
		return assignedTo;
	}

	public void setAssignedTo(String assignedTo) {
		this.assignedTo = assignedTo;
	}

	public Long getAssignedBy() {
		return assignedBy;
	}

	public void setAssignedBy(Long assignedBy) {
		this.assignedBy = assignedBy;
	}

	public Long getDataCollector() {
		return dataCollector;
	}

	public void setDataCollector(Long dataCollector) {
		this.dataCollector = dataCollector;
	}

	public String getVisitingDate() {
		return visitingDate;
	}

	public void setVisitingDate(String visitingDate) {
		this.visitingDate = visitingDate;
	}

	public String getVisitedDate() {
		return visitedDate;
	}

	public void setVisitedDate(String visitedDate) {
		this.visitedDate = visitedDate;
	}

	public String getConversionDate() {
		return conversionDate;
	}

	public void setConversionDate(String conversionDate) {
		this.conversionDate = conversionDate;
	}

	public String getFinalizedCounsellor() {
		return finalizedCounsellor;
	}

	public void setFinalizedCounsellor(String finalizedCounsellor) {
		this.finalizedCounsellor = finalizedCounsellor;
	}

	public String getFinalizedProperty() {
		return finalizedProperty;
	}

	public void setFinalizedProperty(String finalizedProperty) {
		this.finalizedProperty = finalizedProperty;
	}

	public String getFinalizedProof() {
		return finalizedProof;
	}

	public void setFinalizedProof(String finalizedProof) {
		this.finalizedProof = finalizedProof;
	}

	public String getFinalizedStatus() {
		return finalizedStatus;
	}

	public void setFinalizedStatus(String finalizedStatus) {
		this.finalizedStatus = finalizedStatus;
	}

	public String getLeadSource() {
		return leadSource;
	}

	public void setLeadSource(String leadSource) {
		this.leadSource = leadSource;
	}

	public String getLeadFlag() {
		return leadFlag;
	}

	public void setLeadFlag(String leadFlag) {
		this.leadFlag = leadFlag;
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

	public String getBudgetRange() {
		return budgetRange;
	}

	public void setBudgetRange(String budgetRange) {
		this.budgetRange = budgetRange;
	}

	public String getCustomerOccupation() {
		return customerOccupation;
	}

	public void setCustomerOccupation(String customerOccupation) {
		this.customerOccupation = customerOccupation;
	}

	public String getIncomeRange() {
		return incomeRange;
	}

	public void setIncomeRange(String incomeRange) {
		this.incomeRange = incomeRange;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getSubLocation() {
		return subLocation;
	}

	public void setSubLocation(String subLocation) {
		this.subLocation = subLocation;
	}

	public String getPossession() {
		return possession;
	}

	public void setPossession(String possession) {
		this.possession = possession;
	}

}