package com.energyms.energyms.model;
import lombok.Data;
import javax.persistence.*;

import java.io.Serializable;
import java.util.Set;

@Data
@Entity
@Table(name = "users", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"emailId"})
})
public class User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String fullName;
    private String emailId;
    private String password;
    
    
    
    public User() {
		//super();
	}
	public User(String fullName, String emailId, String password, String mobileNumber) {
		super();
		this.fullName = fullName;
		this.emailId = emailId;
		this.password = password;
		this.mobileNumber = mobileNumber;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getFullName() {
		return fullName;
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	public String getEmailId() {
		return emailId;
	}
	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getMobileNumber() {
		return mobileNumber;
	}
	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}
	private String mobileNumber;

   
}