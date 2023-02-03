package com.energyms.energyms.service;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.energyms.energyms.dto.RoomDto;

import com.energyms.energyms.model.Room;
import com.energyms.energyms.model.User;

import com.energyms.energyms.repository.RoomRepository;
import com.energyms.energyms.repository.UserRepository;

@Service
public class RoomService {
	@Autowired
	private RoomRepository roomRepository;

	@Autowired
	private UserRepository userRepository;
	// @Autowired
//	private User user;

	public RoomService(RoomRepository roomRepository) {

		this.roomRepository = roomRepository;
	}

	public Room save(RoomDto roomDto) {
		User user = userRepository.findByEmailId(roomDto.getUserEmailId())
				.orElseThrow(() -> new UsernameNotFoundException(" email not found" + roomDto.getUserEmailId()));

		Room room = new Room(roomDto.getRoomName(), user);// ,(List<Appliance>)roomDto.getAppliances()

		return roomRepository.save(room);

	}

	
	public List<String> getAllRooms(Principal principal) {
		User user = userRepository.findByEmailId(principal.getName())
				.orElseThrow(() -> new UsernameNotFoundException(" email not found" + principal.getName()));

		return roomRepository.findByUser(user);

	}

//	public boolean deleteRoom(String roomName, Principal principal) {
//		// TODO Auto-generated method stub
//		User user = userRepository.findByEmailId(principal.getName())
//				.orElseThrow(() -> new UsernameNotFoundException(" email not found" + principal.getName()));
//		Room room = new Room(roomName, user);
//		if(room!=null)
//		{
//			roomRepository.delete(room);	
//			return true;
//		}
//			return false;
//		
//	}

}
