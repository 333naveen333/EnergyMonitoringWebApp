package com.energyms.energyms.model;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.TimeZone;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "devicedata")
public class DeviceData {
	 @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private long rowId;
	 private String userId;
	private String deviceId;
	private String applianceName;
	private double cost;
	private double carbonEmission;
	
	public double getCost() {
		return cost;
	}
	public void setCost(double cost) {
		this.cost = cost;
	}
	public double getCarbonEmission() {
		return carbonEmission;
	}
	public void setCarbonEmission(double carbonEmission) {
		this.carbonEmission = carbonEmission;
	}
	public String getApplianceName() {
		return applianceName;
	}
	public void setApplianceName(String applianceName) {
		this.applianceName = applianceName;
	}
	private String roomName;
	private double powerConsumption;
	private String  eventTime;
	
	
	public String getEventTime() {
		return eventTime;
	}
	public void setEventTime(String eventTime) {
		this.eventTime = eventTime;
	}
	public String getRoomName() {
		return roomName;
	}
	public void setRoomName(String roomName) {
		this.roomName = roomName;
	}
	public String getDeviceId() {
		return deviceId;
	}
	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}
	public double getPowerConsumption() {
		return powerConsumption;
	}
	public void setPowerConsumption(double powerConsumption) {
		this.powerConsumption = powerConsumption;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	
	public DeviceData(String userId, String deviceId,String applianceName, String roomName, double powerConsumption, String eventTime,double cost,double carbonEmission) {
		super();
		this.userId = userId;
		this.deviceId = deviceId;
		this.applianceName=applianceName;
		this.roomName = roomName;
		this.powerConsumption = powerConsumption;
		this.eventTime = eventTime;
		this.carbonEmission=carbonEmission;
		this.cost=cost;
	}
	public DeviceData() {
		super();
	}
	
}
