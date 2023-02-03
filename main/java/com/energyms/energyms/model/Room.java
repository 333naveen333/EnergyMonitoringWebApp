package com.energyms.energyms.model;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import javax.persistence.Table;
//import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "rooms")
public class Room implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long roomId;
    //@Column(unique=false)
	private String roomName;

	public long getRoomId() {
		return roomId;
	}

	public void setRoomId(long roomId) {
		this.roomId = roomId;
	}

	public String getRoomName() {
		return roomName;
	}

	public void setRoomName(String roomName) {
		this.roomName = roomName;
	}

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "email_id", referencedColumnName = "emailId")
	private User user;

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	
	
	
	public Room() {
		super();
	}

	public Room(String roomName, User user) {
		super();
		this.roomName = roomName;
		this.user = user;
	}

	
	
	
	
	
	
//		  @OneToMany(cascade=CascadeType.ALL)
//	        @JoinColumn(name="room_id")
//		    private List<Appliance>appliances=new ArrayList<>();
}
