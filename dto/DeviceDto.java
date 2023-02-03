package com.energyms.energyms.dto;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

public class DeviceDto {

//	    @Id
//	    @GeneratedValue(strategy = GenerationType.IDENTITY)
//	    private long deviceId;
	    	  
	    private String deviceName;

//		public long getId() {
//			return deviceId;
//		}
//
//		public void setId(long id) {
//			this.deviceId = id;
//		}

		public String getDeviceName() {
			return deviceName;
		}

		public void setDeviceName(String deviceName) {
			this.deviceName = deviceName;
		}

		public DeviceDto(String deviceName) {
			super();
			this.deviceName = deviceName;
		}

		public DeviceDto() {
			super();
		}
	    
	    
	    
}
