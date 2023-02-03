package com.energyms.energyms.controller;

import java.security.Principal;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


import com.energyms.energyms.dto.ApplianceDto;
import com.energyms.energyms.dto.ScheduleDto;
import com.energyms.energyms.model.Appliance;
import com.energyms.energyms.model.Data;
import com.energyms.energyms.model.Data1;
import com.energyms.energyms.model.DataStats;
import com.energyms.energyms.model.Device;
import com.energyms.energyms.model.DeviceData1;
import com.energyms.energyms.model.DeviceDataOfDay;
import com.energyms.energyms.model.DeviceDataOfDay1;
//import com.energyms.energyms.model.DeviceData;
import com.energyms.energyms.model.Room;
import com.energyms.energyms.model.RoomData;
import com.energyms.energyms.model.StatsDiff;
//import com.energyms.energyms.model.RoomData;
import com.energyms.energyms.model.User;
import com.energyms.energyms.repository.ApplianceRepository;
import com.energyms.energyms.repository.DeviceData1Repository;
import com.energyms.energyms.repository.DeviceRepository;
import com.energyms.energyms.repository.RoomRepository;
import com.energyms.energyms.repository.UserRepository;
import com.energyms.energyms.service.ApplianceService;
import com.energyms.energyms.service.DeviceService;

@CrossOrigin(origins="http://localhost:3000")
@RestController
public class ApplianceController {
	@Autowired
	private RoomRepository roomRepository;
	@Autowired
	 private UserRepository userRepository;
	@Autowired
	private ApplianceService applianceService;
	@Autowired
	private DeviceService deviceService;
//	@Autowired
//	private RoomService roomService;
	@Autowired
	 private DeviceRepository deviceRepository;
	@Autowired
	 private ApplianceRepository applianceRepository;
	@Autowired
	private DeviceData1Repository deviceData1Repository;
	
	
	@PostMapping("/registerAppliance")
	public ResponseEntity<?> registerAppliance(@RequestBody ApplianceDto applianceDto,Principal principal)
	{
		//applianceDto.setRoomId(roomId);
		applianceDto.setUserEmailId(principal.getName());   // need to send device from path variabel to request body 
		                                                    // consequnces of these need to be change in all repos,services
		
		User user=userRepository.findByEmailId(applianceDto.getUserEmailId()).orElseThrow(() ->
        new UsernameNotFoundException(" email not found" + applianceDto.getUserEmailId()));
		
		Device device=deviceRepository.findByDeviceId(applianceDto.getDeviceId());
		if(device==null)
		{
			return new ResponseEntity<>("Device doesn't exist...check entered device", HttpStatus.BAD_REQUEST);
		}
		Room room=roomRepository.findByRoomNameAndUserEmailId(applianceDto.getRoomName(),principal.getName());
		Appliance appliance1=applianceRepository.findByApplianceNameAndRoomRoomNameAndUserEmailId(applianceDto.getApplianceName(),applianceDto.getRoomName(),applianceDto.getUserEmailId());
		if(room==null )
		{
			
			return new ResponseEntity<>("Create room to add appliance or check entered room name", HttpStatus.BAD_REQUEST);
			
		}
		Appliance appliance=applianceRepository.findByDeviceDeviceId(applianceDto.getDeviceId());
		if(appliance!=null)
		{
			return new ResponseEntity<>("Device is already in use", HttpStatus.BAD_REQUEST);
		}
		if(appliance1==null)
		{
		applianceService.save(applianceDto, applianceDto.getRoomName(),applianceDto.getDeviceId());
		return new ResponseEntity<>("Appliance registered successfully", HttpStatus.OK);
		}
		
		else
		{
			return new ResponseEntity<>("appliance already register in this room ", HttpStatus.BAD_REQUEST);
		}
			
		
	}
	
//	@PostMapping("/applianceStatusChange/{applianceId}")
//	public ResponseEntity<?> applianceStatusChange(@PathVariable long applianceId)
//	{
//		applianceService.applianceStatusChange(applianceId);
//		return new ResponseEntity<>("Appliance statsu changed", HttpStatus.OK);
//	}
	
	@GetMapping("/getAllAppliances/{roomName}")
	public List<Appliance> getAllAppliances(@PathVariable String roomName,Principal principal)     // get rooms of that particular logged in user
	{
		
		return applianceService.getAllAppliances(roomName,principal);
	} 
	
	@DeleteMapping("/deleteAppliance/{roomName}/{applianceName}")// we can include devicename
	public ResponseEntity<?> deleteAppliance(@PathVariable String roomName,@PathVariable String applianceName,Principal principal)
	{
		boolean s=applianceService.deleteAppliance(roomName,applianceName,principal);
		if(s==true)
		{
			return new ResponseEntity<>("Appliance Deleted successfully", HttpStatus.OK);
		}
		else
			return new ResponseEntity<>("Appliance deletion failed...check ", HttpStatus.BAD_REQUEST);
	}
	
	
	@PostMapping("/applianceStatusChange/{applianceName}/{roomName}")
	public ResponseEntity<?> applianceStatusChange(@PathVariable String applianceName,@PathVariable String roomName,Principal principal)
	{
		 applianceService.changeApplianceStatus(applianceName, roomName, principal);
		String s=applianceService.applianceStatusChange(applianceName,roomName,principal);
		
	
		if(s.contains("unable"))
			return new ResponseEntity<>(s, HttpStatus.BAD_REQUEST);
		else
		return new ResponseEntity<>(s, HttpStatus.OK);
		
	}
	
	
//	@PostMapping("/applianceSchedule/{applianceName}/{roomName}")
//	public ResponseEntity<?> applianceSchedule(@PathVariable String applianceName,@PathVariable String roomName,Principal principal,@RequestBody ScheduleDto scheduleDto)
//	{
//		
//		boolean s=applianceService.applianceSchedule(applianceName,roomName,principal,scheduleDto);
//		if(s==true)
//		return new ResponseEntity<>("Appliance schedule success", HttpStatus.OK);
//		return new ResponseEntity<>("Appliance schedule failed ...please check entered data", HttpStatus.BAD_REQUEST);
//		
//	}
	// appliances unused list
		// appliances used list
	
	@GetMapping("/getOnAppliances")
	public List<Appliance> getOnAppliances(Principal principal)
	{
		return applianceRepository.findByApplianceStatusAndUserEmailId(true,principal.getName());
	}
		
	@GetMapping("/getOffAppliances")
	public List<Appliance> getoffAppliances(Principal principal)
	{
		return applianceRepository.findByApplianceStatusAndUserEmailId(false,principal.getName());
	}
		
	@GetMapping("/getAllApplianceDataStatLifeTime")
	public DataStats getTotalDataStats(Principal principal)
	{
		return applianceService.getTotalStats(principal);
	}
	
	@GetMapping("/getAllApplianceDataStatForThisMonth")
	public DataStats getDataStatForThisMonth(Principal principal)
	{
		return applianceService.getDataStatForThisMonth(principal);
	}
		
	@GetMapping("/getAllApplianceDataStatFromLastSevenDays")
	public DataStats getSevenDaysStat(Principal principal)
	{
		return applianceService.getSevenDaysStat(principal);
	}	
	
	
	@GetMapping("/getEachRoomDataPerDay")
	public List<RoomData> getEachRoomData(Principal principal )
	{
		return applianceService.getEachRoomDataPerDay(principal);
	}
	
	
	
	@GetMapping("/getEachRoomDataFromLastSevenDays")
	public List<RoomData> getSevenDaysRoomData(Principal principal )
	{
		return applianceService.getSevenDaysRoomData(principal);
	}
	
	
	
	
	@GetMapping("/getMonthlyStatsDiff")
	public StatsDiff getStatsDiff(Principal principal)
	{
		return applianceService.getStatsDiff(principal);
	}
	
	@GetMapping("/getEachRoomData/{statFor}")
	public List<RoomData> getRoomData(Principal principal,@PathVariable String statFor)
	{
		if(statFor.equals("Today"))
		{
			return applianceService.getEachRoomDataPerDay(principal);	
		}
		else if(statFor.equals("Last Seven Days"))
		{
			return applianceService.getSevenDaysRoomData(principal);
		}
		else 
		{
			return null;
		}
	}
	
	@GetMapping("/getAllApplianceDataStat/{statFor}")
	public DataStats getSevenDaysStat(Principal principal,@PathVariable String statFor)
	{
		if(statFor.equals("Last Seven Days"))
		{
			return applianceService.getSevenDaysStat(principal);
		}
		else if(statFor.equals("This Month"))
		{
			return applianceService.getDataStatForThisMonth(principal);
		}
		else if(statFor.equals("Life Time"))
		{
			return applianceService.getTotalStats(principal);
		}
		else
		{
			return null;
		}
	}
	
