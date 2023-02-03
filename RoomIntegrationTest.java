package com.energyms.energyms;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import com.energyms.energyms.model.Room;
import com.energyms.energyms.model.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = EnergymsApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class RoomIntegrationTest {
	@LocalServerPort
	private int port;

	@Autowired
	private TestRestTemplate testRestTemplate;
	private HttpHeaders headers = new HttpHeaders();
	
	private String mapToJson(Object object) throws JsonProcessingException 
	{
		ObjectMapper objectMapper = new ObjectMapper();
		return objectMapper.writeValueAsString(object);
	}
	

	private String formFullURLWithPort(String uri)
	{
		return "http://localhost:" + port + uri;
	}
	@Test
	public void saveRoom() throws JsonProcessingException
	{
		Room room1 =new Room();
		room1.setRoomId(1L);
		room1.setRoomName("room");
		User user=new User("name","user1@gmail.com","password","8989898989");
		room1.setUser(user);
        String URIToCreateTicket = "/registerRoom";
		
	  //  String inputInJson = this.mapToJson(room1);
	    
	    HttpEntity<Room> entity = new HttpEntity<Room>(room1, headers);
		ResponseEntity<String> response = testRestTemplate.exchange(
				formFullURLWithPort(URIToCreateTicket),
				HttpMethod.POST, entity, String.class);
		
		String responseInJson = response.getBody();
		System.out.print(responseInJson);
		assertThat(responseInJson).isEqualTo("Room registered successfully");
	}
	

}
