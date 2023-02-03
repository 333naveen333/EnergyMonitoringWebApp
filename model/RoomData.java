package com.energyms.energyms.model;



//@Entity
//@Table(name = "roomData")
public class RoomData {
//	 @Id
//	    @GeneratedValue(strategy = GenerationType.IDENTITY)
//	    private long rowId;
	 private String emailId;
	 private String roomName;
	 private long noOfAppliancesOn;
	 private long noOfAppliancesOff;
	 private double todayPowerConsumed;
	public String getRoomName() {
		return roomName;
	}
	public void setRoomName(String roomName) {
		this.roomName = roomName;
	}
	public long getNoOfAppliancesOn() {
		return noOfAppliancesOn;
	}
	public void setNoOfAppliancesOn(long noOfAppliancesOn) {
		this.noOfAppliancesOn = noOfAppliancesOn;
	}
	public long getNoOfAppliancesOff() {
		return noOfAppliancesOff;
	}
	public void setNoOfAppliancesOff(long noOfAppliancesOff) {
		this.noOfAppliancesOff = noOfAppliancesOff;
	}
	public double getTodayPowerConsumed() {
		return todayPowerConsumed;
	}
	public void setTodayPowerConsumed(double todayPowerConsumed) {
		this.todayPowerConsumed = todayPowerConsumed;
	}
	
	public String getEmailId() {
		return emailId;
	}
	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}
	public RoomData(String emailId,String roomName, long noOfAppliancesOn, long noOfAppliancesOff, double todayPowerConsumed) {
		super();
		this.emailId=emailId;
		this.roomName = roomName;
		this.noOfAppliancesOn = noOfAppliancesOn;
		this.noOfAppliancesOff = noOfAppliancesOff;
		this.todayPowerConsumed = todayPowerConsumed;
	}
	public RoomData() {
		super();
	}
	 

	

}
