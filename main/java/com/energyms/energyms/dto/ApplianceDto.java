package com.energyms.energyms.dto;

public class ApplianceDto {
	 private String applianceName;
	 private String userEmailId;
	 private long roomId;
	 private String deviceId;
	 private boolean applianceStatus;
	public long getRoomId() {
		return roomId;
	}

	public void setRoomId(long roomId) {
		this.roomId = roomId;
	}

	public String getApplianceName() {
		return applianceName;
	}

	public void setApplianceName(String applianceName) {
		this.applianceName = applianceName;
	}

	

	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	public String getUserEmailId() {
		return userEmailId;
	}

	public void setUserEmailId(String userEmailId) {
		this.userEmailId = userEmailId;
	}

//	public ApplianceDto(String applianceName, String userEmailId) {
//		super();
//		this.applianceName = applianceName;
//		this.userEmailId = userEmailId;
//	}
	

	public ApplianceDto() {
		super();
	}

	

	public ApplianceDto(String applianceName, String userEmailId, long roomId, String deviceId, boolean applianceStatus) {
	super();
	this.applianceName = applianceName;
	this.userEmailId = userEmailId;
	this.roomId = roomId;
	this.deviceId = deviceId;
	this.applianceStatus = applianceStatus;
}

	public boolean isApplianceStatus() {
		return applianceStatus;
	}

	public void setApplianceStatus(boolean applianceStatus) {
		this.applianceStatus = applianceStatus;
	}
	 
}
