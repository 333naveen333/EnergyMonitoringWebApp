package com.energyms.energyms.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.energyms.energyms.dto.ApplianceDto;
import com.energyms.energyms.dto.RoomDto;
import com.energyms.energyms.model.Room;
import com.energyms.energyms.model.User;


@Repository
public interface RoomRepository extends JpaRepository<Room,Long> {

	//void save(RoomDto room);

	//RoomDto saveAll(RoomDto room);
	
	Room findByRoomNameAndUserEmailId(String roomName, String emailId );
    Room findByRoomName(String roomName);
	//List<Room> findByUser(User user);//use this to get room entities
	
//	e.company.id = :companyId"
	
	
	 @Query("select distinct t.roomName from Room t where t.user= :user")//use this to get list of room names only
		List<String> findByUser(@Param("user")User user);
  
}
