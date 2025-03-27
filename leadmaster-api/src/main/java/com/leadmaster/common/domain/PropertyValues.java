package com.leadmaster.common.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "property_values")
public class PropertyValues extends AbstractEntity {

	private static final long serialVersionUID = -8924261433695968011L;

	@Column(name = "category")
	private String category;

	@Column(name = "type")
	private String type;

	@Column(name = "project_name")
	private String projectName;

	@Column(name = "location")
	private String location;

	@Column(name = "sub_location")
	private String subLocation;

	@Column(name = "apartment_type")
	private String apartmentType;

	@Column(name = "looking_for")
	private String lookingFor;

	@Column(name = "budget_from")
	private String budgetFrom;

	@Column(name = "budget_to")
	private String budgetTo;

	@Column(name = "possession")
	private String possession;

	@Column(name = "property_link")
	private String propertyLink;

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

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
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

	public String getApartmentType() {
		return apartmentType;
	}

	public void setApartmentType(String apartmentType) {
		this.apartmentType = apartmentType;
	}

	public String getLookingFor() {
		return lookingFor;
	}

	public void setLookingFor(String lookingFor) {
		this.lookingFor = lookingFor;
	}

	public String getBudgetFrom() {
		return budgetFrom;
	}

	public void setBudgetFrom(String budgetFrom) {
		this.budgetFrom = budgetFrom;
	}

	public String getBudgetTo() {
		return budgetTo;
	}

	public void setBudgetTo(String budgetTo) {
		this.budgetTo = budgetTo;
	}

	public String getPossession() {
		return possession;
	}

	public void setPossession(String possession) {
		this.possession = possession;
	}

	public String getPropertyLink() {
		return propertyLink;
	}

	public void setPropertyLink(String propertyLink) {
		this.propertyLink = propertyLink;
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

}
