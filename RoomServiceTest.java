package com.energyms.energyms;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
//import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import com.energyms.energyms.dto.RoomDto;
import com.energyms.energyms.model.Room;
import com.energyms.energyms.model.User;
import com.energyms.energyms.repository.RoomRepository;
import com.energyms.energyms.service.RoomService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RoomServiceTest {
	
	@Autowired 
	private RoomService roomService;
	
	@MockBean
	private RoomRepository roomRepository;
	
	@Test
	@DisplayName("Should save the room into database")
	void save() {
		Room room =new Room();
		room.setRoomId(1L);
		room.setRoomName("room");
		User user=new User("name","user1@gmail.com","password","8989898989");
		room.setUser(user);

		RoomDto dto=new RoomDto("room2","user1@gmail.com");
	
		when(roomRepository.save(any(Room.class))).thenReturn(room);
		
		Room room1=roomService.save(dto);
		assertEquals("room",room1.getRoomName());
	
	}
	
	@Test
	@DisplayName("Fetching rooms from database")
	void getRooms()
	{
		Room room1 =new Room();
		room1.setRoomId(1L);
		room1.setRoomName("room");
		User user=new User("name","user1@gmail.com","password","8989898989");
		room1.setUser(user);
		
		Room room2 =new Room();
		room2.setRoomId(2L);
		room2.setRoomName("room");
		//User user=new User("name","user1@gmail.com","password","8989898989");
		room2.setUser(user);
		
		List<String> roomList=new ArrayList<>();
		roomList.add(room1.getRoomName());
		roomList.add(room2.getRoomName());
		when(roomRepository.findByUser(user.getEmailId())).thenReturn(roomList);
		//Principal p = new;
		List<String> rlist=roomService.getAllRooms(user.getEmailId());
		assertEquals(2,rlist.size());
	}
	
	

}
