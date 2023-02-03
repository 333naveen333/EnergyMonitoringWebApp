package com.energyms.energyms.model;



import com.sun.jdi.PrimitiveValue;

import javax.persistence.*;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.Month;

@Entity
@Table(name = "DeviceDatanew")
public class DeviceData1 {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int deviceDataId;

    private String deviceId;

    private String applianceName;

    private double eventValue;

    private LocalDateTime timestamp;
    private long timeInMinutes;

    private double price;

    private double pricePerUnit;

    private double co2Emission;

    private double co2EmissionPerUnit;
    private long totalTimeInMinutes;

    //private LocalDateTime timeAtAHour;

    private double Consumption;

    private LocalDateTime timeWhenAppisOn;

    private Month month;

    private DayOfWeek dayOfWeek;
    private int hour;
    private int dayOfMonth;
    private int monthOfYear;
    private int year;

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_email_id", referencedColumnName = "emailId")
    private User user;

    public DeviceData1() {
    }

    public DeviceData1(String deviceId, String applianceName, int eventValue, LocalDateTime timestamp, long consumption, User user) {
        this.deviceId = deviceId;
        this.applianceName = applianceName;
        this.eventValue = eventValue;
        this.timestamp = timestamp;
        Consumption = consumption;
        this.user = user;
    }

    public DeviceData1(String deviceId, String applianceName, int eventValue, LocalDateTime timestamp, long timeInMinutes, double consumption, User user) {
        this.deviceId = deviceId;
        this.applianceName = applianceName;
        this.eventValue = eventValue;
        this.timestamp = timestamp;
        this.timeInMinutes = timeInMinutes;
        this.Consumption = consumption;
        this.user = user;
    }

    public DeviceData1(String deviceId, String applianceName,double co2Emission,double price,double consumption) {
        this.deviceId = deviceId;
        this.applianceName = applianceName;
        this.co2Emission = co2Emission;
        this.Consumption = consumption;
        this.price=price;

    }

    public Month getMonth() {
        return month;
    }

    public void setMonth(Month month) {
        this.month = month;
    }

    public DayOfWeek getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(DayOfWeek dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public DeviceData1(String deviceId, String applianceName, double eventValue, LocalDateTime timestamp, LocalDateTime timeWhenAppisOn, long timeInMinutes, double consumption, User user) {
        this.deviceId = deviceId;
        this.applianceName = applianceName;
        this.eventValue = eventValue;
        this.timestamp = timestamp;
        this.timeWhenAppisOn = timeWhenAppisOn;
        this.timeInMinutes = timeInMinutes;
        Consumption = consumption;
        this.user = user;
    }

    public DeviceData1(String deviceId, String applianceName, double eventValue, LocalDateTime timestamp, LocalDateTime timeWhenAppisOn, long timeInMinutes, double consumption, int hour, int dayOfMonth, int monthOfYear, int year, User user) {
        this.deviceId = deviceId;
        this.applianceName = applianceName;
        this.eventValue = eventValue;
        this.timestamp = timestamp;
        this.timeWhenAppisOn = timeWhenAppisOn;
        this.timeInMinutes = timeInMinutes;
        Consumption = consumption;
        this.hour = hour;
        this.dayOfMonth = dayOfMonth;
        this.monthOfYear = monthOfYear;
        this.year = year;
        this.user = user;
    }

    public DeviceData1(String deviceId, String applianceName, double eventValue, LocalDateTime timestamp, long timeInMinutes, double price, double pricePerUnit, double co2Emission, double co2EmissionPerUnit, long totalTimeInMinutes, double consumption, LocalDateTime timeWhenAppisOn, int hour, int dayOfMonth, int monthOfYear, int year, User user) {
        this.deviceId = deviceId;
        this.applianceName = applianceName;
        this.eventValue = eventValue;
        this.timestamp = timestamp;
        this.timeInMinutes = timeInMinutes;
        this.price = price;
        this.pricePerUnit = pricePerUnit;
        this.co2Emission = co2Emission;
        this.co2EmissionPerUnit = co2EmissionPerUnit;
        this.totalTimeInMinutes = totalTimeInMinutes;
        Consumption = consumption;
        this.timeWhenAppisOn = timeWhenAppisOn;
        this.hour = hour;
        this.dayOfMonth = dayOfMonth;
        this.monthOfYear = monthOfYear;
        this.year = year;
        this.user = user;
    }

    public DeviceData1(String deviceId, String applianceName, double eventValue, LocalDateTime timestamp, long timeInMinutes, long totalTimeInMinutes, double consumption, LocalDateTime timeWhenAppisOn, int hour, int dayOfMonth, int monthOfYear, int year, User user) {
        this.deviceId = deviceId;
        this.applianceName = applianceName;
        this.eventValue = eventValue;
        this.timestamp = timestamp;
        this.timeInMinutes = timeInMinutes;
        this.totalTimeInMinutes = totalTimeInMinutes;
        Consumption = consumption;
        this.timeWhenAppisOn = timeWhenAppisOn;
        this.hour = hour;
        this.dayOfMonth = dayOfMonth;
        this.monthOfYear = monthOfYear;
        this.year = year;
        this.user = user;
    }

    public long getTimeInMinutes() {
        return timeInMinutes;
    }

    public void setTimeInMinutes(long timeInMinutes) {
        this.timeInMinutes = timeInMinutes;
    }

    public int getDeviceDataId() {
        return deviceDataId;
    }

    public void setDeviceDataId(int deviceDataId) {
        this.deviceDataId = deviceDataId;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
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

    public long getTotalTimeInMinutes() {
        return totalTimeInMinutes;
    }

    public void setTotalTimeInMinutes(long totalTimeInMinutes) {
        this.totalTimeInMinutes = totalTimeInMinutes;
    }

    public void setEventValue(double eventValue) {
        this.eventValue = eventValue;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public double getConsumption() {
        return Consumption;
    }

    public void setConsumption(double consumption) {
        Consumption = consumption;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getHour() {
        return hour;
    }

    public LocalDateTime getTimeWhenAppisOn() {
        return timeWhenAppisOn;
    }

    public void setTimeWhenAppisOn(LocalDateTime timeWhenAppisOn) {
        this.timeWhenAppisOn = timeWhenAppisOn;
    }

    public void setHour(int hour) {
        this.hour = hour;
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