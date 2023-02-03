package com.energyms.energyms.model;
import java.util.List;
public class Data {
    private List<DeviceDataOfDay> deviceDataOfDayList;
    private double sumPrice;
    private double sumConsumption;
    private double sumco2Emission;

    public Data() {
    }

    public Data(List<DeviceDataOfDay> deviceDataOfDayList, double sumPrice, double sumConsumption, double sumco2Emission) {
        this.deviceDataOfDayList = deviceDataOfDayList;
        this.sumPrice = sumPrice;
        this.sumConsumption = sumConsumption;
        this.sumco2Emission = sumco2Emission;
    }

    public List<DeviceDataOfDay> getDeviceDataOfDayList() {
        return deviceDataOfDayList;
    }

    public void setDeviceDataOfDayList(List<DeviceDataOfDay> deviceDataOfDayList) {
        this.deviceDataOfDayList = deviceDataOfDayList;
    }

    public double getSumPrice() {
        return sumPrice;
    }

    public void setSumPrice(double sumPrice) {
        this.sumPrice = sumPrice;
    }

    public double getSumConsumption() {
        return sumConsumption;
    }

    public void setSumConsumption(double sumConsumption) {
        this.sumConsumption = sumConsumption;
    }

    public double getSumco2Emission() {
        return sumco2Emission;
    }

    public void setSumco2Emission(double sumco2Emission) {
        this.sumco2Emission = sumco2Emission;
    }
}
