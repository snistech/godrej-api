package com.leadmaster.common.dto;

public class AnalyticsDTO {

	private Long admission;
	private Long visited;
	private Long visiting;
	private Long assignedTo;
	private String branch;
	private String startDate;
	private String endDate;

	public Long getAdmission() {
		return admission;
	}

	public void setAdmission(Long admission) {
		this.admission = admission;
	}

	public Long getVisited() {
		return visited;
	}

	public void setVisited(Long visited) {
		this.visited = visited;
	}

	public Long getVisiting() {
		return visiting;
	}

	public void setVisiting(Long visiting) {
		this.visiting = visiting;
	}

	public String getBranch() {
		return branch;
	}

	public void setBranch(String branch) {
		this.branch = branch;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public Long getAssignedTo() {
		return assignedTo;
	}

	public void setAssignedTo(Long assignedTo) {
		this.assignedTo = assignedTo;
	}

}
