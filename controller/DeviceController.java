package com.energyms.energyms.controller;


import java.security.Principal;
import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

//import com.energyms.energyms.kafka.DeviceKafkaProducer;
import com.energyms.energyms.model.Device;

import com.energyms.energyms.service.DeviceService;
@CrossOrigin(origins="http://localhost:3000")
@RestController
public class DeviceController {

	@Autowired
	private DeviceService deviceService;
//@Autowired
	//	private DeviceKafkaProducer deviceKafkaProducer;
	
	@GetMapping("/getDevices")
	public List<Device> getDevices()
	{
	 return	deviceService.getDevices();
	}
	
	//get devices unused 
	
	@GetMapping("/getInActiveDevices")
	public List<Device> getInActiveDevices()
	{
		
		List<Device> inActiveDevices=deviceService.getInAvtiveDevices();
		//deviceKafkaProducer.sendMessage(inActiveDevices);//
		return inActiveDevices;		
	}
	@GetMapping("/getActiveDevices")
	public List<Device> getActiveDevices()
	{
		
		List<Device> ActiveDevices=deviceService.getActiveDevices();
		//deviceKafkaProducer.sendMessage(inActiveDevices);//
		return ActiveDevices;		
	}
	
	    @GetMapping("/pagination/{offset}/{pageSize}")
	    private Page<Device> getProductsWithPagination(@PathVariable int offset, @PathVariable int pageSize) {
	        Page<Device> productsWithPagination = deviceService.findProductsWithPagination(offset, pageSize);
	        return  productsWithPagination;
	    }
	 
	  @GetMapping("/search/{query}")
	    public List<Device> searchProducts(@PathVariable String query){
	        return deviceService.searchProducts(query);
	    }
	  
	  @GetMapping("/pagination/{offset}/{pageSize}/{field}")
	    private Page<Device> getProductsWithPagination(@PathVariable int offset, @PathVariable int pageSize,@PathVariable String field) {
	        Page<Device> productsWithPagination = deviceService.findProductsWithPaginationAndSort(offset, pageSize,field);
	        return  productsWithPagination;
	    }
	 
	  
}
