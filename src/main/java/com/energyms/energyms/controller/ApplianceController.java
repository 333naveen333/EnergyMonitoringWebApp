package com.energyms.energyms.controller;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.energyms.energyms.dto.ApplianceDto;
import com.energyms.energyms.dto.ScheduleDto;
import com.energyms.energyms.model.Appliance;
import com.energyms.energyms.model.Device;
import com.energyms.energyms.model.Room;
import com.energyms.energyms.model.User;
import com.energyms.energyms.repository.ApplianceRepository;
import com.energyms.energyms.repository.DeviceRepository;
import com.energyms.energyms.repository.RoomRepository;
import com.energyms.energyms.repository.UserRepository;
import com.energyms.energyms.service.ApplianceService;

@CrossOrigin(origins="http://localhost:3000")
@RestController
public class ApplianceController {
	@Autowired
	private RoomRepository roomRepository;
	@Autowired
	 private UserRepository userRepository;
	@Autowired
	private ApplianceService applianceService;
//	@Autowired
//	private RoomService roomService;
	@Autowired
	 private DeviceRepository deviceRepository;
	@Autowired
	 private ApplianceRepository applianceRepository;
	
	
	@PostMapping("/registerAppliance/{roomName}")
	public ResponseEntity<?> registerAppliance(@PathVariable String roomName,@RequestBody ApplianceDto applianceDto,Principal principal)
	{
		//applianceDto.setRoomId(roomId);
		applianceDto.setUserEmailId(principal.getName());   // need to send device from path variabel to request body 
		                                                    // consequnces of these need to be change in all repos,services
		
		User user=userRepository.findByEmailId(applianceDto.getUserEmailId()).orElseThrow(() ->
        new UsernameNotFoundException(" email not found" + applianceDto.getUserEmailId()));
		
		Device device=deviceRepository.findByDeviceId(applianceDto.getDeviceId());
		if(device==null)
		{
			return new ResponseEntity<>("Device doesn't exist...check entered device", HttpStatus.BAD_REQUEST);
		}
		Room room=roomRepository.findByRoomNameAndUserEmailId(roomName,principal.getName());
		Appliance appliance1=applianceRepository.findByApplianceNameAndRoomRoomNameAndUserEmailId(applianceDto.getApplianceName(),roomName,applianceDto.getUserEmailId());
		if(room==null )
		{
			
			return new ResponseEntity<>("Create room to add appliance or check entered room name", HttpStatus.BAD_REQUEST);
			
		}
		Appliance appliance=applianceRepository.findByDeviceDeviceIdAndUserEmailId(applianceDto.getDeviceId(),applianceDto.getUserEmailId());
		if(appliance!=null)
		{
			return new ResponseEntity<>("Device is already in use", HttpStatus.BAD_REQUEST);
		}
		if(appliance1==null)
		{
		applianceService.save(applianceDto, roomName,applianceDto.getDeviceId());
		return new ResponseEntity<>("Appliance registered successfully", HttpStatus.OK);
		}
		
		else
		{
			return new ResponseEntity<>("appliance already register in this room ", HttpStatus.BAD_REQUEST);
		}
			
		
	}
	
//	@PostMapping("/applianceStatusChange/{applianceId}")
//	public ResponseEntity<?> applianceStatusChange(@PathVariable long applianceId)
//	{
//		applianceService.applianceStatusChange(applianceId);
//		return new ResponseEntity<>("Appliance statsu changed", HttpStatus.OK);
//	}
	@GetMapping("/getAllAppliances/{roomName}")
	public List<String> getAllAppliances(@PathVariable String roomName,Principal principal)     // get rooms of that particular logged in user
	{
		
		return applianceService.getAllAppliances(roomName,principal);
	} 
	
	@DeleteMapping("/deleteAppliance/{roomName}/{applianceName}")// we can include devicename
	public ResponseEntity<?> deleteAppliance(@PathVariable String roomName,@PathVariable String applianceName,Principal principal)
	{
		boolean s=applianceService.deleteAppliance(roomName,applianceName,principal);
		if(s==true)
		{
			return new ResponseEntity<>("Appliance Deleted successfully", HttpStatus.OK);
		}
		else
			return new ResponseEntity<>("Appliance deletion failed...check ", HttpStatus.BAD_REQUEST);
	}
	
	
	
	@PostMapping("/applianceStatusChange/{applianceName}/{roomName}")
	public ResponseEntity<?> applianceStatusChange(@PathVariable String applianceName,@PathVariable String roomName,Principal principal)
	{
		boolean s=applianceService.applianceStatusChange(applianceName,roomName,principal);
		if(s==true)
		return new ResponseEntity<>("Appliance status changed", HttpStatus.OK);
		return new ResponseEntity<>("Appliance status didn't changed ...please check entered data", HttpStatus.BAD_REQUEST);
	}
	
//	@PostMapping("/applianceSchedule/{applianceName}/{roomName}")
//	public ResponseEntity<?> applianceSchedule(@PathVariable String applianceName,@PathVariable String roomName,Principal principal,@RequestBody ScheduleDto scheduleDto)
//	{
//		
//		boolean s=applianceService.applianceSchedule(applianceName,roomName,principal,scheduleDto);
//		if(s==true)
//		return new ResponseEntity<>("Appliance schedule success", HttpStatus.OK);
//		return new ResponseEntity<>("Appliance schedule failed ...please check entered data", HttpStatus.BAD_REQUEST);
//		
//	}
	// appliances unused list
		// appliances used list
}