	@GetMapping("/getRoomData/{roomName}/{statFor}")
	public RoomData getRoomData(Principal principal,@PathVariable String roomName,@PathVariable String statFor)
	{
		if(statFor.equals("Today"))
		{
			List<RoomData> d= applianceService.getEachRoomDataPerDay(principal);	
			for( RoomData i:d)
			{
				if(i.getRoomName().equals(roomName))
				{
					return i;
				}
			}
		}
		else if(statFor.equals("Last Seven Days"))
		{
			List<RoomData> d=  applianceService.getSevenDaysRoomData(principal);
			for( RoomData i:d)
			{
				if(i.getRoomName().equals(roomName))
				{
					return i;
				}
			}
		}
		else if(statFor.equals("This Month"))
		{
			List<RoomData> d=  applianceService.getThisMonthRoomData(principal);
			for( RoomData i:d)
			{
				if(i.getRoomName().equals(roomName))
				{
					return i;
				}
			}
		}
		else 
		{
			return null;
		}
		return null;
	}
	
	
	
	 @GetMapping("getDetails1/{allDevicesUsageReq}/{applianceName}/{deviceId}/{duration}/{allValues}")
	    public Data getDetailsAgain1(@PathVariable boolean allDevicesUsageReq, @PathVariable String applianceName,
	                                 @PathVariable String deviceId, @PathVariable String duration,
	                                 @PathVariable boolean allValues, Principal principal) {
	        String user = principal.getName();
	        User user1 = userRepository.findByEmailId(user).orElseThrow(() -> new UsernameNotFoundException("Email not found"));
	        LocalDateTime todayTime = LocalDateTime.now();
	        if (allDevicesUsageReq == true) {

	        	
//	            List<Device> devices=deviceController.devicesInUse1();
//	            List<DeviceDataOfDay> deviceDataOfDays=new ArrayList<>();
//	            for(int i=0;i<devices.size();i++){
//	                List<DeviceData> deviceData1=deviceDataRepository.findByDeviceId(devices.get(i).getDeviceId());
//	                DeviceDataOfDay deviceDataOfDay=new DeviceDataOfDay();
//	                if(deviceData1.size()==0){
	//
//	                    deviceDataOfDay.setLabel(deviceId);
//	                    deviceDataOfDay.setConsumption(0);
//	                    deviceDataOfDay.setPrice(0);
//	                    deviceDataOfDay.setCo2Emission(0);
//	                }
//	                else{
//	                    int price1=0;
//	                    int consum1=0;
//	                    int co21=0;
//	                    double price=price1;
//	                    double consum=consum1;
//	                    double co2=co21;
//	                    deviceDataOfDay.setLabel(deviceId);
//	                    for(int j=0;j<deviceData1.size();j++){
//	                        price=price+deviceData1.get(j).getPrice();
//	                        consum=consum+deviceData1.get(i).getConsumption();
//	                        co2=co2+deviceData1.get(i).getCo2Emission();
//	                    }
//	                    deviceDataOfDay.setConsumption(consum);
//	                    deviceDataOfDay.setPrice(price);
//	                    deviceDataOfDay.setCo2Emission(co2);
//	                }
//	                deviceDataOfDays.add(deviceDataOfDay);
//	            }
//	            double s=0;
//	            double con=0;
//	            double c=0;
//	            for(int z=0;z<deviceDataOfDays.size();z++){
//	                s=s+deviceDataOfDays.get(z).getPrice();
//	                con=con+deviceDataOfDays.get(z).getConsumption();
//	                c=c+deviceDataOfDays.get(z).getCo2Emission();
//	            }
//	            Data data=new Data();
//	            data.setDeviceDataOfDayList(deviceDataOfDays);
//	            data.setSumConsumption(con);
//	            data.setSumco2Emission(c);
//	            data.setSumPrice(s);
//	            return data;

	        }
	        else {
	            List<DeviceData1> deviceData1 = deviceData1Repository.findByDeviceIdAndApplianceNameAndUser(deviceId, applianceName, user1);
	            if (deviceData1.size() == 0) {
	                return null;
	            }
	            String x = "TODAY";
	            String y = "LAST_7_DAYS";
	            String z = "LAST_MONTH";
	            String w = "THIS_YEAR";
	            if (duration.equals(x)) {
	                int today = todayTime.getDayOfMonth();
	                int year=todayTime.getYear();
	                int month=todayTime.getMonthValue();
	                List<DeviceData1> deviceDataToday1 = deviceData1Repository.findByDeviceIdAndApplianceNameAndUserAndDayOfMonthAndYearAndMonthOfYear(deviceId, applianceName, user1, today,year,month);
	                int len = deviceDataToday1.size();
	                double sumPrice1 = 0;
	                double sumco2Emission1 = 0;
	                double sumConsumption1 = 0;
	                double sumPrice = sumPrice1;
	                double sumco2Emission = sumco2Emission1;
	                double sumConsumption = sumConsumption1;
	                for (int i = 0; i < len; i++) {
	                    sumPrice = sumPrice + deviceDataToday1.get(i).getPrice();
	                    sumco2Emission = sumco2Emission + deviceDataToday1.get(i).getCo2Emission();
	                    sumConsumption = sumConsumption + deviceDataToday1.get(i).getConsumption();
	                }
	                if (allValues == true) {
	                    List<DeviceDataOfDay> deviceDataOfDays1 = new ArrayList<>();
	                    for(int i=0;i<24;i++)
	                    {
	                        List<DeviceData1> deviceDataToday = deviceData1Repository.findByDeviceIdAndApplianceNameAndUserAndDayOfMonthAndYearAndMonthOfYearAndHour(deviceId, applianceName, user1, today,year,month,i);
	                        
	                        if(deviceDataToday!=null && deviceDataToday.size()==1){
	                            DeviceDataOfDay deviceDataOfDays = new DeviceDataOfDay();
	                            deviceDataOfDays.setPrice((double)Math.round(deviceDataToday.get(0).getPrice() * 100d) / 100d);
	                            deviceDataOfDays.setCo2Emission((double)Math.round(deviceDataToday.get(0).getCo2Emission() * 100d) / 100d);
	                            deviceDataOfDays.setConsumption((double)Math.round(deviceDataToday.get(0).getConsumption() * 100d) / 100d);
	                            String m= String.valueOf(deviceDataToday.get(0).getHour());
	                            deviceDataOfDays.setLabel(m);
	                            deviceDataOfDays1.add(deviceDataOfDays);
	                            //
	                        }
	                        else if(deviceDataToday!=null && deviceDataToday.size()>1)
	                        {
	                        	double p=0;
	                        	double co=0;
	                        	double c=0;
	                        	for(int ii=0;ii<deviceDataToday.size();ii++)
	                        	{
	                        		p+=deviceDataToday.get(ii).getPrice();
	                        		co+=deviceDataToday.get(ii).getCo2Emission();
	                        		c+=deviceDataToday.get(ii).getConsumption();
	                        	}
	                        	DeviceDataOfDay deviceDataOfDays = new DeviceDataOfDay();
	                            deviceDataOfDays.setPrice((double)Math.round(p * 100d) / 100d);
	                            deviceDataOfDays.setCo2Emission((double)Math.round(co * 100d) / 100d);
	                            deviceDataOfDays.setConsumption((double)Math.round(c * 100d) / 100d);
	                            String m= String.valueOf(deviceDataToday.get(deviceDataToday.size()-1).getHour());
	                            deviceDataOfDays.setLabel(m);
	                            deviceDataOfDays1.add(deviceDataOfDays);
	                        }
	                        else{
	                            DeviceDataOfDay deviceDataOfDays = new DeviceDataOfDay();
	                            
	                            deviceDataOfDays.setPrice(0);
	                            deviceDataOfDays.setCo2Emission(0);
	                            deviceDataOfDays.setConsumption(0);
	                            String m= String.valueOf(i);
	                            deviceDataOfDays.setLabel(m);
	                            deviceDataOfDays1.add(deviceDataOfDays);
	                        }


	                    }
	                    Data data = new Data();
	                    data.setDeviceDataOfDayList(deviceDataOfDays1);
	                    data.setSumConsumption((double)Math.round(sumConsumption * 100d) / 100d);
	                    data.setSumco2Emission((double)Math.round(sumco2Emission * 100d) / 100d);
	                    data.setSumPrice((double)Math.round(sumPrice * 100d) / 100d);
	                    return data;
	                }
	            }
	            else if (duration.equals(y)) {
	                int today = todayTime.getDayOfMonth();
	                int year=todayTime.getYear();
	                int month=todayTime.getMonthValue();
	                DayOfWeek week=todayTime.getDayOfWeek();
	                if (allValues == true) {
	                    List<DeviceDataOfDay> deviceDataOfWeeks = new ArrayList<>();
	                    for (int i = 6; i >=0; i--) {
	                        DeviceDataOfDay deviceDataOfWeek = new DeviceDataOfDay();
	                        int today1=today-i;
	                        int month1=month;
	                        if((today1)<=0)
	                        {   
	                        	today1=(31+(today1));
	                        	month1=month1-1;
	                        	}
	                        
	                        List<DeviceData1> deviceDataToday = deviceData1Repository.findByDeviceIdAndApplianceNameAndUserAndDayOfMonthAndYearAndMonthOfYear(deviceId, applianceName, user1, today1,year,month1);
	                        if (deviceDataToday.size() == 1) {
	                            DeviceData1 deviceData2 = deviceDataToday.get(deviceDataToday.size() - 1);
	                            DayOfWeek week1=week.plus(-(i));
	                            deviceDataOfWeek.setLabel(week1.name());
//	                            deviceDataOfWeek.setPrice(deviceData2.getPrice());
//	                            deviceDataOfWeek.setConsumption(deviceData2.getConsumption());
//	                            deviceDataOfWeek.setCo2Emission(deviceData2.getCo2Emission());
	                            
	                            deviceDataOfWeek.setPrice((double)Math.round(deviceData2.getPrice() * 100d) / 100d);
	                            deviceDataOfWeek.setCo2Emission((double)Math.round(deviceData2.getCo2Emission() * 100d) / 100d);
	                            deviceDataOfWeek.setConsumption((double)Math.round(deviceData2.getConsumption() * 100d) / 100d);
	                            
	                        } else if (deviceDataToday.size() == 0) {
	                            DayOfWeek week1=week.plus(-(i));
	                            deviceDataOfWeek.setLabel(week1.name());
	                            deviceDataOfWeek.setPrice(0);
	                            deviceDataOfWeek.setConsumption(0);
	                            deviceDataOfWeek.setCo2Emission(0);
	                        }
	                        else
	                        {
	                            double p1=0;
	                            double c021=0;
	                            double c1=0;
	                            for(int k=0;k<deviceDataToday.size();k++){
	                                p1=p1+deviceDataToday.get(k).getPrice();
	                                c021=c021+deviceDataToday.get(k).getCo2Emission();
	                                c1=c1+deviceDataToday.get(k).getConsumption();
	                            }
	                            DayOfWeek week1=week.plus(-(i));
	                            deviceDataOfWeek.setLabel(week1.name());
//	                            deviceDataOfWeek.setPrice(p1);
//	                            deviceDataOfWeek.setConsumption(c1);
//	                            deviceDataOfWeek.setCo2Emission(c021);
	                            
	                            deviceDataOfWeek.setPrice((double)Math.round(p1 * 100d) / 100d);
	                            deviceDataOfWeek.setCo2Emission((double)Math.round(c021 * 100d) / 100d);
	                            deviceDataOfWeek.setConsumption((double)Math.round(c1 * 100d) / 100d);
	                        }
	                        deviceDataOfWeeks.add(deviceDataOfWeek);
	                    }
	                        int len = deviceDataOfWeeks.size();
	                        double sumPrice1 = 0;
	                        double sumco2Emission1 = 0;
	                        double sumConsumption1 = 0;
	                        double sumPrice = sumPrice1;
	                        double sumco2Emission = sumco2Emission1;
	                        double sumConsumption = sumConsumption1;
	                        for (int j = 0; j < len; j++) {
	                            sumPrice = sumPrice + deviceDataOfWeeks.get(j).getPrice();
	                            sumco2Emission = sumco2Emission + deviceDataOfWeeks.get(j).getCo2Emission();
	                            sumConsumption = sumConsumption + deviceDataOfWeeks.get(j).getConsumption();
	                        }
	                        Data data=new Data();
	                        data.setDeviceDataOfDayList(deviceDataOfWeeks);
	                       // data.setSumPrice(sumPrice);
	                        //data.setSumConsumption(sumConsumption);
	                        //data.setSumco2Emission(sumco2Emission);
	                        data.setSumConsumption((double)Math.round(sumConsumption * 100d) / 100d);
		                    data.setSumco2Emission((double)Math.round(sumco2Emission * 100d) / 100d);
		                    data.setSumPrice((double)Math.round(sumPrice * 100d) / 100d);
	                        return data;
	                }
	            }
	            else if (duration.equals(z)) {
	                int month = todayTime.getMonthValue()-1;
	                if (allValues == true) {       
	                	List<DeviceDataOfDay> deviceDataOfWeeks = new ArrayList<>();       
	                	List<DeviceData1> deviceDataToday = deviceData1Repository.findByDeviceIdAndApplianceNameAndUserAndMonthOfYear(deviceId, applianceName, user1, month);    
	                	for(int j=1;j<=31;j++){          
	                		List<DeviceData1> deviceDataList=deviceData1Repository.findByDeviceIdAndApplianceNameAndUserAndMonthOfYearAndDayOfMonth(deviceId,applianceName,user1,month,j);  
	                		
	                	if(deviceDataList.size()==0){    
	                		DeviceDataOfDay deviceDataOfDays = new DeviceDataOfDay();    
	                		deviceDataOfDays.setPrice(0);                 
	                		deviceDataOfDays.setCo2Emission(0);              
	                		deviceDataOfDays.setConsumption(0);              
	                		Month m=todayTime.getMonth().minus(1);           
	                		deviceDataOfDays.setLabel(m.name()+" "+j);
	                		deviceDataOfWeeks.add(deviceDataOfDays);
	                		}
	                	else if (deviceDataList.size()==1) {                            
	                		DeviceDataOfDay deviceDataOfDays = new DeviceDataOfDay();        
	                		//deviceDataOfDays.setPrice(deviceDataList.get(0).getPrice());    
	                		//deviceDataOfDays.setCo2Emission(deviceDataList.get(0).getCo2Emission());       
	                	//	deviceDataOfDays.setConsumption(deviceDataList.get(0).getConsumption()); 
	                		   deviceDataOfDays.setPrice((double)Math.round(deviceDataToday.get(0).getPrice() * 100d) / 100d);
	                            deviceDataOfDays.setCo2Emission((double)Math.round(deviceDataToday.get(0).getCo2Emission() * 100d) / 100d);
	                            deviceDataOfDays.setConsumption((double)Math.round(deviceDataToday.get(0).getConsumption() * 100d) / 100d);
	                		
	                		 
	                		
	                		Month m=deviceDataList.get(0).getMonth();                    
	                		deviceDataOfDays.setLabel(m.name()+" "+j);             
	                		deviceDataOfWeeks.add(deviceDataOfDays);              
	                		}               
	                	else{              
	                		double p=0;        
	                		double co=0;          
	                		double c=0;
	                		for(int i=0;i<deviceDataList.size();i++)
	                		{
	                			p+=deviceDataList.get(i).getPrice();
	                			co+=deviceDataList.get(i).getCo2Emission();
	                			c+=deviceDataList.get(i).getConsumption();
	                			}                
	                		DeviceDataOfDay deviceDataOfDays = new DeviceDataOfDay();  
	                		
	                		  deviceDataOfDays.setPrice((double)Math.round(p * 100d) / 100d);
	                            deviceDataOfDays.setCo2Emission((double)Math.round(co * 100d) / 100d);
	                            deviceDataOfDays.setConsumption((double)Math.round(c * 100d) / 100d);
	                		
	                		//deviceDataOfDays.setPrice(p);                 
	                		//deviceDataOfDays.setCo2Emission(co);         
//	                		deviceDataOfDays.setConsumption(c);  
	                		
	                		
	                		
	                		Month m=deviceDataList.get(deviceDataList.size()-1).getMonth();     
	                		deviceDataOfDays.setLabel(m.name()+" "+j);             
	                		deviceDataOfWeeks.add(deviceDataOfDays);            
	                		}                 
	                	}//                    for (int i = 0; i < deviceDataToday.size(); i++) {//                        DeviceDataOfDay deviceDataOfDays = new DeviceDataOfDay();//                        deviceDataOfDays.setPrice(deviceDataToday.get(i).getPrice());//                        deviceDataOfDays.setCo2Emission(deviceDataToday.get(i).getCo2Emission());//                        deviceDataOfDays.setConsumption(deviceDataToday.get(i).getConsumption());//                        Month m=deviceDataToday.get(i).getMonth();//                        deviceDataOfDays.setLabel(m.name()+" "+deviceDataToday.get(i).getDayOfMonth());//                        deviceDataOfWeeks.add(deviceDataOfDays);//                    }           
	                	int len = deviceDataOfWeeks.size();       
	                	double sumPrice1 = 0;              
	                	double sumco2Emission1 = 0;        
	                	double sumConsumption1 = 0;           
	                	double sumPrice = sumPrice1;         
	                	double sumco2Emission = sumco2Emission1;    
	                	
	                	double sumConsumption = sumConsumption1;       
	                	for (int j = 0; j < len; j++) {       
	                		sumPrice = sumPrice + deviceDataOfWeeks.get(j).getPrice();       
	                		sumco2Emission = sumco2Emission + deviceDataOfWeeks.get(j).getCo2Emission();   
	                		sumConsumption = sumConsumption + deviceDataOfWeeks.get(j).getConsumption(); 
	                		}                 
	                	Data data=new Data();       
	                	data.setDeviceDataOfDayList(deviceDataOfWeeks);   
//	                	data.setSumPrice(sumPrice);           
//	                	data.setSumConsumption(sumConsumption);    
//	                	data.setSumco2Emission(sumco2Emission); 
	                	 data.setSumConsumption((double)Math.round(sumConsumption * 100d) / 100d);
		                    data.setSumco2Emission((double)Math.round(sumco2Emission * 100d) / 100d);
		                    data.setSumPrice((double)Math.round(sumPrice * 100d) / 100d);
	                	return data;               
	                	}
	                
	            }
	            else if (duration.equals(w)) {
	                int year = todayTime.getYear();//-1
	                if (allValues == true) {
	                    List<DeviceDataOfDay> deviceDataOfWeeks = new ArrayList<>();
	                    for (int i = 0; i < 12; i++) {
	                        DeviceDataOfDay deviceDataOfWeek = new DeviceDataOfDay();
	                        List<DeviceData1> deviceDataToday = deviceData1Repository.findByDeviceIdAndApplianceNameAndUserAndMonthOfYearAndYear(deviceId, applianceName, user1, i+1,year);
	                        if(deviceDataToday.size()==0){
	                            Month month=Month.JANUARY.plus(i);
	                            deviceDataOfWeek.setLabel(month.name());
	                            deviceDataOfWeek.setPrice(0);
	                            deviceDataOfWeek.setConsumption(0);
	                            deviceDataOfWeek.setCo2Emission(0);
	                        }
	                        else {
	                            double price=0;
	                            double consum=0;
	                            double co2=0;
	                            for(int j=0;j<deviceDataToday.size();j++){
	                                price=price+deviceDataToday.get(j).getPrice();
	                                consum=consum+deviceDataToday.get(j).getConsumption();
	                                co2=co2+deviceDataToday.get(j).getCo2Emission();
	                            }
	                            Month month=Month.JANUARY.plus(i);
	                            deviceDataOfWeek.setLabel(month.name());
//	                            deviceDataOfWeek.setPrice(price);
//	                            deviceDataOfWeek.setConsumption(consum);
//	                            deviceDataOfWeek.setCo2Emission(co2);
	                            
	                            deviceDataOfWeek.setPrice((double)Math.round(price * 100d) / 100d);
	                            deviceDataOfWeek.setCo2Emission((double)Math.round(consum * 100d) / 100d);
	                            deviceDataOfWeek.setConsumption((double)Math.round(co2 * 100d) / 100d);
	                        }
	                        deviceDataOfWeeks.add(deviceDataOfWeek);
	                    }
	                    int len = deviceDataOfWeeks.size();
	                    double sumPrice1 = 0;
	                    double sumco2Emission1 = 0;
	                    double sumConsumption1 = 0;
	                    double sumPrice = sumPrice1;
	                    double sumco2Emission = sumco2Emission1;
	                    double sumConsumption = sumConsumption1;
	                    for (int j = 0; j < len; j++) {
	                        sumPrice = sumPrice + deviceDataOfWeeks.get(j).getPrice();
	                        sumco2Emission = sumco2Emission + deviceDataOfWeeks.get(j).getCo2Emission();
	                        sumConsumption = sumConsumption + deviceDataOfWeeks.get(j).getConsumption();
	                    }
	                    Data data=new Data();
	                    data.setDeviceDataOfDayList(deviceDataOfWeeks);
//	                    data.setSumPrice((double)Math.round(sumPrice * 1000d) / 1000d);
//	                    data.setSumConsumption(sumConsumption);//(double)Math.round(sumco2Emission * 1000d) / 1000d
//	                    data.setSumco2Emission((double)Math.round(sumco2Emission * 1000d) / 1000d);
	                    
	                    data.setSumConsumption((double)Math.round(sumConsumption * 100d) / 100d);
	                    data.setSumco2Emission((double)Math.round(sumco2Emission * 100d) / 100d);
	                    data.setSumPrice((double)Math.round(sumPrice * 100d) / 100d);
	                    return data;
	                }
	            }
	        }
	        return null;
	    }
	
