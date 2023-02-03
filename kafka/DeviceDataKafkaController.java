package com.energyms.energyms.kafka;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.energyms.energyms.model.Appliance;

import com.energyms.energyms.model.DeviceDataKafkaMessage;
import com.energyms.energyms.repository.ApplianceRepository;
import com.energyms.energyms.repository.DeviceDataRepository;

@RestController
public class DeviceDataKafkaController {

	@Autowired
	private DeviceDataRepository deviceDataRepository;
   @Autowired
    private DeviceKafkaProducer deviceKafkaProducer;
   @Autowired
   private ApplianceRepository applianceRepository;
	 @PostMapping("/deviceDataPublish")
	    public ResponseEntity<?> publish(@RequestBody DeviceDataKafkaMessage deviceDataKafkaMessage ){
		 String[] s=deviceDataKafkaMessage.getTopic().split("-", 2);
	    	String deviceId=s[1];
	    	Appliance appliance =applianceRepository.findByDeviceDeviceId(deviceId);
	    	if(appliance==null)
	    	{
	    		return new ResponseEntity<>("Device doesn't exist or device not in use", HttpStatus.BAD_REQUEST);
	    	}
	    	
		 deviceKafkaProducer.sendMessage(deviceDataKafkaMessage);
		
	    	return new ResponseEntity<>("message sent to kafka topic", HttpStatus.OK);
	    }
	 
	//@PostMapping("/getRoomsData")
	//public 
}
