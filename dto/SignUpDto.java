package com.energyms.energyms.dto;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

import lombok.Data;
@Data
public class SignUpDto {
    private String fullName;
    
    @Email(message="Please provide a valid email address")
    @Pattern(regexp=".+@.+\\..+", message="Please provide a valid email address")
    @NotEmpty(message = "Email cannot be empty")
    private String emailId;
    
    @NotEmpty(message="Password cannot be empty")
    private String password;
    
    @Pattern(regexp = "^[6-9]{1}[0-9]{9}$",message="please provide valid mobile number")
    private String mobileNumber;
    public SignUpDto() {
		//super();
	}
	public SignUpDto(String fullName, String emailId, String password, String mobileNumber) {
		super();
		this.fullName = fullName;
		this.emailId = emailId;
		this.password = password;
		this.mobileNumber = mobileNumber;
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
	
}
