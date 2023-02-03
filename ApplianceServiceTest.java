package com.energyms.energyms;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.CALLS_REAL_METHODS;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import com.energyms.energyms.dto.ApplianceDto;
import com.energyms.energyms.model.Appliance;
import com.energyms.energyms.model.Device;
import com.energyms.energyms.model.DeviceData;
import com.energyms.energyms.model.Room;
import com.energyms.energyms.model.RoomData;
import com.energyms.energyms.model.User;
import com.energyms.energyms.repository.ApplianceRepository;
import com.energyms.energyms.repository.DeviceDataRepository;
//import com.energyms.energyms.repository.RoomDataRepository;
import com.energyms.energyms.repository.RoomRepository;
import com.energyms.energyms.repository.UserRepository;
import com.energyms.energyms.service.ApplianceService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ApplianceServiceTest {

	@Autowired
	private ApplianceService applianceService;

	@MockBean
	private ApplianceRepository applianceRepository;

	@MockBean
	private UserRepository userRepository;

//@MockBean
//private RoomDataRepository roomDataRepository;
	@MockBean
	private RoomRepository roomRepository;
	@MockBean
	private DeviceDataRepository deviceDataRepository;

	@Mock
	private Principal principal;

	@Mock
	private User userStub;

	@Test
	@DisplayName("Should save the appliance into database")
	void saveAppliance() {
		User user = new User("name", "user1@gmail.com", "password", "8989898989");
		Optional<User> user1 = Optional.of(user);
		Room room = new Room();
		room.setRoomId(1L);
		room.setRoomName("room");
		room.setUser(user);
		Device device = new Device();
		device.setDeviceId("id1");
		device.setDeviceName("deviceName");
		// when(principal.getName()).thenReturn("user1@gmail.com");
		Appliance appliance = new Appliance("Ac", true, user, room, device, "some time");
		ApplianceDto dto = new ApplianceDto("Fan", "user1@gmail.com", 1, "device", true, "room");
		when(userRepository.findByEmailId("user1@gmail.com")).thenReturn(user1);
		// String applianceName, String userEmailId, long roomId, String deviceId,
		// boolean applianceStatus,String roomName)
		when(applianceRepository.save(any(Appliance.class))).thenReturn(appliance);
		Appliance appliance1 = applianceService.save(dto, "room", "deviceName");
		assertEquals("room", appliance1.getRoom().getRoomName());
	}

	@Test
	@DisplayName("Should fetch the appliances from database")
	void getAllAppliances() {
		User user = new User("name", "user1@gmail.com", "password", "8989898989");

		Optional<User> user1 = Optional.of(user);

		Room room = new Room();
		room.setRoomId(1L);
		room.setRoomName("room");
		room.setUser(user);
		Device device = new Device();
		device.setDeviceId("id1");
		device.setDeviceName("deviceName");

		Appliance appliance1 = new Appliance("Ac", true, user, room, device, "some time");
		Appliance appliance2 = new Appliance("Fan", true, user, room, device, "some time");
		List<Appliance> list = new ArrayList<>();
		list.add(appliance1);
		list.add(appliance2);
		when(principal.getName()).thenReturn("user1@gmail.com");

		when(applianceRepository.findByRoomRoomName("room", user)).thenReturn(list);
		when(userRepository.findByEmailId("user1@gmail.com")).thenReturn(user1);
		List<Appliance> list1 = applianceService.getAllAppliances("room", principal);
		System.out.println("------------");
		System.out.println(list1.size());
		assertEquals(2, list1.size());
	}

	@Test
	@DisplayName("Should delete the appliance from database")
	void deleteAppliance() {
		User user = new User("name", "user1@gmail.com", "password", "8989898989");

		Optional<User> user1 = Optional.of(user);

		Room room = new Room();
		room.setRoomId(1L);
		room.setRoomName("room");
		room.setUser(user);
		Device device = new Device();
		device.setDeviceId("id1");
		device.setDeviceName("deviceName");
		when(principal.getName()).thenReturn("user1@gmail.com");
		when(userRepository.findByEmailId("user1@gmail.com")).thenReturn(user1);
		Appliance appliance1 = new Appliance("Ac", true, user, room, device, "some time");

		when(applianceRepository.findByApplianceNameAndRoomRoomNameAndUser("Ac", "room", user)).thenReturn(appliance1);
		doNothing().when(applianceRepository).delete(appliance1);
		applianceService.deleteAppliance("room", "Ac", principal);
		verify(applianceRepository, times(1)).delete(appliance1);
	}

	@Test
	@DisplayName("Should change the appliance status ")
	void applianceStatusChange() {
		User user = new User("name", "user1@gmail.com", "password", "8989898989");

		Optional<User> user1 = Optional.of(user);

		Room room = new Room();
		room.setRoomId(1L);
		room.setRoomName("room");
		room.setUser(user);
		Device device = new Device();
		device.setDeviceId("id1");
		device.setDeviceName("deviceName");
		when(principal.getName()).thenReturn("user1@gmail.com");
		when(userRepository.findByEmailId("user1@gmail.com")).thenReturn(user1);
		Appliance appliance1 = new Appliance("Ac", false, user, room, device, "24/01/2023  16:28:28 ");
		when(applianceRepository.findByApplianceNameAndRoomRoomNameAndUserEmailId("Ac", "room", "user1@gmail.com"))
				.thenReturn(appliance1);
		when(applianceRepository.save(appliance1)).thenReturn(appliance1);
		// appliance1.setApplianceStatus(true);

		String a = applianceService.applianceStatusChange("Ac", "room", principal);
		// System.out.println("-----------------"+a);-->if we give wrong appliance then
		// o/p->unable to chan
		assertEquals(true, a.contains("turned"));
		assertEquals(true, appliance1.isApplianceStatus());
		String b = applianceService.applianceStatusChange("Acc", "room", principal);
		assertEquals(true, b.contains("unable"));

	}

	@Test
	@DisplayName("Get all rooms and its data for today ")
	void getEachRoomDataPerDay() {
		Room room1 = new Room();
		room1.setRoomId(1L);
		room1.setRoomName("room");
		User user = new User("name", "user1@gmail.com", "password", "8989898989");
		room1.setUser(user);
		Optional<User> user1 = Optional.of(user);
		Room room2 = new Room();
		room2.setRoomId(2L);
		room2.setRoomName("room");

		room2.setUser(user);
		Device device1 = new Device();
		device1.setDeviceId("id1");
		device1.setDeviceName("deviceName");
		Device device2 = new Device();
		device2.setDeviceId("id2");
		device2.setDeviceName("deviceName");

		List<Room> roomList = new ArrayList<>();
		roomList.add(room1);
		roomList.add(room2);
		when(principal.getName()).thenReturn("user1@gmail.com");

		Appliance appliance1 = new Appliance("Ac", false, user, room1, device1, "24/01/2023  16:28:28 ");
		Appliance appliance2 = new Appliance("fan", true, user, room2, device2, "24/01/2023  16:28:28 ");
		Appliance appliance3 = new Appliance("fan", true, user, room2, device2, "24/01/2023  16:28:28 ");

		List<Appliance> a1 = new ArrayList<>();
		a1.add(appliance1);
		List<Appliance> a2 = new ArrayList<>();
		a2.add(appliance2);
		a2.add(appliance3);

		DeviceData d = new DeviceData("user1@gmail.com", "id1", "Ac", "room", 508323, "27/01/2023  16:38:02 ", 0.1,
				0.1);
		List<DeviceData> d1 = new ArrayList<>();
		d1.add(d);

		when(userRepository.findByEmailId("user1@gmail.com")).thenReturn(user1);
		when(roomRepository.findByUserEmailId(principal.getName())).thenReturn(roomList);

		when(applianceRepository.findByApplianceStatusAndUserEmailIdAndRoomRoomName(true, principal.getName(), "room"))
				.thenReturn(a2);

		when(applianceRepository.findByApplianceStatusAndUserEmailIdAndRoomRoomName(false, principal.getName(), "room"))
				.thenReturn(a1);
		when(deviceDataRepository.findByUserIdAndRoomName(principal.getName(), "room")).thenReturn(d1);

		List<RoomData> d11 = applianceService.getEachRoomDataPerDay(principal);

		assertEquals(2, d11.get(0).getNoOfAppliancesOn());// 2 or a2.size()
		assertEquals(d.getPowerConsumption(), d11.get(0).getTodayPowerConsumed());// in device data if todays date is
																					// there it will passed
	}

	@Test
	@DisplayName("Get all rooms and its data for last 7 days ")
	void getSevenDaysRoomData() {
		Room room1 = new Room();
		room1.setRoomId(1L);
		room1.setRoomName("room");
		User user = new User("name", "user1@gmail.com", "password", "8989898989");
		room1.setUser(user);
		Optional<User> user1 = Optional.of(user);
		Room room2 = new Room();
		room2.setRoomId(2L);
		room2.setRoomName("room");

		room2.setUser(user);
		Device device1 = new Device();
		device1.setDeviceId("id1");
		device1.setDeviceName("deviceName");
		Device device2 = new Device();
		device2.setDeviceId("id2");
		device2.setDeviceName("deviceName");

		List<Room> roomList = new ArrayList<>();
		roomList.add(room1);
		roomList.add(room2);
		when(principal.getName()).thenReturn("user1@gmail.com");

		Appliance appliance1 = new Appliance("Ac", false, user, room1, device1, "24/01/2023  16:28:28 ");
		Appliance appliance2 = new Appliance("fan", true, user, room2, device2, "24/01/2023  15:28:28 ");
		Appliance appliance3 = new Appliance("light", true, user, room2, device2, "24/01/2023  14:28:28 ");

		List<Appliance> a1 = new ArrayList<>();
		a1.add(appliance1);
		List<Appliance> a2 = new ArrayList<>();
		a2.add(appliance2);
		a2.add(appliance3);

		DeviceData d = new DeviceData("user1@gmail.com", "id1", "Ac", "room", 508323, "25/01/2023  16:38:02 ", 0.1,
				0.1);
		List<DeviceData> d1 = new ArrayList<>();
		d1.add(d);

		when(userRepository.findByEmailId("user1@gmail.com")).thenReturn(user1);
		when(roomRepository.findByUserEmailId(principal.getName())).thenReturn(roomList);

		when(applianceRepository.findByApplianceStatusAndUserEmailIdAndRoomRoomName(true, principal.getName(), "room"))
				.thenReturn(a2);
		

		when(applianceRepository.findByApplianceStatusAndUserEmailIdAndRoomRoomName(false, principal.getName(), "room"))
				.thenReturn(a1);
		when(deviceDataRepository.findByUserIdAndRoomName(principal.getName(), "room")).thenReturn(d1);

		List<RoomData> d11 = applianceService.getSevenDaysRoomData(principal);

		assertEquals(2, d11.get(0).getNoOfAppliancesOn());// 2 or a2.size()
		assertEquals(d.getPowerConsumption(), d11.get(0).getTodayPowerConsumed());
	}
	
	@Test
	@DisplayName("Get Stats difference of last month and current month ")
	void getStatsDiff()
	{
		DeviceData d1 = new DeviceData("user1@gmail.com", "id1", "Ac", "room", 508323, "25/12/2022  16:38:02 ", 0.1,
				0.1);
	
		DeviceData d2 = new DeviceData("user1@gmail.com", "id1", "Ac", "room", 508325, "25/01/2023  16:38:02 ", 0.1,
				0.1);
		List<DeviceData> d = new ArrayList<>();
		d.add(d2);
		d.add(d1);
		when(deviceDataRepository.findByUserId(principal.getName())).thenReturn(d);
		assertEquals(2,applianceService.getStatsDiff(principal).getPowerDifference());
		
	}
	@Test
	@DisplayName("Get Data stats for last 7 days ")
	void getSevenDaysStat()
	{
		DeviceData d1 = new DeviceData("user1@gmail.com", "id1", "Ac", "room", 508323, "25/01/2023  16:38:02 ", 0.1,
				0.1);
	
		DeviceData d2 = new DeviceData("user1@gmail.com", "id1", "Ac", "room", 5, "24/01/2023  16:38:02 ", 0.1,
				0.1);
		List<DeviceData> d = new ArrayList<>();
		d.add(d2);
		d.add(d1);
		when(deviceDataRepository.findByUserId(principal.getName())).thenReturn(d);
		Room room1 = new Room();
		room1.setRoomId(1L);
		room1.setRoomName("room");
		User user = new User("name", "user1@gmail.com", "password", "8989898989");
		Device device1 = new Device();
		device1.setDeviceId("id1");
		device1.setDeviceName("deviceName");
		Device device2 = new Device();
		device2.setDeviceId("id2");
		device2.setDeviceName("deviceName");
		Appliance appliance1 = new Appliance("Ac", false, user, room1, device1, "24/01/2023  16:28:28 ");
		Appliance appliance2 = new Appliance("fan", true, user, room1, device2, "24/01/2023  15:28:28 ");
		Appliance appliance3 = new Appliance("light", true, user, room1, device2, "24/01/2023  14:28:28 ");

		List<Appliance> a1 = new ArrayList<>();
		a1.add(appliance1);
		List<Appliance> a2 = new ArrayList<>();
		a2.add(appliance2);
		a2.add(appliance3);
		when(applianceRepository.findByApplianceStatusAndUserEmailIdAndRoomRoomName(true, principal.getName(), "room"))
		.thenReturn(a2);


when(applianceRepository.findByApplianceStatusAndUserEmailIdAndRoomRoomName(false, principal.getName(), "room"))
		.thenReturn(a1);
assertEquals(applianceService.getSevenDaysStat(principal).getTotalPowerConsumed(),508328);
	}
	@Test
	@DisplayName("Get Data stats for last this month ")
	void getDataStatForThisMonth()
	{
		DeviceData d1 = new DeviceData("user1@gmail.com", "id1", "Ac", "room", 508323, "25/01/2023  16:38:02 ", 0.1,
				0.1);
	
		DeviceData d2 = new DeviceData("user1@gmail.com", "id1", "Ac", "room", 5, "24/01/2023  16:38:02 ", 0.1,
				0.1);
		List<DeviceData> d = new ArrayList<>();
		d.add(d2);
		d.add(d1);
		when(deviceDataRepository.findByUserId(principal.getName())).thenReturn(d);
		Room room1 = new Room();
		room1.setRoomId(1L);
		room1.setRoomName("room");
		User user = new User("name", "user1@gmail.com", "password", "8989898989");
		Device device1 = new Device();
		device1.setDeviceId("id1");
		device1.setDeviceName("deviceName");
		Device device2 = new Device();
		device2.setDeviceId("id2");
		device2.setDeviceName("deviceName");
		Appliance appliance1 = new Appliance("Ac", false, user, room1, device1, "24/01/2023  16:28:28 ");
		Appliance appliance2 = new Appliance("fan", true, user, room1, device2, "24/01/2023  15:28:28 ");
		Appliance appliance3 = new Appliance("light", true, user, room1, device2, "24/01/2023  14:28:28 ");

		List<Appliance> a1 = new ArrayList<>();
		a1.add(appliance1);
		List<Appliance> a2 = new ArrayList<>();
		a2.add(appliance2);
		a2.add(appliance3);
		when(applianceRepository.findByApplianceStatusAndUserEmailIdAndRoomRoomName(true, principal.getName(), "room"))
		.thenReturn(a2);


when(applianceRepository.findByApplianceStatusAndUserEmailIdAndRoomRoomName(false, principal.getName(), "room"))
		.thenReturn(a1);
assertEquals(applianceService.getDataStatForThisMonth(principal).getTotalPowerConsumed(),508328);
	}
}
