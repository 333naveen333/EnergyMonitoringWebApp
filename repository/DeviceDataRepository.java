package com.energyms.energyms.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.energyms.energyms.model.Appliance;
import com.energyms.energyms.model.DeviceData;
@Repository

public interface DeviceDataRepository extends JpaRepository<DeviceData,Long>{
	List<DeviceData> findByDeviceId(String deviceId);
	List<DeviceData> findByUserId(String emailId);
	List<DeviceData> findByUserIdAndRoomName(String emailId,String roomName);
}
