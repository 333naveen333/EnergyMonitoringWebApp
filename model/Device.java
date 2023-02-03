package com.energyms.energyms.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "devices")//, uniqueConstraints = {
      //  @UniqueConstraint(columnNames = {"deviceId"})
//}) 
public class Device implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long rowId;
	// deviceId auto no generation
	private String deviceId;

	private String deviceName;

	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	public String getDeviceName() {
		return deviceName;
	}

	public void setDeviceName(String deviceName) {
		this.deviceName = deviceName;
	}

	public Device() {
		super();
	}

	public Device(String deviceId, String deviceName) {
		super();
		this.deviceId = deviceId;
		this.deviceName = deviceName;
	}

	@Override
	public String toString() {
		return "Device [deviceId=" + deviceId + ", deviceName=" + deviceName + "]";
	}
	

}
