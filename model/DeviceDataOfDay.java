package com.energyms.energyms.model;

import javax.persistence.*;
import java.util.List;
public class DeviceDataOfDay {
    private String label;
    private double price;
    private double Consumption;
    private double co2Emission;

    public DeviceDataOfDay() {
    }

    public DeviceDataOfDay(String label, double price, double consumption, double co2Emission) {
        this.label = label;
        this.price = price;
        Consumption = consumption;
        this.co2Emission = co2Emission;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getConsumption() {
        return Consumption;
    }

    public void setConsumption(double consumption) {
        Consumption = consumption;
    }

    public double getCo2Emission() {
        return co2Emission;
    }

    public void setCo2Emission(double co2Emission) {
        this.co2Emission = co2Emission;
    }
}
