package com.energyms.energyms.repository;



import org.springframework.data.jpa.repository.JpaRepository;

import com.energyms.energyms.model.DeviceData1;
import com.energyms.energyms.model.User;

import java.util.List;

public interface DeviceData1Repository extends JpaRepository<DeviceData1,Integer> {
    //DeviceData findByDeviceDeviceId(String deviceId);

    List<DeviceData1> findByDeviceId(String deviceId);
    //List<DeviceData> findByDeviceDeviceIdAndRoomRoomNameAndUser(String deviceId, String roomName, User user);
    List<DeviceData1> findBydayOfMonth(int today);

    //@Query(value = "SELECT deviceId, applianceName,price,Consumption,co2Emission FROM DeviceData", nativeQuery = true)
    //@Query("select s.deviceId, s.applianceName,s.price,s.Consumption,s.co2Emission from DeviceData s")
    //@Query("select distinct t.deviceId,t.applianceName,t.price,t.Consumption,t.co2Emission from DeviceData t")
    //@Query(" SELECT new DeviceData(t.deviceId,t.applianceName,t.price,t.Consumption,t.co2Emission) FROM DeviceData t")
    List<DeviceData1> findByDeviceIdAndApplianceNameAndUser(String deviceId, String applianceName, User user);

    //List<DeviceData> findByDeviceIdAndApplianceNameAndUserdayOfMonth(String deviceId, String applianceName, User user1, int today);

    List<DeviceData1> findByDeviceIdAndApplianceNameAndUserAndDayOfMonth(String deviceId, String applianceName, User user1, int today);

    List<DeviceData1> findByDeviceIdAndApplianceNameAndUserAndMonthOfYear(String deviceId, String applianceName, User user1, int month);

    //List<DeviceData> findByDeviceIdAndApplianceNameAndUserAndYear(String deviceId, String applianceName, User user1, int year);

    List<DeviceData1> findByDeviceIdAndApplianceNameAndUserAndMonthOfYearAndYear(String deviceId, String applianceName, User user1, int i, int year);

    List<DeviceData1> findByDeviceIdAndApplianceNameAndUserAndDayOfMonthAndYearAndMonthOfYear(String deviceId, String applianceName, User user1, int i, int year, int month);

   List< DeviceData1> findByDeviceIdAndApplianceNameAndUserAndDayOfMonthAndYearAndMonthOfYearAndHour(String deviceId, String applianceName, User user1, int today, int year, int month, int i);
List<DeviceData1> findByDeviceIdAndApplianceNameAndUserAndMonthOfYearAndDayOfMonth(String deviceId,
		String applianceName, User user1, int month, int j);

    //List<DeviceData> findByDeviceIdAndRoomNameAndUser(String deviceId, String roomName, User user);

   // List<DeviceData> findByDeviceIdAndRoomRoomNameAndUser(String deviceId, String roomName, User user);
}