	 @GetMapping("getDetails1/{allDevicesUsageReq}/{applianceName}/{deviceId}/{duration}/{consumptionReq}/{costReq}/{co2Req}")
	    public Data1 getDetailsAgain2(@PathVariable boolean allDevicesUsageReq, @PathVariable String applianceName,
	                                  @PathVariable String deviceId, @PathVariable String duration,
	                                  Principal principal, @PathVariable boolean consumptionReq, @PathVariable boolean costReq, @PathVariable boolean co2Req) 
	 {
	        String user = principal.getName();
	        User user1 = userRepository.findByEmailId(user).orElseThrow(() -> new UsernameNotFoundException("Email not found"));
	        LocalDateTime todayTime = LocalDateTime.now();
	        int month1= todayTime.getMonthValue();
	        int year1=todayTime.getYear();
	        if (allDevicesUsageReq == true) {
                  return null;

	        }
	        else 
	        {
	            //int year2=todayTime.getYear();
	            //int month2=todayTime.getMonthValue();
	            List<DeviceData1> deviceData1 = deviceData1Repository.findByDeviceIdAndApplianceNameAndUser(deviceId, applianceName, user1);
	            if (deviceData1.size() == 0)
	            {
	                return null;
	            }
	            String x = "TODAY";
	            String y = "LAST_7_DAYS";
	            String z = "THIS_MONTH";
	            String w = "LAST_YEAR";
	            if (duration.equals(x))
	            {
	                int today = todayTime.getDayOfMonth();
	                List<DeviceData1> deviceDataToday1 = deviceData1Repository.findByDeviceIdAndApplianceNameAndUserAndDayOfMonthAndYearAndMonthOfYear(deviceId, applianceName, user1, today,year1,month1);
	                if (deviceDataToday1.size() == 0)
	                {
	                    return null;
	                }
	                int len = deviceDataToday1.size();
	                double sumPrice1 = 0;
	                double sumco2Emission1 = 0;
	                double sumConsumption1 = 0;
	                double sumPrice = sumPrice1;
	                double sumco2Emission = sumco2Emission1;
	                double sumConsumption = sumConsumption1;
	                for (int i = 0; i < len; i++) {
	                    sumPrice = sumPrice + deviceDataToday1.get(i).getPrice();
	                    sumco2Emission = sumco2Emission + deviceDataToday1.get(i).getCo2Emission();
	                    sumConsumption = sumConsumption + deviceDataToday1.get(i).getConsumption();
	                }
	                if (costReq == true && consumptionReq == false && co2Req == false) {
	                    List<DeviceDataOfDay1> deviceDataOfDays1 = new ArrayList<>();
	                    for (int i = 0; i < 24; i++) {
	                        List<DeviceData1> deviceDataToday = deviceData1Repository.findByDeviceIdAndApplianceNameAndUserAndDayOfMonthAndYearAndMonthOfYearAndHour(deviceId, applianceName, user1, today, year1, month1, i);
	                        if (deviceDataToday != null && deviceDataToday.size()==1) {
	                            DeviceDataOfDay1 deviceDataOfDays = new DeviceDataOfDay1();
	                            deviceDataOfDays.setValue(deviceDataToday.get(0).getPrice());
	                            String m = String.valueOf(deviceDataToday.get(0).getHour());
	                            deviceDataOfDays.setLabel(m);
	                            deviceDataOfDays1.add(deviceDataOfDays);
	                        } 
	                        else if(deviceDataToday!=null && deviceDataToday.size()>1)
	                        {
	                        	double p=0;
	                        	double co=0;
	                        	double c=0;
	                        	for(int ii=0;ii<deviceDataToday.size();ii++)
	                        	{
	                        		p+=deviceDataToday.get(ii).getPrice();
	                        		co+=deviceDataToday.get(ii).getCo2Emission();
	                        		c+=deviceDataToday.get(ii).getConsumption();
	                        	}
	                        	DeviceDataOfDay1 deviceDataOfDays = new DeviceDataOfDay1();
	                            deviceDataOfDays.setValue(p);
	                         //   deviceDataOfDays.setCo2Emission(co);
	                           // deviceDataOfDays.setConsumption(c);
	                            String m= String.valueOf(deviceDataToday.get(deviceDataToday.size()-1).getHour());
	                            deviceDataOfDays.setLabel(m);
	                            deviceDataOfDays1.add(deviceDataOfDays);
	                        }else {
	                            DeviceDataOfDay1 deviceDataOfDays = new DeviceDataOfDay1();
	                            deviceDataOfDays.setValue(0);
	                            String m = String.valueOf(i);
	                            deviceDataOfDays.setLabel(m);
	                            deviceDataOfDays1.add(deviceDataOfDays);
	                        }
	                    }
	                        Data1 data = new Data1();
	                        data.setDeviceDataOfDayList(deviceDataOfDays1);
	                        data.setSumValue((double)Math.round(sumPrice * 1000d) / 1000d);
	                        return data;

	                }
	                 if (costReq == false && consumptionReq == true && co2Req == false) 
	                {
	                    List<DeviceDataOfDay1> deviceDataOfDays1 = new ArrayList<>();
	                    for(int i=0;i<24;i++) {
	                        List<DeviceData1> deviceDataToday = deviceData1Repository.findByDeviceIdAndApplianceNameAndUserAndDayOfMonthAndYearAndMonthOfYearAndHour(deviceId, applianceName, user1, today, year1, month1, i);
	                        if (deviceDataToday != null && deviceDataToday.size()==1 ) {
	                            DeviceDataOfDay1 deviceDataOfDays = new DeviceDataOfDay1();
	                            deviceDataOfDays.setValue(deviceDataToday.get(0).getConsumption());
	                            String m = String.valueOf(deviceDataToday.get(0).getHour());
	                            deviceDataOfDays.setLabel(m);
	                            deviceDataOfDays1.add(deviceDataOfDays);
	                        }
	                        else if(deviceDataToday!=null && deviceDataToday.size()>1)
	                        {
	                        	double p=0;
	                        	double co=0;
	                        	double c=0;
	                        	for(int ii=0;ii<deviceDataToday.size();ii++)
	                        	{
	                        		p+=deviceDataToday.get(ii).getPrice();
	                        		co+=deviceDataToday.get(ii).getCo2Emission();
	                        		c+=deviceDataToday.get(ii).getConsumption();
	                        	}
	                        	DeviceDataOfDay1 deviceDataOfDays = new DeviceDataOfDay1();
	                            deviceDataOfDays.setValue(c);
	                         //   deviceDataOfDays.setCo2Emission(co);
	                           // deviceDataOfDays.setConsumption(c);
	                            String m= String.valueOf(deviceDataToday.get(deviceDataToday.size()-1).getHour());
	                            deviceDataOfDays.setLabel(m);
	                            deviceDataOfDays1.add(deviceDataOfDays);
	                        }
	                        else {
	                            DeviceDataOfDay1 deviceDataOfDays = new DeviceDataOfDay1();
	                            deviceDataOfDays.setValue(0);
	                            String m = String.valueOf(i);
	                            deviceDataOfDays.setLabel(m);
	                            deviceDataOfDays1.add(deviceDataOfDays);
	                        }
	                    }
	                        Data1 data = new Data1();
	                        data.setDeviceDataOfDayList(deviceDataOfDays1);
	                        data.setSumValue((double)Math.round(sumConsumption * 1000d) / 1000d);
	                        return data;

	                }
	                 if (costReq == false && consumptionReq == false && co2Req == true) {
	                    List<DeviceDataOfDay1> deviceDataOfDays1 = new ArrayList<>();
	                    for(int i=0;i<24;i++) {
	                        List<DeviceData1> deviceDataToday = deviceData1Repository.findByDeviceIdAndApplianceNameAndUserAndDayOfMonthAndYearAndMonthOfYearAndHour(deviceId, applianceName, user1, today, year1, month1, i);
	                        if (deviceDataToday != null && deviceDataToday.size()==1) {
	                            DeviceDataOfDay1 deviceDataOfDays = new DeviceDataOfDay1();
	                            deviceDataOfDays.setValue(deviceDataToday.get(0).getCo2Emission());
	                            String m = String.valueOf(deviceDataToday.get(0).getHour());
	                            deviceDataOfDays.setLabel(m);
	                            deviceDataOfDays1.add(deviceDataOfDays);
	                        }
	                        else if(deviceDataToday!=null && deviceDataToday.size()>1)
	                        {
	                        	double p=0;
	                        	double co=0;
	                        	double c=0;
	                        	for(int ii=0;ii<deviceDataToday.size();ii++)
	                        	{
	                        		p+=deviceDataToday.get(ii).getPrice();
	                        		co+=deviceDataToday.get(ii).getCo2Emission();
	                        		c+=deviceDataToday.get(ii).getConsumption();
	                        	}
	                        	DeviceDataOfDay1 deviceDataOfDays = new DeviceDataOfDay1();
	                            deviceDataOfDays.setValue(co);
	                         //   deviceDataOfDays.setCo2Emission(co);
	                           // deviceDataOfDays.setConsumption(c);
	                            String m= String.valueOf(deviceDataToday.get(deviceDataToday.size()-1).getHour());
	                            deviceDataOfDays.setLabel(m);
	                            deviceDataOfDays1.add(deviceDataOfDays);
	                        }
	                        else {
	                            DeviceDataOfDay1 deviceDataOfDays = new DeviceDataOfDay1();
	                            deviceDataOfDays.setValue(0);
	                            String m = String.valueOf(i);
	                            deviceDataOfDays.setLabel(m);
	                            deviceDataOfDays1.add(deviceDataOfDays);
	                        }
	                    }
	                    Data1 data = new Data1();
	                    data.setDeviceDataOfDayList(deviceDataOfDays1);
	                    data.setSumValue((double)Math.round(sumco2Emission * 1000d) / 1000d);//
	                    return data;
	                }
	            }
	            else if (duration.equals(y)) {
	                DayOfWeek week=todayTime.getDayOfWeek();
	                int today = todayTime.getDayOfMonth();
	                int month=todayTime.getMonthValue();
	                if (costReq == false && consumptionReq == true && co2Req == false)  {
	                    List<DeviceDataOfDay1> deviceDataOfWeeks = new ArrayList<>();
	                    for (int i = 6; i >= 0; i--) {
	                        DeviceDataOfDay1 deviceDataOfWeek = new DeviceDataOfDay1();
	                        int today1=today-i;
	                        int month2=month;
	                        if((today1)<=0)
	                        {   
	                        	today1=(31+(today1));
	                        	month2=month2-1;
	                        	}
	                        List<DeviceData1> deviceDataToday = deviceData1Repository.findByDeviceIdAndApplianceNameAndUserAndDayOfMonthAndYearAndMonthOfYear(deviceId, applianceName, user1, today1,year1,month2);
	                            if (deviceDataToday.size() == 1) {
	                                DeviceData1 deviceData2 = deviceDataToday.get(deviceDataToday.size() - 1);
	                                DayOfWeek week1=week.plus(-(i));
	                                deviceDataOfWeek.setLabel(week1.name());
	                                deviceDataOfWeek.setValue(deviceData2.getConsumption());
	                            } else if (deviceDataToday.size() == 0) {
	                                DayOfWeek week1=week.plus(-(i));
	                                deviceDataOfWeek.setLabel(week1.name());
	                                deviceDataOfWeek.setValue(0);
	                            }





	                            else
	                            {
	                                double p1=0;
	                                double c021=0;
	                                double c1=0;
	                                for(int k=0;k<deviceDataToday.size();k++){
	                                    p1=p1+deviceDataToday.get(k).getPrice();
	                                    c021=c021+deviceDataToday.get(k).getCo2Emission();
	                                    c1=c1+deviceDataToday.get(k).getConsumption();
	                                }
	                                DayOfWeek week1=week.plus(-(i));
	                                deviceDataOfWeek.setLabel(week1.name());
	                                deviceDataOfWeek.setValue(c1);
	                            }
	                        deviceDataOfWeeks.add(deviceDataOfWeek);
	                    }
	                    int len = deviceDataOfWeeks.size();
	                    double sumPrice1 = 0;
	                    double sumco2Emission1 = 0;
	                    double sumConsumption1 = 0;
	                    double sumPrice = sumPrice1;
	                    double sumco2Emission = sumco2Emission1;
	                    double sumConsumption = sumConsumption1;
	                    for (int j = 0; j < len; j++) {
	                        sumConsumption = sumConsumption + deviceDataOfWeeks.get(j).getValue();
	                    }
	                    Data1 data=new Data1();
	                    data.setDeviceDataOfDayList(deviceDataOfWeeks);
	                    data.setSumValue(sumConsumption);
	                    return data;
	                }
	                if (costReq == true && consumptionReq == false && co2Req == false)  {
	                    List<DeviceDataOfDay1> deviceDataOfWeeks = new ArrayList<>();
	                    for (int i = 6; i >=0; i--) {
	                        DeviceDataOfDay1 deviceDataOfWeek = new DeviceDataOfDay1();
	                        int today1=today-i;
	                        int month2=month;
	                        if((today1)<=0)
	                        {   
	                        	today1=(31+(today1));
	                        	month2=month2-1;
	                        	}
	                        List<DeviceData1> deviceDataToday = deviceData1Repository.findByDeviceIdAndApplianceNameAndUserAndDayOfMonthAndYearAndMonthOfYear(deviceId, applianceName, user1, today1,year1,month2);
	                        if (deviceDataToday.size() == 1) {
	                            DeviceData1 deviceData2 = deviceDataToday.get(deviceDataToday.size() - 1);
	                            DayOfWeek week1=week.plus(-(i));
	                            deviceDataOfWeek.setLabel(week1.name());
	                            deviceDataOfWeek.setValue(deviceData2.getPrice());
	                        } else if (deviceDataToday.size() == 0) {
	                            DayOfWeek week1=week.plus(-(i));
	                            deviceDataOfWeek.setLabel(week1.name());
	                            deviceDataOfWeek.setValue(0);
	                        }
	                        else
	                        {
	                            double p1=0;
	                            double c021=0;
	                            double c1=0;
	                            for(int k=0;k<deviceDataToday.size();k++){
	                                p1=p1+deviceDataToday.get(k).getPrice();
	                                c021=c021+deviceDataToday.get(k).getCo2Emission();
	                                c1=c1+deviceDataToday.get(k).getConsumption();
	                            }
	                            DayOfWeek week1=week.plus(-(i));
	                            deviceDataOfWeek.setLabel(week1.name());
	                            deviceDataOfWeek.setValue(p1);
	                        }
	                        deviceDataOfWeeks.add(deviceDataOfWeek);
	                    }
	                    int len = deviceDataOfWeeks.size();
	                    double sumPrice1 = 0;
	                    double sumco2Emission1 = 0;
	                    double sumConsumption1 = 0;
	                    double sumPrice = sumPrice1;
	                    double sumco2Emission = sumco2Emission1;
	                    double sumConsumption = sumConsumption1;
	                    for (int j = 0; j < len; j++) {
	                        sumPrice = sumPrice + deviceDataOfWeeks.get(j).getValue();
	                    }
	                    Data1 data=new Data1();
	                    data.setDeviceDataOfDayList(deviceDataOfWeeks);
	                    data.setSumValue(sumPrice);
	                    return data;
	                }
	                 if (costReq == false && consumptionReq == false && co2Req == true)  {
	                    List<DeviceDataOfDay1> deviceDataOfWeeks = new ArrayList<>();
	                    for (int i = 6; i >=0; i--) {
	                        DeviceDataOfDay1 deviceDataOfWeek = new DeviceDataOfDay1();
	                        int today1=today-i;
	                        int month2=month;
	                        if((today1)<=0)
	                        {   
	                        	today1=(31+(today1));
	                        	month2=month2-1;
	                        	}
	                        List<DeviceData1> deviceDataToday = deviceData1Repository.findByDeviceIdAndApplianceNameAndUserAndDayOfMonthAndYearAndMonthOfYear(deviceId, applianceName, user1, today1,year1,month2);
	                        if (deviceDataToday.size() == 1) {
	                            DeviceData1 deviceData2 = deviceDataToday.get(deviceDataToday.size() - 1);
	                            DayOfWeek week1=week.plus(-(i));
	                            deviceDataOfWeek.setLabel(week1.name());
	                            deviceDataOfWeek.setValue(deviceData2.getCo2Emission());
	                        } else if (deviceDataToday.size() == 0) {
	                            DayOfWeek week1=week.plus(-(i));
	                            deviceDataOfWeek.setLabel(week1.name());
	                            deviceDataOfWeek.setValue(0);
	                        }
	                        else
	                        {
	                            double p1=0;
	                            double c021=0;
	                            double c1=0;
	                            for(int k=0;k<deviceDataToday.size();k++){
	                                p1=p1+deviceDataToday.get(k).getPrice();
	                                c021=c021+deviceDataToday.get(k).getCo2Emission();
	                                c1=c1+deviceDataToday.get(k).getConsumption();
	                            }
	                            DayOfWeek week1=week.plus(-(i));
	                            deviceDataOfWeek.setLabel(week1.name());
	                            deviceDataOfWeek.setValue(c021);
	                        }
	                        deviceDataOfWeeks.add(deviceDataOfWeek);
	                    }
	                    int len = deviceDataOfWeeks.size();
	                    double sumPrice1 = 0;
	                    double sumco2Emission1 = 0;
	                    double sumConsumption1 = 0;
	                    double sumPrice = sumPrice1;
	                    double sumco2Emission = sumco2Emission1;
	                    double sumConsumption = sumConsumption1;
	                    for (int j = 0; j < len; j++) {
	                        sumco2Emission = sumco2Emission + deviceDataOfWeeks.get(j).getValue();
	                    }
	                    Data1 data=new Data1();
	                    data.setDeviceDataOfDayList(deviceDataOfWeeks);
	                    data.setSumValue(sumco2Emission);
	                    return data;
	                }
	            }
	            else if (duration.equals(z)) {
	                int month = todayTime.getMonthValue()-1;
	              
	                if (costReq == false && consumptionReq == true && co2Req == false) {
	                    List<DeviceDataOfDay1> deviceDataOfWeeks = new ArrayList<>();
	                    List<DeviceData1> deviceDataToday = deviceData1Repository.findByDeviceIdAndApplianceNameAndUserAndMonthOfYearAndYear(deviceId, applianceName, user1, month,year1);
	                	for(int j=1;j<=31;j++){          
	                		List<DeviceData1> deviceDataList=deviceData1Repository.findByDeviceIdAndApplianceNameAndUserAndMonthOfYearAndDayOfMonth(deviceId,applianceName,user1,month,j);  
	                		
	                	if(deviceDataList.size()==0){    
	                		DeviceDataOfDay1 deviceDataOfDays = new DeviceDataOfDay1();    
	                		//deviceDataOfDays.setPrice(0);                 
	                		//deviceDataOfDays.setCo2Emission(0);              
	                		deviceDataOfDays.setValue(0);              
	                		Month m=todayTime.getMonth().minus(1);           
	                		deviceDataOfDays.setLabel(m.name()+" "+j);
	                		deviceDataOfWeeks.add(deviceDataOfDays);
	                		}
	                	else if (deviceDataList.size()==1) {                            
	                		DeviceDataOfDay1 deviceDataOfDays = new DeviceDataOfDay1();        
	                		//deviceDataOfDays.setPrice(deviceDataList.get(0).getPrice());    
	                		//deviceDataOfDays.setCo2Emission(deviceDataList.get(0).getCo2Emission());       
	                		deviceDataOfDays.setValue(deviceDataList.get(0).getConsumption());     
	                		Month m=deviceDataList.get(0).getMonth();                    
	                		deviceDataOfDays.setLabel(m.name()+" "+j);             
	                		deviceDataOfWeeks.add(deviceDataOfDays);              
	                		}               
	                	else{              
	                		double p=0;        
	                		double co=0;          
	                		double c=0;
	                		for(int i=0;i<deviceDataList.size();i++)
	                		{
	                			p+=deviceDataList.get(i).getPrice();
	                			co+=deviceDataList.get(i).getCo2Emission();
	                			c+=deviceDataList.get(i).getConsumption();
	                			}                
	                		DeviceDataOfDay1 deviceDataOfDays = new DeviceDataOfDay1();       
	                		//deviceDataOfDays.setPrice(p);                 
	                		//deviceDataOfDays.setCo2Emission(co);         
	                		deviceDataOfDays.setValue(c);        
	                		Month m=deviceDataList.get(deviceDataList.size()-1).getMonth();     
	                		deviceDataOfDays.setLabel(m.name()+" "+j);             
	                		deviceDataOfWeeks.add(deviceDataOfDays);            
	                		}      
	                    int len = deviceDataOfWeeks.size();
	                    double sumPrice1 = 0;
	                    double sumco2Emission1 = 0;
	                    double sumConsumption1 = 0;
	                    double sumPrice = sumPrice1;
	                    double sumco2Emission = sumco2Emission1;
	                    double sumConsumption = sumConsumption1;
	                    for (int jj = 0; jj < len; jj++) {
	                        sumConsumption = sumConsumption + deviceDataOfWeeks.get(jj).getValue();
	                    }
	                    Data1 data=new Data1();
	                    data.setDeviceDataOfDayList(deviceDataOfWeeks);
	                    data.setSumValue(sumConsumption);
	                    return data;
	                }
	                }
	                	 if (costReq == true && consumptionReq == false && co2Req == false) {
	                    List<DeviceDataOfDay1> deviceDataOfWeeks = new ArrayList<>();
	                    List<DeviceData1> deviceDataToday = deviceData1Repository.findByDeviceIdAndApplianceNameAndUserAndMonthOfYearAndYear(deviceId, applianceName, user1, month,year1);
	                    for(int j=1;j<=31;j++){          
	                		List<DeviceData1> deviceDataList=deviceData1Repository.findByDeviceIdAndApplianceNameAndUserAndMonthOfYearAndDayOfMonth(deviceId,applianceName,user1,month,j);  
	                		
	                	if(deviceDataList.size()==0){    
	                		DeviceDataOfDay1 deviceDataOfDays = new DeviceDataOfDay1();    
	                		deviceDataOfDays.setValue(0)    ;             
	                		//deviceDataOfDays.setCo2Emission(0);              
	                		//deviceDataOfDays.setValue(0);              
	                		Month m=todayTime.getMonth().minus(1);           
	                		deviceDataOfDays.setLabel(m.name()+" "+j);
	                		deviceDataOfWeeks.add(deviceDataOfDays);
	                		}
	                	else if (deviceDataList.size()==1) {                            
	                		DeviceDataOfDay1 deviceDataOfDays = new DeviceDataOfDay1();        
	                		//deviceDataOfDays.setPrice(deviceDataList.get(0).getPrice());    
	                		//deviceDataOfDays.setCo2Emission(deviceDataList.get(0).getCo2Emission());       
	                		deviceDataOfDays.setValue(deviceDataList.get(0).getPrice());     
	                		Month m=deviceDataList.get(0).getMonth();                    
	                		deviceDataOfDays.setLabel(m.name()+" "+j);             
	                		deviceDataOfWeeks.add(deviceDataOfDays);              
	                		}               
	                	else{              
	                		double p=0;        
	                		double co=0;          
	                		double c=0;
	                		for(int i=0;i<deviceDataList.size();i++)
	                		{
	                			p+=deviceDataList.get(i).getPrice();
	                			co+=deviceDataList.get(i).getCo2Emission();
	                			c+=deviceDataList.get(i).getConsumption();
	                			}                
	                		DeviceDataOfDay1 deviceDataOfDays = new DeviceDataOfDay1();       
	                		//deviceDataOfDays.setPrice(p);                 
	                		//deviceDataOfDays.setCo2Emission(co);         
	                		deviceDataOfDays.setValue(p);        
	                		Month m=deviceDataList.get(deviceDataList.size()-1).getMonth();     
	                		deviceDataOfDays.setLabel(m.name()+" "+j);             
	                		deviceDataOfWeeks.add(deviceDataOfDays);            
	                		}    
	                    int len = deviceDataOfWeeks.size();
	                    double sumPrice1 = 0;
	                    double sumco2Emission1 = 0;
	                    double sumConsumption1 = 0;
	                    double sumPrice = sumPrice1;
	                    double sumco2Emission = sumco2Emission1;
	                    double sumConsumption = sumConsumption1;
	                    for (int jj = 0; jj < len; jj++) {
	                        sumPrice = sumPrice + deviceDataOfWeeks.get(jj).getValue();
	                    }
	                    Data1 data=new Data1();
	                    data.setDeviceDataOfDayList(deviceDataOfWeeks);
	                    data.setSumValue(sumPrice);
	                    return data;
	                }
	                	}
	                    if (costReq == false && consumptionReq == false && co2Req == true) {
	                    List<DeviceDataOfDay1> deviceDataOfWeeks = new ArrayList<>();
	                    List<DeviceData1> deviceDataToday = deviceData1Repository.findByDeviceIdAndApplianceNameAndUserAndMonthOfYearAndYear(deviceId, applianceName, user1, month,year1);
	                    for(int j=1;j<=31;j++){          
	                		List<DeviceData1> deviceDataList=deviceData1Repository.findByDeviceIdAndApplianceNameAndUserAndMonthOfYearAndDayOfMonth(deviceId,applianceName,user1,month,j);  
	                		
	                	if(deviceDataList.size()==0){    
	                		DeviceDataOfDay1 deviceDataOfDays = new DeviceDataOfDay1();    
	                		deviceDataOfDays.setValue(0)    ;             
	                		//deviceDataOfDays.setCo2Emission(0);              
	                		//deviceDataOfDays.setValue(0);              
	                		Month m=todayTime.getMonth().minus(1);           
	                		deviceDataOfDays.setLabel(m.name()+" "+j);
	                		deviceDataOfWeeks.add(deviceDataOfDays);
	                		}
	                	else if (deviceDataList.size()==1) {                            
	                		DeviceDataOfDay1 deviceDataOfDays = new DeviceDataOfDay1();        
	                		//deviceDataOfDays.setPrice(deviceDataList.get(0).getPrice());    
	                		//deviceDataOfDays.setCo2Emission(deviceDataList.get(0).getCo2Emission());       
	                		deviceDataOfDays.setValue(deviceDataList.get(0).getCo2Emission());     
	                		Month m=deviceDataList.get(0).getMonth();                    
	                		deviceDataOfDays.setLabel(m.name()+" "+j);             
	                		deviceDataOfWeeks.add(deviceDataOfDays);              
	                		}               
	                	else{              
	                		double p=0;        
	                		double co=0;          
	                		double c=0;
	                		for(int i=0;i<deviceDataList.size();i++)
	                		{
	                			p+=deviceDataList.get(i).getPrice();
	                			co+=deviceDataList.get(i).getCo2Emission();
	                			c+=deviceDataList.get(i).getConsumption();
	                			}                
	                		DeviceDataOfDay1 deviceDataOfDays = new DeviceDataOfDay1();       
	                		//deviceDataOfDays.setPrice(p);                 
	                		//deviceDataOfDays.setCo2Emission(co);         
	                		deviceDataOfDays.setValue(co);        
	                		Month m=deviceDataList.get(deviceDataList.size()-1).getMonth();     
	                		deviceDataOfDays.setLabel(m.name()+" "+j);             
	                		deviceDataOfWeeks.add(deviceDataOfDays);            
	                		} 
	                    int len = deviceDataOfWeeks.size();
	                    double sumPrice1 = 0;
	                    double sumco2Emission1 = 0;
	                    double sumConsumption1 = 0;
	                    double sumPrice = sumPrice1;
	                    double sumco2Emission = sumco2Emission1;
	                    double sumConsumption = sumConsumption1;
	                    for (int jj = 0; jj < len; jj++) {
	                        sumco2Emission = sumco2Emission + deviceDataOfWeeks.get(jj).getValue();
	                    }
	                    Data1 data=new Data1();
	                    data.setDeviceDataOfDayList(deviceDataOfWeeks);
	                    data.setSumValue(sumco2Emission);
	                    return data;
	                }
	            }
	            else if (duration.equals(w)) {
	                int year = todayTime.getYear();
	                if (costReq == false && consumptionReq == true && co2Req == false) {
	                    List<DeviceDataOfDay1> deviceDataOfWeeks = new ArrayList<>();
	                    for (int i = 0; i < 12; i++) {
	                        DeviceDataOfDay1 deviceDataOfWeek = new DeviceDataOfDay1();
	                        List<DeviceData1> deviceDataToday = deviceData1Repository.findByDeviceIdAndApplianceNameAndUserAndMonthOfYearAndYear(deviceId, applianceName, user1, i+1,year);
	                        if(deviceDataToday.size()==0){
	                            Month month11=Month.JANUARY.plus(i);
	                            deviceDataOfWeek.setLabel(month11.name());
	                            deviceDataOfWeek.setValue(0);
	                        }
	                        else {
	                            double price=0;
	                            double consum=0;
	                            double co2=0;
	                            for(int j=0;j<deviceDataToday.size();j++){
	                                price=price+deviceDataToday.get(j).getPrice();
	                                consum=consum+deviceDataToday.get(j).getConsumption();
	                                co2=co2+deviceDataToday.get(j).getCo2Emission();
	                            }
	                            Month month11=Month.JANUARY.plus(i);
	                            deviceDataOfWeek.setLabel(month11.name());
	                            deviceDataOfWeek.setValue(consum);
	                        }
	                        deviceDataOfWeeks.add(deviceDataOfWeek);
	                    }
	                    int len = deviceDataOfWeeks.size();
	                    double sumPrice1 = 0;
	                    double sumco2Emission1 = 0;
	                    double sumConsumption1 = 0;
	                    double sumPrice = sumPrice1;
	                    double sumco2Emission = sumco2Emission1;
	                    double sumConsumption = sumConsumption1;
	                    for (int j = 0; j < len; j++) {
	                        sumConsumption = sumConsumption + deviceDataOfWeeks.get(j).getValue();
	                    }
	                    Data1 data=new Data1();
	                    data.setDeviceDataOfDayList(deviceDataOfWeeks);
	                    data.setSumValue(sumConsumption);
	                    return data;
	                }
	                if (costReq == true && consumptionReq == false && co2Req == false) {
	                    List<DeviceDataOfDay1> deviceDataOfWeeks = new ArrayList<>();
	                    for (int i = 0; i < 12; i++) {
	                        DeviceDataOfDay1 deviceDataOfWeek = new DeviceDataOfDay1();
	                        List<DeviceData1> deviceDataToday = deviceData1Repository.findByDeviceIdAndApplianceNameAndUserAndMonthOfYearAndYear(deviceId, applianceName, user1, i+1,year);
	                        if(deviceDataToday.size()==0){
	                            Month month11=Month.JANUARY.plus(i);
	                            deviceDataOfWeek.setLabel(month11.name());
	                            deviceDataOfWeek.setValue(0);
	                        }
	                        else {
	                            double price=0;
	                            double consum=0;
	                            double co2=0;
	                            for(int j=0;j<deviceDataToday.size();j++){
	                                price=price+deviceDataToday.get(j).getPrice();
	                                consum=consum+deviceDataToday.get(j).getConsumption();
	                                co2=co2+deviceDataToday.get(j).getCo2Emission();
	                            }
	                            Month month11=Month.JANUARY.plus(i);
	                            deviceDataOfWeek.setLabel(month11.name());
	                            deviceDataOfWeek.setValue(price);
	                        }
	                        deviceDataOfWeeks.add(deviceDataOfWeek);
	                    }
	                    int len = deviceDataOfWeeks.size();
	                    double sumPrice1 = 0;
	                    double sumco2Emission1 = 0;
	                    double sumConsumption1 = 0;
	                    double sumPrice = sumPrice1;
	                    double sumco2Emission = sumco2Emission1;
	                    double sumConsumption = sumConsumption1;
	                    for (int j = 0; j < len; j++) {
	                        sumPrice = sumPrice + deviceDataOfWeeks.get(j).getValue();
	                    }
	                    Data1 data=new Data1();
	                    data.setDeviceDataOfDayList(deviceDataOfWeeks);
	                    data.setSumValue(sumPrice);
	                    return data;
	                }
	                if (costReq == false && consumptionReq == false && co2Req == true) {
	                    List<DeviceDataOfDay1> deviceDataOfWeeks = new ArrayList<>();
	                    for (int i = 0; i < 12; i++) {
	                        DeviceDataOfDay1 deviceDataOfWeek = new DeviceDataOfDay1();
	                        List<DeviceData1> deviceDataToday = deviceData1Repository.findByDeviceIdAndApplianceNameAndUserAndMonthOfYearAndYear(deviceId, applianceName, user1, i+1,year);
	                        if(deviceDataToday.size()==0){
	                            Month month11=Month.JANUARY.plus(i);
	                            deviceDataOfWeek.setLabel(month11.name());
	                            deviceDataOfWeek.setValue(0);
	                        }
	                        else {
	                            double price=0;
	                            double consum=0;
	                            double co2=0;
	                            for(int j=0;j<deviceDataToday.size();j++){
	                                price=price+deviceDataToday.get(j).getPrice();
	                                consum=consum+deviceDataToday.get(j).getConsumption();
	                                co2=co2+deviceDataToday.get(j).getCo2Emission();
	                            }
	                            Month month11=Month.JANUARY.plus(i);
	                            deviceDataOfWeek.setLabel(month11.name());
	                            deviceDataOfWeek.setValue(co2);
	                        }
	                        deviceDataOfWeeks.add(deviceDataOfWeek);
	                    }
	                    int len = deviceDataOfWeeks.size();
	                    double sumPrice1 = 0;
	                    double sumco2Emission1 = 0;
	                    double sumConsumption1 = 0;
	                    double sumPrice = sumPrice1;
	                    double sumco2Emission = sumco2Emission1;
	                    double sumConsumption = sumConsumption1;
	                    for (int j = 0; j < len; j++) {
	                        sumco2Emission = sumco2Emission + deviceDataOfWeeks.get(j).getValue();
	                    }
	                    Data1 data=new Data1();
	                    data.setDeviceDataOfDayList(deviceDataOfWeeks);
	                    data.setSumValue(sumco2Emission);
	                    return data;
	                }
	            }
	            
	            }
	        return null;
	    }
	 }


@GetMapping("getDetails1/{allDevicesUsageReq}")
public Data getDetailsAgain1(@PathVariable boolean allDevicesUsageReq, Principal principal) {
   // String user = principal.getName();
  //  User user1 = userRepository.findByEmailId(user).orElseThrow(() -> new UsernameNotFoundException("Email not found"));
   // LocalDateTime todayTime = LocalDateTime.now();
    List<Device> devices = deviceService.getActiveDevices();
    List<DeviceDataOfDay> deviceDataOfDays = new ArrayList<>();
    for (int i = 0; i < devices.size(); i++) {
        List<DeviceData1> deviceData1 = deviceData1Repository.findByDeviceId(devices.get(i).getDeviceId());
        DeviceDataOfDay deviceDataOfDay = new DeviceDataOfDay();
        if (deviceData1.size() == 0) {

            deviceDataOfDay.setLabel(devices.get(i).getDeviceId());
            deviceDataOfDay.setConsumption(0);
            deviceDataOfDay.setPrice(0);
            deviceDataOfDay.setCo2Emission(0);
        } else {
            int price1 = 0;
            int consum1 = 0;
            int co21 = 0;
            double price = price1;
            double consum = consum1;
            double co2 = co21;
            deviceDataOfDay.setLabel(devices.get(i).getDeviceId());
            for (int j = 0; j < deviceData1.size(); j++) {
                price = price + deviceData1.get(j).getPrice();
                consum = consum + deviceData1.get(j).getConsumption();
                co2 = co2 + deviceData1.get(j).getCo2Emission();
            }
            deviceDataOfDay.setConsumption(consum);
            deviceDataOfDay.setPrice(price);
            deviceDataOfDay.setCo2Emission(co2);
        }
        deviceDataOfDays.add(deviceDataOfDay);
    }
    double s = 0;
    double con = 0;
    double c = 0;
    for (int z = 0; z < deviceDataOfDays.size(); z++) {
        s = s + deviceDataOfDays.get(z).getPrice();
        con = con + deviceDataOfDays.get(z).getConsumption();
        c = c + deviceDataOfDays.get(z).getCo2Emission();
    }
    Data data = new Data();
    data.setDeviceDataOfDayList(deviceDataOfDays);
    data.setSumConsumption(con);
    data.setSumco2Emission(c);
    data.setSumPrice(s);
    return data;
}

	
	


}















//@PostMapping("/schedule")
//public String scheduleAppliance(@RequestBody ScheduleDto scheduleDto,Principal principal)
//{
//	return applianceService.scheduleAppliance(scheduleDto,principal);
//}