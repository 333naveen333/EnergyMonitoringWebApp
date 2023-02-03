package com.energyms.energyms.model;

public class StatsDiff {
   
	private String powerConsumption="Power Consumption";
	private double thisMonthPowerConsumed;
	private double lastMonthPowerConsumed;
	private double powerDifference;//difference
	
	private String cost="Cost";
	private double thisMonthCost;
	private double lastMonthCost;
	private double costDifference;
	
	private String carbon="Carbon";
	private double thisMonthCarbon;
	private double lastMonthCarbon;
	private double carbonDifference;
	public String getPowerConsumption() {
		return powerConsumption;
	}
	public void setPowerConsumption(String powerConsumption) {
		this.powerConsumption = powerConsumption;
	}
	public double getThisMonthPowerConsumed() {
		return thisMonthPowerConsumed;
	}
	public void setThisMonthPowerConsumed(double thisMonthPowerConsumed) {
		this.thisMonthPowerConsumed = thisMonthPowerConsumed;
	}
	public double getLastMonthPowerConsumed() {
		return lastMonthPowerConsumed;
	}
	public void setLastMonthPowerConsumed(double lastMonthPowerConsumed) {
		this.lastMonthPowerConsumed = lastMonthPowerConsumed;
	}
	public double getPowerDifference() {
		return powerDifference;
	}
	public void setPowerDifference(double powerDifference) {
		this.powerDifference = powerDifference;
	}
	public String getCost() {
		return cost;
	}
	public void setCost(String cost) {
		this.cost = cost;
	}
	public double getThisMonthCost() {
		return thisMonthCost;
	}
	public void setThisMonthCost(double thisMonthCost) {
		this.thisMonthCost = thisMonthCost;
	}
	public double getLastMonthCost() {
		return lastMonthCost;
	}
	public void setLastMonthCost(double lastMonthCost) {
		this.lastMonthCost = lastMonthCost;
	}
	public double getCostDifference() {
		return costDifference;
	}
	public void setCostDifference(double costDifference) {
		this.costDifference = costDifference;
	}
	public String getCarbon() {
		return carbon;
	}
	public void setCarbon(String carbon) {
		this.carbon = carbon;
	}
	public double getThisMonthCarbon() {
		return thisMonthCarbon;
	}
	public void setThisMonthCarbon(double thisMonthCarbon) {
		this.thisMonthCarbon = thisMonthCarbon;
	}
	public double getLastMonthCarbon() {
		return lastMonthCarbon;
	}
	public void setLastMonthCarbon(double lastMonthCarbon) {
		this.lastMonthCarbon = lastMonthCarbon;
	}
	public double getCarbonDifference() {
		return carbonDifference;
	}
	public void setCarbonDifference(double carbonDiffernce) {
		this.carbonDifference = carbonDiffernce;
	}
	public StatsDiff(String powerConsumption, double thisMonthPowerConsumed, double lastMonthPowerConsumed,
			double powerDiffernce, String cost, double thisMonthCost, double lastMonthCost, double costDiffernce, String carbon,
			double thisMonthCarbon, double lastMonthCarbon, double carbonDiffernce) {
		super();
		this.powerConsumption = powerConsumption;
		this.thisMonthPowerConsumed = thisMonthPowerConsumed;
		this.lastMonthPowerConsumed = lastMonthPowerConsumed;
		this.powerDifference = powerDiffernce;
		this.cost = cost;
		this.thisMonthCost = thisMonthCost;
		this.lastMonthCost = lastMonthCost;
		this.costDifference = costDiffernce;
		this.carbon = carbon;
		this.thisMonthCarbon = thisMonthCarbon;
		this.lastMonthCarbon = lastMonthCarbon;
		this.carbonDifference = carbonDiffernce;
	}
	public StatsDiff() {
		super();
	}
	
	
}
