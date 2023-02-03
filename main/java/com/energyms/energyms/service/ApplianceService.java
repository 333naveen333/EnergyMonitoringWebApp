package com.energyms.energyms.service;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.energyms.energyms.dto.ApplianceDto;
import com.energyms.energyms.dto.RoomDto;
import com.energyms.energyms.dto.ScheduleDto;
import com.energyms.energyms.model.Appliance;
import com.energyms.energyms.model.Device;
import com.energyms.energyms.model.Room;
import com.energyms.energyms.model.User;
import com.energyms.energyms.repository.ApplianceRepository;
import com.energyms.energyms.repository.DeviceRepository;
import com.energyms.energyms.repository.RoomRepository;
import com.energyms.energyms.repository.UserRepository;

@Service
public class ApplianceService {

	@Autowired
	private ApplianceRepository applianceRepository;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private RoomRepository roomRepository;
	@Autowired
	private DeviceRepository deviceRepository;

	public Appliance save(ApplianceDto applianceDto, String roomName, String deviceId) {
		User user = userRepository.findByEmailId(applianceDto.getUserEmailId())
				.orElseThrow(() -> new UsernameNotFoundException(" email not found" + applianceDto.getUserEmailId()));

		Room room = roomRepository.findByRoomNameAndUserEmailId(roomName, applianceDto.getUserEmailId());
		// ,roomId
		Device device = deviceRepository.findByDeviceId(deviceId);

		Appliance appliance = new Appliance(applianceDto.getApplianceName(), applianceDto.isApplianceStatus(), user,
				room, device);
		// applianceRepository.saveRoomId(applianceDto.getRoomId());
		return applianceRepository.save(appliance);

	}
//	public boolean applianceStatusChange(long applianceId) {
//		Appliance appliance=applianceRepository.findByApplianceId(applianceId).orElseThrow(() ->
//        new UsernameNotFoundException("not found  " + applianceId));
//		if(appliance.isApplianceStatus())
//		{
//			appliance.setApplianceStatus(false);
//			applianceRepository.save(appliance);
//		}
//		else
//		{
//			appliance.setApplianceStatus(true);
//			applianceRepository.save(appliance);
//		}
//		return true;

	// }

	public Optional<Room> findByRoomId(long id) {
		return roomRepository.findById(id);
	}

	public List<String> getAllAppliances(String roomName, Principal principal) {
		User user = userRepository.findByEmailId(principal.getName())
				.orElseThrow(() -> new UsernameNotFoundException(" email not found" + principal.getName()));

		return applianceRepository.findByRoomRoomName(roomName, user);

	}

	public boolean deleteAppliance(String roomName, String applianceName, Principal principal) {
		User user = userRepository.findByEmailId(principal.getName())
				.orElseThrow(() -> new UsernameNotFoundException(" email not found" + principal.getName()));
		Appliance appliance = applianceRepository.findByApplianceNameAndRoomRoomNameAndUser(applianceName, roomName,user);
		
		if(appliance!=null)
		{
			applianceRepository.delete(appliance);
			//deviceRepository.createDeviceThatIsDeleted(appliance.getDevice().getDeviceName(), appliance.getDevice().getDeviceName());
			//Device device=deviceRepository.findByDeviceId(appliance.getDevice().getDeviceId());
			//deviceRepository.save(device);
			return true;
		}
		else
			return false;		
	}
	

	public boolean applianceStatusChange(String applianceName, String roomName, Principal principal) {
		// TODO Auto-generated method stub
		// User user = userRepository.findByEmailId(principal.getName())
		// .orElseThrow(() -> new UsernameNotFoundException(" email not found" +
		// principal.getName()));
		Appliance appliance = applianceRepository.findByApplianceNameAndRoomRoomNameAndUserEmailId(applianceName,
				roomName, principal.getName());

		if (appliance != null) {
			if (appliance.isApplianceStatus()) {
				appliance.setApplianceStatus(false);
				applianceRepository.save(appliance);
			} else {
				appliance.setApplianceStatus(true);
				applianceRepository.save(appliance);
			}
			return true;
		}

		else {
			return false;
		}
	}

//	public boolean applianceSchedule(String applianceName, String roomName, Principal principal,ScheduleDto scheduleDto)
//	{
//		Appliance appliance = applianceRepository.findByApplianceNameAndRoomRoomNameAndUserEmailId(applianceName,
//				roomName, principal.getName());
//		
//		
//		
//		return false;
//
//		
//	}

}
