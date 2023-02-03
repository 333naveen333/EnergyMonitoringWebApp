package com.energyms.energyms.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.RestController;


import com.energyms.energyms.model.Device;

import com.energyms.energyms.service.DeviceService;
@CrossOrigin(origins="http://localhost:3000")
@RestController
public class DeviceController {

	@Autowired
	private DeviceService deviceService;
	
	
	
	 
	@GetMapping("/getDevices")
	public List<Device> getDevices()
	{
	 return	deviceService.getDevices();
	}
	
	//get devices unused 
	
	
	
}
