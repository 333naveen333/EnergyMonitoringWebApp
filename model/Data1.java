package com.energyms.energyms.model;

import java.util.List;

public class Data1 {
    private List<DeviceDataOfDay1> deviceDataOfDayList;
    private double sumValue;

    public List<DeviceDataOfDay1> getDeviceDataOfDayList() {
        return deviceDataOfDayList;
    }

    public void setDeviceDataOfDayList(List<DeviceDataOfDay1> deviceDataOfDayList) {
        this.deviceDataOfDayList = deviceDataOfDayList;
    }

    public double getSumValue() {
        return sumValue;
    }

    public void setSumValue(double sumValue) {
        this.sumValue = sumValue;
    }
}
