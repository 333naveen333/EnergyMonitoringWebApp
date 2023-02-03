package com.energyms.energyms.repository;



import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.energyms.energyms.model.Device;
import com.energyms.energyms.model.User;

@Repository
public interface DeviceRepository extends JpaRepository<Device,Long> {

	Device findByDeviceId(String deviceId);
//	@Query("INSERT INTO devices (deviceId,deviceName) VALUES (:deviceId,:deviceName)")
//	//@Query("select distinct t.applianceName from Appliance t where t.room.roomName=:roomName and t.user=:user ")
//	void createDeviceThatIsDeleted(@Param("deviceId") String deviceId, @Param("deviceName") String deviceName);
	
}
