package com.energyms.energyms;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import com.energyms.energyms.model.Appliance;
import com.energyms.energyms.model.Device;
import com.energyms.energyms.model.Room;
import com.energyms.energyms.model.User;
import com.energyms.energyms.repository.ApplianceRepository;
import com.energyms.energyms.repository.DeviceRepository;
import com.energyms.energyms.service.DeviceService;


@RunWith(SpringRunner.class)
@SpringBootTest
public class DeviceServiceTest {

	@Autowired 
	private DeviceService deviceService;
	
	@MockBean
	private DeviceRepository deviceRepository;
	
	@MockBean
	private ApplianceRepository applianceRepository;
	
	@Test
	@DisplayName("Should fetch the total devices from database")
	void getDevices() {
		Device d1=new Device();
		d1.setDeviceId("id1");
		d1.setDeviceName("name1");
		Device d2=new Device();
		d2.setDeviceId("id2");
		d2.setDeviceName("name2");
		List<Device> dlist=new ArrayList<>();
		dlist.add(d1);
		dlist.add(d2);
		when(deviceRepository.findAll()).thenReturn(dlist);
		List<Device> dlist1=deviceService.getDevices();
		assertEquals(dlist,dlist1);
		
		
	}
	
	@Test
	@DisplayName("Should fetch the Inactive devices from database")
	void getInActiveDevices()
	{
		
		Device d1=new Device();
		d1.setDeviceId("id1");
		d1.setDeviceName("name1");
		
		Device d2=new Device();
		d2.setDeviceId("id2");
		d2.setDeviceName("name2");
		Device d3=new Device();
		d3.setDeviceId("id3");
		d3.setDeviceName("name3");
		
		List<Device> dlist=new ArrayList<>();
		dlist.add(d1);
		dlist.add(d2);
		dlist.add(d3);
		User user=new User("name","user1@gmail.com","password","8989898989");
		Optional<User>user1=Optional.of(user);
		Room room =new Room();
		room.setRoomId(1L);
		room.setRoomName("room");
		room.setUser(user);
		
		
		 //when(principal.getName()).thenReturn("user1@gmail.com");
		Appliance appliance = new Appliance("Ac", true, user,
				room, d1,"some time");
		
		Appliance appliance1 = new Appliance("Fan", true, user,
				room, d2,"some time");
		List<Appliance> alist=new ArrayList<>();
		alist.add(appliance1);
		alist.add(appliance);
		when(deviceRepository.findAll()).thenReturn(dlist);
		when(applianceRepository.findAll()).thenReturn(alist);
		
		List<Device> inactive=deviceService.getInAvtiveDevices();
		assertEquals(1,inactive.size());
		
	}
	

}
