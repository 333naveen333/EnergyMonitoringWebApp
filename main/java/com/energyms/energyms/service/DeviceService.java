package com.energyms.energyms.service;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.energyms.energyms.dto.DeviceDto;
import com.energyms.energyms.model.Device;
import com.energyms.energyms.model.User;
import com.energyms.energyms.repository.DeviceRepository;
import com.energyms.energyms.repository.UserRepository;


@Service
public class DeviceService  {

	@Autowired
	 private DeviceRepository deviceRepository;
	
	
	
	public DeviceService(DeviceRepository deviceRepository) {
		
		this.deviceRepository = deviceRepository;
	}

	


	public List<Device> getDevices() {
		// TODO Auto-generated method stub
		return deviceRepository.findAll();
	}
	
	

	   
}
