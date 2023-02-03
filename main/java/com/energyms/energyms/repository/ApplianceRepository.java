package com.energyms.energyms.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.energyms.energyms.dto.ApplianceDto;
import com.energyms.energyms.model.Appliance;
import com.energyms.energyms.model.User;

@Repository
public interface ApplianceRepository extends JpaRepository<Appliance, Long> {

	// Optional<Appliance> findByEmailId(String userEmailId);

	// void saveRoomId(long roomId);
	Optional<Appliance> findByApplianceId(long applianceId);

	Appliance findByDeviceDeviceId(String deviceId);

	Appliance findByDeviceDeviceIdAndUserEmailId(String deviceId, String emailId);

	// select distinct t.roomName from Room t where t.user= :user"

	@Query("select distinct t.applianceName from Appliance t where t.room.roomName=:roomName and t.user=:user ")
	List<String> findByRoomRoomName(@Param("roomName") String roomName, @Param("user") User user);// @Param("user")User
																									// user
	// @Query("select * from Appliance t where t.applianceName=:applianceName and
	// t.roomName=:roomName")

	Appliance findByApplianceNameAndRoomRoomName(String applianceName, String roomName);

	Appliance findByApplianceNameAndRoomRoomNameAndUserEmailId(String applianceName, String roomName,
			String userEmailId);

	Appliance findByApplianceNameAndRoomRoomNameAndUser(String applianceName, String roomName, User user);

}
