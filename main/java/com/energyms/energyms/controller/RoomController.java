package com.energyms.energyms.controller;
import java.security.Principal;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.energyms.energyms.dto.RoomDto;
import com.energyms.energyms.model.Room;
import com.energyms.energyms.repository.RoomRepository;
import com.energyms.energyms.service.RoomService;
@CrossOrigin(origins="http://localhost:3000")
@RestController
public class RoomController {

	@Autowired
	private RoomService roomService;
	@Autowired
	private RoomRepository roomRepository;
	
	@PostMapping("/registerRoom")
	public ResponseEntity<?> registerRoom(@RequestBody RoomDto roomDto,Principal principal)
	{
		roomDto.setUserEmailId(principal.getName());
		//String s=;
		Room room=roomRepository.findByRoomNameAndUserEmailId(roomDto.getRoomName(),principal.getName());
		if(room!=null)
		{
			return  new ResponseEntity<>("Room with this name already registerd", HttpStatus.BAD_REQUEST);
			
		}
		else
		{
			roomService.save(roomDto);
		
		return new ResponseEntity<>("Room registered successfully", HttpStatus.OK);
		}
		
	}			
	
	@GetMapping("/getAllRooms")
	public List<String> getAllRooms(Principal principal)     // get rooms of that particular logged in user
	{
		return roomService.getAllRooms(principal);
	}
	
//	@DeleteMapping("/deleteRoom/{roomName}")
//	public ResponseEntity<?> deleteRoom(@PathVariable String roomName,Principal principal)
//	{
//		boolean s= roomService.deleteRoom(roomName,principal);
//		if(s==true)
//			return new ResponseEntity<>("room deleted ", HttpStatus.OK);
//		return new ResponseEntity<>("room deletion failed ", HttpStatus.BAD_REQUEST);
//	}
	
}
