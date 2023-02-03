package com.energyms.energyms.dto;

import java.sql.Time;

public class ScheduleDto {
  private boolean applianceStatus;
  private Time startTime;
  private Time endTime;
public boolean isApplianceStatus() {
	return applianceStatus;
}
public void setApplianceStatus(boolean applianceStatus) {
	this.applianceStatus = applianceStatus;
}
public Time getStartTime() {
	return startTime;
}
public void setStartTime(Time startTime) {
	this.startTime = startTime;
}
public Time getEndTime() {
	return endTime;
}
public void setEndTime(Time endTime) {
	this.endTime = endTime;
}
public ScheduleDto(boolean applianceStatus, Time startTime, Time endTime) {
	super();
	this.applianceStatus = applianceStatus;
	this.startTime = startTime;
	this.endTime = endTime;
}
  
}
