package com.energyms.energyms.dto;


import org.apache.kafka.common.protocol.types.Field;

import java.sql.Timestamp;
import java.time.LocalDateTime;

public class DeviceDataDto {

    private double consumption;

    private String applianceName;

    private double eventValue;

    private String deviceId;

    private LocalDateTime timestamp;

    private String emailId;

    private long timeInMinutes;

    private LocalDateTime timeWhenAppisOn;

    private int hour;
    private int dayOfMonth;
    private int monthOfYear;
    private int year;

    private double price;

    private double pricePerUnit;

    private double co2Emission;

    private double co2EmissionPerUnit;

    private long totalTimeInMinutes;

    public DeviceDataDto() {
    }

    public DeviceDataDto(double consumption, String applianceName, double eventValue, String deviceId, LocalDateTime timestamp, String emailId) {
        this.consumption = consumption;
        this.applianceName = applianceName;
        this.eventValue = eventValue;
        this.deviceId = deviceId;
        this.timestamp = timestamp;
        this.emailId = emailId;
    }

    public DeviceDataDto(double consumption, String applianceName, double eventValue, String deviceId, LocalDateTime timestamp, String emailId, long timeInMinutes) {
        this.consumption = consumption;
        this.applianceName = applianceName;
        this.eventValue = eventValue;
        this.deviceId = deviceId;
        this.timestamp = timestamp;
        this.emailId = emailId;
        this.timeInMinutes = timeInMinutes;
    }

    public DeviceDataDto(double consumption, String applianceName, double eventValue, String deviceId, LocalDateTime timestamp, String emailId, long timeInMinutes, LocalDateTime timeWhenAppisOn, int hour, int dayOfMonth, int monthOfYear, int year) {
        this.consumption = consumption;
        this.applianceName = applianceName;
        this.eventValue = eventValue;
        this.deviceId = deviceId;
        this.timestamp = timestamp;
        this.emailId = emailId;
        this.timeInMinutes = timeInMinutes;
        this.timeWhenAppisOn = timeWhenAppisOn;
        this.hour = hour;
        this.dayOfMonth = dayOfMonth;
        this.monthOfYear = monthOfYear;
        this.year = year;
    }

    public DeviceDataDto(double consumption, String applianceName, double eventValue, String deviceId, LocalDateTime timestamp, String emailId, long timeInMinutes, LocalDateTime timeWhenAppisOn, int hour, int dayOfMonth, int monthOfYear, int year, long totalTimeInMinutes) {
        this.consumption = consumption;
        this.applianceName = applianceName;
        this.eventValue = eventValue;
        this.deviceId = deviceId;
        this.timestamp = timestamp;
        this.emailId = emailId;
        this.timeInMinutes = timeInMinutes;
        this.timeWhenAppisOn = timeWhenAppisOn;
        this.hour = hour;
        this.dayOfMonth = dayOfMonth;
        this.monthOfYear = monthOfYear;
        this.year = year;
        this.totalTimeInMinutes = totalTimeInMinutes;
    }

    public DeviceDataDto(double consumption, String applianceName, double eventValue, String deviceId, LocalDateTime timestamp, String emailId, long timeInMinutes, LocalDateTime timeWhenAppisOn, int hour, int dayOfMonth, int monthOfYear, int year, double price, double pricePerUnit, double co2Emission, double co2EmissionPerUnit, long totalTimeInMinutes) {
        this.consumption = consumption;
        this.applianceName = applianceName;
        this.eventValue = eventValue;
        this.deviceId = deviceId;
        this.timestamp = timestamp;
        this.emailId = emailId;
        this.timeInMinutes = timeInMinutes;
        this.timeWhenAppisOn = timeWhenAppisOn;
        this.hour = hour;
        this.dayOfMonth = dayOfMonth;
        this.monthOfYear = monthOfYear;
        this.year = year;
        this.price = price;
        this.pricePerUnit = pricePerUnit;
        this.co2Emission = co2Emission;
        this.co2EmissionPerUnit = co2EmissionPerUnit;
        this.totalTimeInMinutes = totalTimeInMinutes;
    }

    public long getTimeInMinutes() {
        return timeInMinutes;
    }

    public void setTimeInMinutes(long timeInMinutes) {
        this.timeInMinutes = timeInMinutes;
    }

    public double getConsumption() {
        return consumption;
    }

    public void setConsumption(double consumption) {
        this.consumption = consumption;
    }

    public String getApplianceName() {
        return applianceName;
    }

    public void setApplianceName(String applianceName) {
        this.applianceName = applianceName;
    }

    public double getEventValue() {
        return eventValue;
    }

    public void setEventValue(double eventValue) {
        this.eventValue = eventValue;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getPricePerUnit() {
        return pricePerUnit;
    }

    public void setPricePerUnit(double pricePerUnit) {
        this.pricePerUnit = pricePerUnit;
    }

    public double getCo2Emission() {
        return co2Emission;
    }

    public void setCo2Emission(double co2Emission) {
        this.co2Emission = co2Emission;
    }

    public double getCo2EmissionPerUnit() {
        return co2EmissionPerUnit;
    }

    public void setCo2EmissionPerUnit(double co2EmissionPerUnit) {
        this.co2EmissionPerUnit = co2EmissionPerUnit;
    }

    public LocalDateTime getTimeWhenAppisOn() {
        return timeWhenAppisOn;
    }

    public long getTotalTimeInMinutes() {
        return totalTimeInMinutes;
    }

    public void setTotalTimeInMinutes(long totalTimeInMinutes) {
        this.totalTimeInMinutes = totalTimeInMinutes;
    }

    public void setTimeWhenAppisOn(LocalDateTime timeWhenAppisOn) {
        this.timeWhenAppisOn = timeWhenAppisOn;
    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public int getDayOfMonth() {
        return dayOfMonth;
    }

    public void setDayOfMonth(int dayOfMonth) {
        this.dayOfMonth = dayOfMonth;
    }

    public int getMonthOfYear() {
        return monthOfYear;
    }

    public void setMonthOfYear(int monthOfYear) {
        this.monthOfYear = monthOfYear;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }
}

