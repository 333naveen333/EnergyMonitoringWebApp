package com.energyms.energyms.dto;

public class ScheduleDto {
	private String applianceName;
	private String roomName;
	private boolean setApplianceStatus;
	private String startTime;
	private String endTime;
	
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	
	public String getApplianceName() {
		return applianceName;
	}
	public void setApplianceName(String applianceName) {
		this.applianceName = applianceName;
	}
	public String getRoomName() {
		return roomName;
	}
	public void setRoomName(String roomName) {
		this.roomName = roomName;
	}
	public boolean isSetApplianceStatus() {
		return setApplianceStatus;
	}
	public void setSetApplianceStatus(boolean setApplianceStatus) {
		this.setApplianceStatus = setApplianceStatus;
	}
	
	public ScheduleDto(String applianceName, String roomName, boolean setApplianceStatus, String startTime,
			String endTime) {
		super();
		this.applianceName = applianceName;
		this.roomName = roomName;
		this.setApplianceStatus = setApplianceStatus;
		this.startTime = startTime;
		this.endTime = endTime;
	}
	public ScheduleDto() {
		super();
	}
	

}
