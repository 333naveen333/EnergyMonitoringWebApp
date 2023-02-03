package com.energyms.energyms.controller;

//import com.energymoni.energymoni.dto.DeviceDataDto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.energyms.energyms.dto.DeviceDataDto;
import com.energyms.energyms.model.Appliance;
import com.energyms.energyms.model.Device;
import com.energyms.energyms.model.DeviceData1;
import com.energyms.energyms.model.User;
import com.energyms.energyms.repository.ApplianceRepository;
import com.energyms.energyms.repository.DeviceData1Repository;
import com.energyms.energyms.repository.DeviceRepository;
import com.energyms.energyms.repository.UserRepository;

import java.security.Principal;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;

@RestController
@RequestMapping("/kafka")
public class JsonMessageController {


  //  private JsonKafkaProducer kafkaProducer;

    @Autowired
    private DeviceRepository deviceRepository;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ApplianceRepository applianceRepository;
    @Autowired
    private DeviceData1Repository deviceData1Repository;

   

    public ResponseEntity<?> publish(DeviceDataDto deviceDataDto, Principal principal) {
        deviceDataDto.setEmailId(principal.getName());
        User user = userRepository.findByEmailId(deviceDataDto.getEmailId()).orElseThrow(
                () -> new UsernameNotFoundException("Email not found")
        );
        if (user == null) {
            return new ResponseEntity<>("user doesnt exist", HttpStatus.NOT_FOUND);
        }
//        String s= deviceDataDto.getDeviceId();
//        String[] parts = s.split("-");
//        String s1=parts[1];
//        deviceDataDto.setDeviceId(s1);
        String s1 = deviceDataDto.getDeviceId();
        //List<DeviceData> deviceData2=deviceDataRepository.findByDeviceId(s1);
        double Consumption = 0;
        double priceValue=0;
        double co2EmissionValue=0;
        deviceDataDto.setTimestamp(LocalDateTime.now());
        deviceDataDto.setConsumption(Consumption);
        deviceDataDto.setPrice(priceValue);
        deviceDataDto.setCo2Emission(co2EmissionValue);
        deviceDataDto.setPricePerUnit(0.002);
        deviceDataDto.setCo2EmissionPerUnit(0.001);
        long time = LocalDateTime.now().getHour();
        Appliance appliance = applianceRepository.findByDeviceDeviceId(s1);
        Device device = deviceRepository.findByDeviceId(s1);
        deviceDataDto.setApplianceName(appliance.getApplianceName());
        deviceDataDto.setApplianceName(appliance.getApplianceName());
        if (device == null) {
            return new ResponseEntity<>("There is no existing device", HttpStatus.NOT_FOUND);
        }
//        if (appliance == null) {
//            return new ResponseEntity<>("There is no appliance with the device attached", HttpStatus.NOT_FOUND);
//        }

        List<DeviceData1> deviceData2 = deviceData1Repository.findByDeviceId(deviceDataDto.getDeviceId());

        deviceDataDto.setTimestamp(LocalDateTime.now());

        if (deviceData2.size() == 0) {
//            2023-01-21 15:09:14.839959----51+480+540+41==1112--18.5--15,16,17,18,19,20,21,22,23,24,22-1,2,3,4,5,6,7,8,9
//            2023-01-22 09:41:47.620841

            long totalMinutes = (deviceDataDto.getTimeInMinutes());//1112
            long totalHours = (totalMinutes) / 60;//18.5
            long totalDays = (totalMinutes) / 1440;
            if (deviceDataDto.getTimestamp().getYear() == deviceDataDto.getTimeWhenAppisOn().getYear() &&
                    deviceDataDto.getTimestamp().getMonth() == deviceDataDto.getTimeWhenAppisOn().getMonth() &&
                    deviceDataDto.getTimestamp().getDayOfMonth() == deviceDataDto.getTimeWhenAppisOn().getDayOfMonth() &&
                    deviceDataDto.getTimestamp().getHour() == deviceDataDto.getTimeWhenAppisOn().getHour()) {
                //if (totalHours <= 1) {
                DeviceData1 deviceData1 = new DeviceData1();
                deviceData1.setUser(user);
                deviceData1.setDeviceId(deviceDataDto.getDeviceId());
                deviceData1.setTimestamp(LocalDateTime.now());
                deviceData1.setEventValue(deviceDataDto.getEventValue());
                deviceData1.setApplianceName(deviceDataDto.getApplianceName());
                deviceData1.setConsumption((deviceDataDto.getEventValue() * totalMinutes)/1000);
                deviceData1.setPrice(deviceData1.getConsumption()*2);
                deviceData1.setCo2Emission(deviceData1.getConsumption()*0.8);
                deviceData1.setPricePerUnit(2);
                deviceData1.setCo2EmissionPerUnit(0.8);
                deviceData1.setTimeInMinutes(deviceDataDto.getTimeInMinutes());
                deviceData1.setTotalTimeInMinutes(deviceDataDto.getTimeInMinutes());
                deviceData1.setTimeWhenAppisOn(deviceDataDto.getTimeWhenAppisOn());
                deviceData1.setHour(deviceDataDto.getTimeWhenAppisOn().getHour());
                deviceData1.setDayOfMonth(deviceDataDto.getTimeWhenAppisOn().getDayOfMonth());
                deviceData1.setMonthOfYear(deviceDataDto.getTimeWhenAppisOn().getMonthValue());
                deviceData1.setYear(deviceDataDto.getTimeWhenAppisOn().getYear());
                deviceData1.setMonth(deviceDataDto.getTimeWhenAppisOn().getMonth());
                deviceData1.setDayOfWeek(deviceDataDto.getTimeWhenAppisOn().getDayOfWeek());
                deviceData1Repository.save(deviceData1);
            } else if (deviceDataDto.getTimestamp().getYear() == deviceDataDto.getTimeWhenAppisOn().getYear() &&
                    deviceDataDto.getTimestamp().getMonth() == deviceDataDto.getTimeWhenAppisOn().getMonth() &&
                    deviceDataDto.getTimestamp().getDayOfMonth() == deviceDataDto.getTimeWhenAppisOn().getDayOfMonth() &&
                    deviceDataDto.getTimestamp().getHour() != deviceDataDto.getTimeWhenAppisOn().getHour()) {
                //else if (totalHours<=24) {
                DeviceData1 deviceData21 = new DeviceData1();
                deviceData21.setUser(user);
                deviceData21.setDeviceId(deviceDataDto.getDeviceId());
                deviceData21.setTimestamp(LocalDateTime.now());
                deviceData21.setEventValue(deviceDataDto.getEventValue());
                deviceData21.setApplianceName(deviceDataDto.getApplianceName());
                deviceData21.setTimeWhenAppisOn(deviceDataDto.getTimeWhenAppisOn());
                deviceData21.setTimeInMinutes(60 - deviceDataDto.getTimeWhenAppisOn().getMinute());
                deviceData21.setConsumption((deviceDataDto.getEventValue() * (60 - deviceDataDto.getTimeWhenAppisOn().getMinute()))/1000);
                deviceData21.setPrice(deviceData21.getConsumption() * 2);
                deviceData21.setCo2Emission(deviceData21.getConsumption() * 0.8);
                deviceData21.setPricePerUnit(2);
                deviceData21.setCo2EmissionPerUnit(0.8);
                deviceData21.setTotalTimeInMinutes(totalMinutes);
                deviceData21.setHour(deviceDataDto.getTimeWhenAppisOn().getHour());
                deviceData21.setDayOfMonth(deviceDataDto.getTimeWhenAppisOn().getDayOfMonth());
                deviceData21.setMonthOfYear(deviceDataDto.getTimeWhenAppisOn().getMonthValue());
                deviceData21.setYear(deviceDataDto.getTimeWhenAppisOn().getYear());
                deviceData21.setDayOfWeek(deviceDataDto.getTimeWhenAppisOn().getDayOfWeek());
                deviceData21.setMonth(deviceDataDto.getTimeWhenAppisOn().getMonth());
                deviceData1Repository.save(deviceData21);
                int minu = 60 - deviceDataDto.getTimeWhenAppisOn().getMinute();
                long totalMinutes1 = totalMinutes - minu;
                long totalhours = totalMinutes1 / 60;
                for (int i = 1; i <= totalhours + 1; i++) {
                    DeviceData1 deviceData1 = new DeviceData1();
                    deviceData1.setUser(user);
                    deviceData1.setDeviceId(deviceDataDto.getDeviceId());
                    deviceData1.setTimestamp(LocalDateTime.now());
                    deviceData1.setEventValue(deviceDataDto.getEventValue());
                    deviceData1.setApplianceName(deviceDataDto.getApplianceName());
                    deviceData1.setConsumption((deviceDataDto.getEventValue() * deviceDataDto.getTimeInMinutes()));
                    deviceData1.setTimeWhenAppisOn(deviceDataDto.getTimeWhenAppisOn());
                    int hour = deviceDataDto.getTimeWhenAppisOn().getHour() + i;
                    int dayOfMonth = deviceDataDto.getTimeWhenAppisOn().getDayOfMonth();
                    int monthOfYear = deviceDataDto.getTimeWhenAppisOn().getMonthValue();
                    int year = deviceDataDto.getTimeWhenAppisOn().getYear();
                    if (hour > 24) {

                        if (dayOfMonth > 31) {

                            monthOfYear = monthOfYear + 1;
                            if (monthOfYear > 12) {
                                year = year + 1;
                                monthOfYear = monthOfYear - 12;
                            }
                            dayOfMonth = dayOfMonth - 31;
                            hour = 1;
                        }
                        dayOfMonth = dayOfMonth + 1;
                        hour = hour - 24;
                    }
                    long totalminutesPerHour = totalMinutes1;//192-30=162
                    long min = 60;
                    long total = totalminutesPerHour;//162
                    total = total - ((i - 1) * 60);//156-(2*60)==156-120==96
                    if (total < 60) {
                        min = total;
                    }
                    deviceData1.setTimeInMinutes(min);
                    deviceData1.setTotalTimeInMinutes(totalminutesPerHour);
                    deviceData1.setConsumption((deviceDataDto.getEventValue() * min)/1000);
                    deviceData1.setPrice(deviceData1.getConsumption() * 2);
                    deviceData1.setCo2Emission(deviceData1.getConsumption()*0.8 );
                    deviceData1.setPricePerUnit(2);
                    deviceData1.setCo2EmissionPerUnit(0.8);
                    deviceData1.setTotalTimeInMinutes(total);
                    deviceData1.setHour(hour);
                    deviceData1.setDayOfMonth(dayOfMonth);
                    deviceData1.setMonthOfYear(monthOfYear);
                    deviceData1.setYear(year);
                    deviceData21.setDayOfWeek(deviceDataDto.getTimeWhenAppisOn().getDayOfWeek());
                    deviceData21.setMonth(deviceDataDto.getTimeWhenAppisOn().getMonth());
                    deviceData1Repository.save(deviceData1);
                }
            }
            else if (deviceDataDto.getTimestamp().getYear() == deviceDataDto.getTimeWhenAppisOn().getYear() &&
                    deviceDataDto.getTimestamp().getMonth() == deviceDataDto.getTimeWhenAppisOn().getMonth() &&
                    deviceDataDto.getTimestamp().getDayOfMonth() != deviceDataDto.getTimeWhenAppisOn().getDayOfMonth()) {
                //1 day==24 hours
                //1 hour=60 minutes
                LocalDateTime time1=deviceDataDto.getTimeWhenAppisOn();
                int minute1=60-time1.getMinute();
                int hour1=23-time1.getHour();
                int day1=time1.getDayOfMonth();
                LocalDateTime time2=LocalDateTime.now();
                int minute2= time2.getMinute();
                int hour2= time2.getHour();
                long totalminuteslastday=(hour2*60)+minute2;
                //15-01-2023 07-43-09467---16-01-2023 12-40-0000
                long totalminuteson1stday=(hour1*60)+minute1;
                //totalMinutes=totalMinutes+totalminuteslastday;
                DeviceData1 deviceData21 = new DeviceData1();
                deviceData21.setUser(user);
                deviceData21.setDeviceId(deviceDataDto.getDeviceId());
                deviceData21.setTimestamp(LocalDateTime.now());
                deviceData21.setEventValue(deviceDataDto.getEventValue());
                deviceData21.setApplianceName(deviceDataDto.getApplianceName());
                deviceData21.setTimeWhenAppisOn(deviceDataDto.getTimeWhenAppisOn());
                deviceData21.setTimeInMinutes(totalminuteson1stday);
                deviceData21.setConsumption((deviceDataDto.getEventValue() * totalminuteson1stday)/1000);
                deviceData21.setPrice(deviceData21.getConsumption() * 2);
                deviceData21.setCo2Emission(deviceData21.getConsumption() * 0.8);
                deviceData21.setPricePerUnit(2);
                deviceData21.setCo2EmissionPerUnit(0.8);
                deviceData21.setTotalTimeInMinutes(totalMinutes);
                deviceData21.setHour(deviceDataDto.getTimeWhenAppisOn().getHour());
                deviceData21.setDayOfMonth(deviceDataDto.getTimeWhenAppisOn().getDayOfMonth());
                deviceData21.setMonthOfYear(deviceDataDto.getTimeWhenAppisOn().getMonthValue());
                deviceData21.setYear(deviceDataDto.getTimeWhenAppisOn().getYear());
                deviceData21.setDayOfWeek(deviceDataDto.getTimeWhenAppisOn().getDayOfWeek());
                deviceData21.setMonth(deviceDataDto.getTimeWhenAppisOn().getMonth());
                deviceData1Repository.save(deviceData21);
                long totalMinutes1=totalMinutes - totalminuteson1stday;
                long totalhours = totalMinutes1 / 60;
                long totalDays1=totalMinutes1/1440;
                for (int i = 1; i <= totalDays1+1; i++) {
                    DeviceData1 deviceData1 = new DeviceData1();
                    deviceData1.setUser(user);
                    deviceData1.setDeviceId(deviceDataDto.getDeviceId());
                    deviceData1.setTimestamp(LocalDateTime.now());
                    deviceData1.setEventValue(deviceDataDto.getEventValue());
                    deviceData1.setApplianceName(deviceDataDto.getApplianceName());
                    deviceData1.setConsumption((deviceDataDto.getEventValue() * deviceDataDto.getTimeInMinutes()));
                    deviceData1.setTimeWhenAppisOn(deviceDataDto.getTimeWhenAppisOn());
                    int hour = deviceDataDto.getTimeWhenAppisOn().getHour();
                    int dayOfMonth = deviceDataDto.getTimeWhenAppisOn().getDayOfMonth()+i;
                    DayOfWeek dayOfWeek=deviceDataDto.getTimeWhenAppisOn().getDayOfWeek().plus(i);
                    Month month=deviceDataDto.getTimeWhenAppisOn().getMonth();
                    int monthOfYear = deviceDataDto.getTimeWhenAppisOn().getMonthValue();
                    int year = deviceDataDto.getTimeWhenAppisOn().getYear();
                    if(dayOfMonth>31){
                        monthOfYear=monthOfYear+1;
                        month=month.plus(1);
                        dayOfMonth=dayOfMonth-(31*(dayOfMonth/31));
                        if(monthOfYear>12){
                            year=year+1;
                            monthOfYear=monthOfYear-12;
                            dayOfMonth = dayOfMonth - (31 * (dayOfMonth / 31));
                        }
                    }
                    long totalminutesPerHour = totalMinutes1;
                    long min = 60*24;
                    long total = totalminutesPerHour;
                    total = total - ((i-1) * 60*24);
                    if (total < 60*24) {
                        min = total;
                    }
                    deviceData1.setTimeInMinutes(min);
                    deviceData1.setConsumption((deviceDataDto.getEventValue() * min)/1000);
                    deviceData1.setPrice(deviceData1.getConsumption()*2);
                    deviceData1.setCo2Emission(deviceData1.getConsumption()*0.8);
                    deviceData1.setPricePerUnit(2);
                    deviceData1.setCo2EmissionPerUnit(0.8);
                    deviceData1.setTotalTimeInMinutes(total);
                    deviceData1.setHour(hour);
                    deviceData1.setDayOfMonth(dayOfMonth);
                    deviceData1.setMonthOfYear(monthOfYear);
                    deviceData1.setYear(year);
                    deviceData1.setMonth(month);
                    deviceData1.setDayOfWeek(dayOfWeek);
                    deviceData1Repository.save(deviceData1);
                }

//            } else if (totalHours<=730 && totalHours>24) {
//                for(int i=0;i<totalHours;i++){
//                    DeviceData deviceData1 = new DeviceData();
//                    deviceData1.setUser(user);
//                    deviceData1.setDeviceId(deviceDataDto.getDeviceId());
//                    deviceData1.setTimestamp(LocalDateTime.now());
//                    deviceData1.setEventValue(deviceDataDto.getEventValue());
//                    deviceData1.setApplianceName(deviceDataDto.getApplianceName());
//                    deviceData1.setConsumption((deviceDataDto.getEventValue() * deviceDataDto.getTimeInMinutes()));
//                    deviceData1.setTimeWhenAppisOn(deviceDataDto.getTimeWhenAppisOn());
//                    int hour=deviceDataDto.getTimeWhenAppisOn().getHour()+i;
//                    int dayOfMonth=deviceDataDto.getTimeWhenAppisOn().getDayOfMonth();
//                    int monthOfYear=deviceDataDto.getTimeWhenAppisOn().getMonthValue();
//                    int year=deviceDataDto.getTimeWhenAppisOn().getYear();
//
////                    static void factors(int n, int i){
////                        if (i <= n) {
////                            if (n % i == 0) {
////                                System.out.print(i + " ");}
////                            factors(n, i + 1);}}
//
//                    if(hour>24){
//                        if(dayOfMonth>30){
//                            monthOfYear=monthOfYear+1;
//                            if(monthOfYear>12){
//                                year=year+1;
//                                monthOfYear=monthOfYear-12;
//                            }
//                            else{
//                                dayOfMonth=dayOfMonth-30;
//                                hour=hour-24;
//                            }
//                        }
//                        else{
//                            dayOfMonth=dayOfMonth+1;
//                            hour=hour-24;
//                        }
//                    }
//                    else{
//                        hour=hour+i;
//                    }
//                    long totalminutesPerHour=deviceDataDto.getTimeInMinutes();
//                    long min=60;
//                    long total=totalminutesPerHour;
//                    total=total-((i+1)*60);
//                    if(total<60){
//                        min=total;
//                    }
//                    deviceData1.setTimeInMinutes(min);
//                    deviceData1.setConsumption((deviceDataDto.getEventValue() * min));
//                    deviceData1.setTotalTimeInMinutes(total);
//                    deviceData1.setHour(hour);
//                    deviceData1.setDayOfMonth(dayOfMonth);
//                    deviceData1.setMonthOfYear(monthOfYear);
//                    deviceData1.setYear(year);
//                    deviceDataRepository.save(deviceData1);
//                    //deviceData1.setTimeWhenAppisOn(LocalDateTime.now());
//                }

            }
//            else if (deviceDataDto.getTimestamp().getYear() == deviceDataDto.getTimeWhenAppisOn().getYear() &&
//                    deviceDataDto.getTimestamp().getMonth() == deviceDataDto.getTimeWhenAppisOn().getMonth() &&
//                    deviceDataDto.getTimestamp().getDayOfMonth() != deviceDataDto.getTimeWhenAppisOn().getDayOfMonth()) {
//                //else if (totalDays<=31) {
//                //totalDays<=31
//                for (int i = 0; i < totalHours; i++) {
//                    DeviceData deviceData1 = new DeviceData();
//                    deviceData1.setUser(user);
//                    deviceData1.setDeviceId(deviceDataDto.getDeviceId());
//                    deviceData1.setTimestamp(LocalDateTime.now());
//                    deviceData1.setEventValue(deviceDataDto.getEventValue());
//                    deviceData1.setApplianceName(deviceDataDto.getApplianceName());
//                    deviceData1.setConsumption((deviceDataDto.getEventValue() * deviceDataDto.getTimeInMinutes()));
//                    deviceData1.setTimeWhenAppisOn(deviceDataDto.getTimeWhenAppisOn());
//                    int hour = deviceDataDto.getTimeWhenAppisOn().getHour() + i;
//                    int dayOfMonth = deviceDataDto.getTimeWhenAppisOn().getDayOfMonth();
//                    int monthOfYear = deviceDataDto.getTimeWhenAppisOn().getMonthValue();
//                    int year = deviceDataDto.getTimeWhenAppisOn().getYear();
//                    int k;
//                    int j;
//                    if (hour >= 24) {
//                        dayOfMonth = dayOfMonth + (1 * (hour / 24));
//                        hour = hour - (24 * (hour / 24));
//                        if (dayOfMonth > 31) {
//                            monthOfYear = monthOfYear + 1;
//                            dayOfMonth = dayOfMonth - (31 * (dayOfMonth / 31));
//                            hour = (hour - (24 * (hour / 24)));
//                            if (monthOfYear > 12) {
//                                year = year + 1;
//                                monthOfYear = monthOfYear - 12;
//                                dayOfMonth = dayOfMonth - (31 * (dayOfMonth / 31));
//                                hour = (hour - (24 * (hour / 24)));
////                            } else {
////                                dayOfMonth = dayOfMonth + (1 * (hour / 24));
////                                hour = (hour - (24 * (hour / 24)));
////                            }
//                            }
////                        else {
////                            dayOfMonth = dayOfMonth + (1 * (hour / 24));
////                            hour = hour - (24 * (hour / 24));
////
////                        }
//                        }
//                    }
////                        if(hour>49 && hour<49+24){
////                            if(dayOfMonth>30){
////                                monthOfYear=monthOfYear+1;
////                                if(monthOfYear>12){
////                                    year=year+1;
////                                    monthOfYear=monthOfYear-12;
////                                }
////                                else{
////                                    dayOfMonth=dayOfMonth-30;
////                                    hour=(hour-(49));
////                                }
////                            }
////                            else{
////                                dayOfMonth=dayOfMonth+1;
////                                hour=hour-(49);
////                            }
////                        }
////                        if(hour>49 && hour<49+24){
////                            if(dayOfMonth>30){
////                                monthOfYear=monthOfYear+1;
////                                if(monthOfYear>12){
////                                    year=year+1;
////                                    monthOfYear=monthOfYear-12;
////                                }
////                                else{
////                                    dayOfMonth=dayOfMonth-30;
////                                    hour=(hour-(49));
////                                }
////                            }
////                            else{
////                                dayOfMonth=dayOfMonth+1;
////                                hour=hour-(49);
////                            }
////                        }
//                    long totalminutesPerHour = deviceDataDto.getTimeInMinutes();
//                    long min = 60;
//                    long total = totalminutesPerHour;
//                    total = total - ((i + 1) * 60);
//                    if (total < 60) {
//                        min = total;
//                    }
//                    deviceData1.setTimeInMinutes(min);
//                    deviceData1.setTotalTimeInMinutes(totalminutesPerHour);
//                    deviceData1.setConsumption((deviceDataDto.getEventValue() * min));
//                    deviceData1.setPrice(deviceData1.getConsumption()*1);
//                    deviceData1.setCo2Emission(deviceData1.getConsumption()*0.5);
//                    deviceData1.setPricePerUnit(1);
//                    deviceData1.setCo2EmissionPerUnit(0.5);
//                    deviceData1.setTotalTimeInMinutes(total);
//                    deviceData1.setHour(hour);
//                    deviceData1.setDayOfMonth(dayOfMonth);
//                    deviceData1.setMonthOfYear(monthOfYear);
//                    deviceData1.setYear(year);
//                    deviceDataRepository.save(deviceData1);
//                }
//            }

//            else if(deviceDataDto.getTimestamp().getYear()==deviceDataDto.getTimeWhenAppisOn().getYear()&&
//                    deviceDataDto.getTimestamp().getMonth()!=deviceDataDto.getTimeWhenAppisOn().getMonth()) {
//            	
//            	
//
//            }
            else if(deviceDataDto.getTimestamp().getYear()==deviceDataDto.getTimeWhenAppisOn().getYear()&&        deviceDataDto.getTimestamp().getMonth()!=deviceDataDto.getTimeWhenAppisOn().getMonth()) 
            {    
            	LocalDateTime time1=deviceDataDto.getTimeWhenAppisOn(); 
            int minute1=60-time1.getMinute();  
            int hour1=23-time1.getHour(); 
            int day1=time1.getDayOfMonth();   
            LocalDateTime time2=LocalDateTime.now(); 
            int minute2= time2.getMinute();   
            int hour2= time2.getHour();  
            long totalminuteslastday=(hour2*60)+minute2;
            //15-01-2023 07-43-09467---16-01-2023 12-40-0000  
            long totalminuteson1stday=(hour1*60)+minute1;    //totalMinutes=totalMinutes+totalminuteslastday;   
            DeviceData1 deviceData21 = new DeviceData1();   
            deviceData21.setUser(user);  
            deviceData21.setDeviceId(deviceDataDto.getDeviceId());  
            deviceData21.setTimestamp(LocalDateTime.now()); 
            deviceData21.setEventValue(deviceDataDto.getEventValue()); 
            deviceData21.setApplianceName(deviceDataDto.getApplianceName()); 
            deviceData21.setTimeWhenAppisOn(deviceDataDto.getTimeWhenAppisOn());  
            deviceData21.setTimeInMinutes(totalminuteson1stday);    
            deviceData21.setConsumption((deviceDataDto.getEventValue() * totalminuteson1stday)/1000);
            deviceData21.setPrice(deviceData21.getConsumption() * 2);  
            deviceData21.setCo2Emission(deviceData21.getConsumption() * 0.8);   
            deviceData21.setPricePerUnit(2); 
            deviceData21.setCo2EmissionPerUnit(0.8);   
            deviceData21.setTotalTimeInMinutes(totalMinutes); 
            deviceData21.setHour(deviceDataDto.getTimeWhenAppisOn().getHour());   
            deviceData21.setDayOfMonth(deviceDataDto.getTimeWhenAppisOn().getDayOfMonth());
            deviceData21.setMonthOfYear(deviceDataDto.getTimeWhenAppisOn().getMonthValue()); 
            deviceData21.setYear(deviceDataDto.getTimeWhenAppisOn().getYear());  
            deviceData21.setDayOfWeek(deviceDataDto.getTimeWhenAppisOn().getDayOfWeek());  
            deviceData21.setMonth(deviceDataDto.getTimeWhenAppisOn().getMonth());   
            deviceData1Repository.save(deviceData21);   
            long totalMinutes1=totalMinutes - totalminuteson1stday;
            long totalhours = totalMinutes1 / 60;  
            long totalDays1=totalMinutes1/1440; 
            for (int i = 1; i <= totalDays1+1; i++) 
            {        DeviceData1 deviceData1 = new DeviceData1();  
            deviceData1.setUser(user);     
            deviceData1.setDeviceId(deviceDataDto.getDeviceId());  
            deviceData1.setTimestamp(LocalDateTime.now());    
            deviceData1.setEventValue(deviceDataDto.getEventValue());
            deviceData1.setApplianceName(deviceDataDto.getApplianceName());   
            deviceData1.setConsumption((deviceDataDto.getEventValue() * deviceDataDto.getTimeInMinutes()));
            deviceData1.setTimeWhenAppisOn(deviceDataDto.getTimeWhenAppisOn());     
            int hour = deviceDataDto.getTimeWhenAppisOn().getHour();      
            int dayOfMonth = deviceDataDto.getTimeWhenAppisOn().getDayOfMonth()+i;  
            DayOfWeek dayOfWeek=deviceDataDto.getTimeWhenAppisOn().getDayOfWeek().plus(i);  
            Month month=deviceDataDto.getTimeWhenAppisOn().getMonth();     
            int monthOfYear1 = deviceDataDto.getTimeWhenAppisOn().getMonthValue();  
            int monthOfYear=monthOfYear1;      
            int year1 = deviceDataDto.getTimeWhenAppisOn().getYear();   
            int year=year1;   
            if(dayOfMonth>31){//32--11--1,60--   
            	monthOfYear=monthOfYear+(dayOfMonth/31);
            	//--31/31==1--62/31==2       
            	month=month.plus(dayOfMonth/31);  
            	dayOfMonth=dayOfMonth-(31*(dayOfMonth/31));      
            	if(monthOfYear>12)
            	{                year=year+1;          
            	monthOfYear=monthOfYear-12;    
            	dayOfMonth = dayOfMonth - (31 * (dayOfMonth / 31));
            	}        
            	}     
            int dayOfMonth2=dayOfMonth; 
            int monthOfYear2=monthOfYear;    
            int year2=year;        
            Month month2=month;   
            DayOfWeek dayOfWeek2=dayOfWeek;  
            long totalminutesPerHour = totalMinutes1;    
            long min = 60*24;      
            long total = totalminutesPerHour;    
            total = total - ((i-1) * 60*24); 
            if (total < 60*24) 
            {            min = total;        }   
            deviceData1.setTimeInMinutes(min);   
            deviceData1.setConsumption((deviceDataDto.getEventValue() * min)/1000);   
            deviceData1.setPrice(deviceData1.getConsumption()*2);    
            deviceData1.setCo2Emission(deviceData1.getConsumption()*0.8);     
            deviceData1.setPricePerUnit(2);    
            deviceData1.setCo2EmissionPerUnit(0.8);    
            deviceData1.setTotalTimeInMinutes(total);  
            deviceData1.setHour(hour);     
            deviceData1.setDayOfMonth(dayOfMonth2);   
            deviceData1.setMonthOfYear(monthOfYear2);    
            deviceData1.setYear(year2);     
            deviceData1.setMonth(month2);   
            deviceData1.setDayOfWeek(dayOfWeek2);   
            deviceData1Repository.save(deviceData1); 
            }}
            
    
            
            else if (deviceDataDto.getTimestamp().getYear()!=deviceDataDto.getTimeWhenAppisOn().getYear()) {
                //1 day==24 hours
                //1 hour=60 minutes
                LocalDateTime time1=deviceDataDto.getTimeWhenAppisOn();
                int minute1=60-time1.getMinute();
                int hour1=23-time1.getHour();
                int day1=time1.getDayOfMonth();
                LocalDateTime time2=LocalDateTime.now();
                int minute2= time2.getMinute();
                int hour2= time2.getHour();
                long totalminuteslastday=(hour2*60)+minute2;
                //15-01-2023 07-43-09467---16-01-2023 12-40-0000
                long totalminuteson1stday=(hour1*60)+minute1;
                //totalMinutes=totalMinutes+totalminuteslastday;
                DeviceData1 deviceData21 = new DeviceData1();
                deviceData21.setUser(user);
                deviceData21.setDeviceId(deviceDataDto.getDeviceId());
                deviceData21.setTimestamp(LocalDateTime.now());
                deviceData21.setEventValue(deviceDataDto.getEventValue());
                deviceData21.setApplianceName(deviceDataDto.getApplianceName());
                deviceData21.setTimeWhenAppisOn(deviceDataDto.getTimeWhenAppisOn());
                deviceData21.setTimeInMinutes(totalminuteson1stday);
                deviceData21.setConsumption((deviceDataDto.getEventValue() * totalminuteson1stday)/1000);
                deviceData21.setPrice(deviceData21.getConsumption() * 2);
                deviceData21.setCo2Emission(deviceData21.getConsumption() * 0.8);
                deviceData21.setPricePerUnit(2);
                deviceData21.setCo2EmissionPerUnit(0.8);
                deviceData21.setTotalTimeInMinutes(totalMinutes);
                deviceData21.setHour(deviceDataDto.getTimeWhenAppisOn().getHour());
                deviceData21.setDayOfMonth(deviceDataDto.getTimeWhenAppisOn().getDayOfMonth());
                deviceData21.setMonthOfYear(deviceDataDto.getTimeWhenAppisOn().getMonthValue());
                deviceData21.setYear(deviceDataDto.getTimeWhenAppisOn().getYear());
                deviceData21.setDayOfWeek(deviceDataDto.getTimeWhenAppisOn().getDayOfWeek());
                deviceData21.setMonth(deviceDataDto.getTimeWhenAppisOn().getMonth());
                deviceData1Repository.save(deviceData21);
                long totalMinutes1=totalMinutes - totalminuteson1stday;
                long totalhours = totalMinutes1 / 60;
                long totalDays1=totalMinutes1/1440;
                for (int i = 1; i <= totalDays1+1; i++) {
                    DeviceData1 deviceData1 = new DeviceData1();
                    deviceData1.setUser(user);
                    deviceData1.setDeviceId(deviceDataDto.getDeviceId());
                    deviceData1.setTimestamp(LocalDateTime.now());
                    deviceData1.setEventValue(deviceDataDto.getEventValue());
                    deviceData1.setApplianceName(deviceDataDto.getApplianceName());
                    deviceData1.setConsumption((deviceDataDto.getEventValue() * deviceDataDto.getTimeInMinutes()));
                    deviceData1.setTimeWhenAppisOn(deviceDataDto.getTimeWhenAppisOn());
                    int hour = deviceDataDto.getTimeWhenAppisOn().getHour();
                    int dayOfMonth = deviceDataDto.getTimeWhenAppisOn().getDayOfMonth()+i;
                    DayOfWeek dayOfWeek=deviceDataDto.getTimeWhenAppisOn().getDayOfWeek().plus(i);
                    Month month=deviceDataDto.getTimeWhenAppisOn().getMonth();
                    int monthOfYear1 = deviceDataDto.getTimeWhenAppisOn().getMonthValue();
                    int monthOfYear=monthOfYear1;
                    int year1 = deviceDataDto.getTimeWhenAppisOn().getYear();
                    int year=year1;
                    if(dayOfMonth>30){//32--11--1,60--
                        monthOfYear=monthOfYear+(dayOfMonth/31);//--31/31==1--62/31==2
                        month=month.plus(dayOfMonth/31);
                        dayOfMonth=dayOfMonth-(31*(dayOfMonth/31))+1;
                        if(monthOfYear>12){
                            year=year+1;
                            monthOfYear=monthOfYear-12;
                            dayOfMonth = dayOfMonth - (31 * (dayOfMonth / 31));
                        }
                    }
                    int dayOfMonth2=dayOfMonth;
                    int monthOfYear2=monthOfYear;
                    int year2=year;
                    Month month2=month;
                    DayOfWeek dayOfWeek2=dayOfWeek;
                    long totalminutesPerHour = totalMinutes1;
                    long min = 60*24;
                    long total = totalminutesPerHour;
                    total = total - ((i-1) * 60*24);
                    if (total < 60*24) {
                        min = total;
                    }
                    deviceData1.setTimeInMinutes(min);
                    deviceData1.setConsumption((deviceDataDto.getEventValue() * min)/1000);
                    deviceData1.setPrice(deviceData1.getConsumption()*2);
                    deviceData1.setCo2Emission(deviceData1.getConsumption()*0.8);
                    deviceData1.setPricePerUnit(2);
                    deviceData1.setCo2EmissionPerUnit(0.8);
                    deviceData1.setTotalTimeInMinutes(total);
                    deviceData1.setHour(hour);
                    deviceData1.setDayOfMonth(dayOfMonth2);
                    deviceData1.setMonthOfYear(monthOfYear2);
                    deviceData1.setYear(year2);
                    deviceData1.setMonth(month2);
                    deviceData1.setDayOfWeek(dayOfWeek2);
                    deviceData1Repository.save(deviceData1);
                }
            }



//            else if (deviceDataDto.getTimestamp().getYear()!=deviceDataDto.getTimeWhenAppisOn().getYear()) {
//
//                for(int i=0;i<totalHours;i++){
//                    DeviceData deviceData1 = new DeviceData();
//                    deviceData1.setUser(user);
//                    deviceData1.setDeviceId(deviceDataDto.getDeviceId());
//                    deviceData1.setTimestamp(LocalDateTime.now());
//                    deviceData1.setEventValue(deviceDataDto.getEventValue());
//                    deviceData1.setApplianceName(deviceDataDto.getApplianceName());
//                    deviceData1.setConsumption((deviceDataDto.getEventValue() * deviceDataDto.getTimeInMinutes()));
//                    deviceData1.setTimeWhenAppisOn(deviceDataDto.getTimeWhenAppisOn());
//                    int hour=deviceDataDto.getTimeWhenAppisOn().getHour()+i;
//                    int dayOfMonth=deviceDataDto.getTimeWhenAppisOn().getDayOfMonth();
//                    int monthOfYear=deviceDataDto.getTimeWhenAppisOn().getMonthValue();
//                    int year=deviceDataDto.getTimeWhenAppisOn().getYear();
//                    if(hour>=24){
//                        dayOfMonth=dayOfMonth+(1*(hour/24));
//                        hour=hour-(24*(hour/24));
//                        if(dayOfMonth>31){
//                            monthOfYear=monthOfYear+1;
//                            dayOfMonth = dayOfMonth - (31 * (dayOfMonth / 31));
//                            hour=(hour-(24*(hour/24)));
//                            if(monthOfYear>12){
//                                year=year+1;
//                                monthOfYear=monthOfYear-(12*(monthOfYear/12));
//                                dayOfMonth = dayOfMonth - (31 * (dayOfMonth / 31));
//                                hour=(hour-(24*(hour/24)));
//                            }
////                            else{
////                                dayOfMonth = dayOfMonth + (1 * (hour / 24));
////                                hour = (hour - (24 * (hour / 24)));
////                            }
//                        }
////                        else{
////                            dayOfMonth=dayOfMonth+(1*(hour/24));
////                            hour=hour-(24*(hour/24));
////                        }
//                    }
//                    long totalminutesPerHour=deviceDataDto.getTimeInMinutes();
//                    long min=60;
//                    long total=totalminutesPerHour;
//                    total=total-((i+1)*60);
//                    if(total<60){
//                        min=total;
//                    }
//                    deviceData1.setTimeInMinutes(min);
//                    deviceData1.setTotalTimeInMinutes(totalminutesPerHour);
//                    deviceData1.setConsumption((deviceDataDto.getEventValue() * min));
//                    deviceData1.setPrice(deviceData1.getConsumption()*3.5);
//                    deviceData1.setCo2Emission(deviceData1.getConsumption()*0.25);
//                    deviceData1.setPricePerUnit(3.5);
//                    deviceData1.setCo2EmissionPerUnit(0.25);
//                    deviceData1.setTotalTimeInMinutes(total);
//                    deviceData1.setHour(hour);
//                    deviceData1.setDayOfMonth(dayOfMonth);
//                    deviceData1.setMonthOfYear(monthOfYear);
//                    deviceData1.setYear(year);
//                    deviceDataRepository.save(deviceData1);
//                }
//            }
           // kafkaProducer.sendMessage(deviceDataDto);
            return ResponseEntity.ok("Json message sent to kafka topic");
    }
//            DeviceData deviceData1 = new DeviceData();
//            deviceData1.setUser(user);
//            deviceData1.setDeviceId(deviceDataDto.getDeviceId());
//            deviceData1.setTimestamp(LocalDateTime.now());
//            deviceData1.setEventValue(deviceDataDto.getEventValue());
//            deviceData1.setApplianceName(deviceDataDto.getApplianceName());
//            deviceData1.setConsumption((deviceDataDto.getEventValue() * deviceDataDto.getTimeInMinutes()));
//            deviceData1.setTimeInMinutes(deviceDataDto.getTimeInMinutes());
//            deviceDataRepository.save(deviceDa\ta1);
//        }
//        else{
//            DeviceData oldDeviceData=deviceData2.get(deviceData2.size()-1);
//            if(oldDeviceData==null){
//                DeviceData deviceData1=new DeviceData();
//                deviceData1.setUser(user);
//                deviceData1.setDeviceId(deviceDataDto.getDeviceId());
//                deviceData1.setTimestamp(LocalDateTime.now());
//                deviceData1.setEventValue(deviceDataDto.getEventValue());
//                deviceData1.setApplianceName(deviceDataDto.getApplianceName());
//                deviceData1.setConsumption((deviceDataDto.getEventValue()*deviceDataDto.getTimeInMinutes()));
//                deviceData1.setTimeInMinutes(deviceDataDto.getTimeInMinutes());
//                deviceDataRepository.save(deviceData1);
//            }
//            else{
//                if(oldDeviceData!=null){
//                    //if(oldDeviceData.getTimestamp().getDayOfMonth()==deviceDataDto.getTimestamp().getDayOfMonth()){
//                    if((oldDeviceData.getTimestamp().getHour()==deviceDataDto.getTimestamp().getHour())
//                            &&(oldDeviceData.getTimestamp().getDayOfMonth()==deviceDataDto.getTimestamp().getDayOfMonth())){
//                        DeviceData oldDeviceData1=deviceData2.get(deviceData2.size()-1);
//                        Consumption= ((deviceDataDto.getEventValue()*deviceDataDto.getTimeInMinutes())+ oldDeviceData.getConsumption());
//                        oldDeviceData1.setConsumption(Consumption);
//                        oldDeviceData1.setEventValue(deviceDataDto.getEventValue());
//                        oldDeviceData1.setTimestamp(LocalDateTime.now());
//                        oldDeviceData1.setTimeInMinutes(deviceDataDto.getTimeInMinutes()+ oldDeviceData.getTimeInMinutes());
//                        deviceDataRepository.save(oldDeviceData1);
//                    }
//                    else{
//                        DeviceData deviceData1=new DeviceData();
//                        deviceData1.setUser(user);
//                        deviceData1.setDeviceId(deviceDataDto.getDeviceId());
//                        deviceData1.setTimestamp(LocalDateTime.now());
//                        deviceData1.setEventValue(deviceDataDto.getEventValue());
//                        deviceData1.setApplianceName(deviceDataDto.getApplianceName());
//                        deviceData1.setConsumption( (deviceDataDto.getEventValue()*deviceDataDto.getTimeInMinutes()));
//                        deviceData1.setTimeInMinutes(deviceDataDto.getTimeInMinutes());
//                        deviceDataRepository.save(deviceData1);
//                    }
//                }
//            }
//        }

        else
        {
            DeviceData1 oldDeviceData=deviceData2.get(deviceData2.size()-1);
            long totalMinutes = (deviceDataDto.getTimeInMinutes());//1112
            long totalHours = (totalMinutes) / 60;//18.5
            long totalDays = (totalMinutes) / 1440;
            if((oldDeviceData.getTimestamp().getYear()==deviceDataDto.getTimestamp().getYear())&&
            (oldDeviceData.getTimestamp().getMonth()==deviceDataDto.getTimestamp().getMonth()) &&
                    (oldDeviceData.getTimestamp().getDayOfMonth()==deviceDataDto.getTimestamp().getDayOfMonth())&&
                    (oldDeviceData.getTimestamp().getHour()==deviceDataDto.getTimestamp().getHour())){
                oldDeviceData.setTimeInMinutes(deviceDataDto.getTimeInMinutes()+ oldDeviceData.getTimeInMinutes());
                if(oldDeviceData.getTimeInMinutes()<=60){
                    Consumption= ((deviceDataDto.getEventValue()* totalMinutes)+ oldDeviceData.getConsumption());
                    oldDeviceData.setConsumption(Consumption/1000);
                    oldDeviceData.setTotalTimeInMinutes(totalMinutes);
                    oldDeviceData.setPrice(oldDeviceData.getConsumption()*2);
                    oldDeviceData.setCo2Emission(oldDeviceData.getConsumption()*0.8);
                    oldDeviceData.setEventValue(deviceDataDto.getEventValue());
                    oldDeviceData.setCo2EmissionPerUnit(2);
                    oldDeviceData.setPricePerUnit(0.8);
                    oldDeviceData.setTimestamp(LocalDateTime.now());
                    oldDeviceData.setTimeInMinutes(deviceDataDto.getTimeInMinutes()+ oldDeviceData.getTimeInMinutes());
                    deviceData1Repository.save(oldDeviceData);
                }
                else{
                    DeviceData1 deviceData1 = new DeviceData1();
                    deviceData1.setUser(user);
                    deviceData1.setDeviceId(deviceDataDto.getDeviceId());
                    deviceData1.setTimestamp(LocalDateTime.now());
                    deviceData1.setEventValue(deviceDataDto.getEventValue());
                    deviceData1.setApplianceName(deviceDataDto.getApplianceName());
                    deviceData1.setConsumption((deviceDataDto.getEventValue() * totalMinutes)/1000);
                    deviceData1.setPrice(deviceData1.getConsumption()*2);
                    deviceData1.setCo2Emission(deviceData1.getConsumption()*0.8);
                    deviceData1.setPricePerUnit(2);
                    deviceData1.setCo2EmissionPerUnit(0.8);
                    deviceData1.setTimeInMinutes(deviceDataDto.getTimeInMinutes());
                    deviceData1.setTotalTimeInMinutes(totalMinutes);
                    deviceData1.setTimeWhenAppisOn(deviceDataDto.getTimeWhenAppisOn());
                    deviceData1.setHour(deviceDataDto.getTimeWhenAppisOn().getHour());
                    deviceData1.setDayOfMonth(deviceDataDto.getTimeWhenAppisOn().getDayOfMonth());
                    deviceData1.setMonthOfYear(deviceDataDto.getTimeWhenAppisOn().getMonthValue());
                    deviceData1.setYear(deviceDataDto.getTimeWhenAppisOn().getYear());
                    deviceData1.setDayOfWeek(deviceDataDto.getTimeWhenAppisOn().getDayOfWeek());
                    deviceData1.setMonth(deviceDataDto.getTimeWhenAppisOn().getMonth());
                    deviceData1Repository.save(deviceData1);
                }
            }
            else if (deviceDataDto.getTimestamp().getYear() == deviceDataDto.getTimeWhenAppisOn().getYear() &&
                    deviceDataDto.getTimestamp().getMonth() == deviceDataDto.getTimeWhenAppisOn().getMonth() &&
                    deviceDataDto.getTimestamp().getDayOfMonth() == deviceDataDto.getTimeWhenAppisOn().getDayOfMonth() &&
                    deviceDataDto.getTimestamp().getHour() != deviceDataDto.getTimeWhenAppisOn().getHour()) {
                DeviceData1 deviceData21 = new DeviceData1();
                deviceData21.setUser(user);
                deviceData21.setDeviceId(deviceDataDto.getDeviceId());
                deviceData21.setTimestamp(LocalDateTime.now());
                deviceData21.setEventValue(deviceDataDto.getEventValue());
                deviceData21.setApplianceName(deviceDataDto.getApplianceName());
                deviceData21.setTimeWhenAppisOn(deviceDataDto.getTimeWhenAppisOn());
                deviceData21.setTimeInMinutes(60-deviceDataDto.getTimeWhenAppisOn().getMinute());
                deviceData21.setConsumption((deviceDataDto.getEventValue() *(60-deviceDataDto.getTimeWhenAppisOn().getMinute()))/1000);
                deviceData21.setPrice(deviceData21.getConsumption()*2);
                deviceData21.setCo2Emission(deviceData21.getConsumption()*0.8);
                deviceData21.setPricePerUnit(2);
                deviceData21.setCo2EmissionPerUnit(0.8);
                deviceData21.setTotalTimeInMinutes(totalMinutes);
                deviceData21.setHour(deviceDataDto.getTimeWhenAppisOn().getHour());
                deviceData21.setDayOfMonth(deviceDataDto.getTimeWhenAppisOn().getDayOfMonth());
                deviceData21.setMonthOfYear(deviceDataDto.getTimeWhenAppisOn().getMonthValue());
                deviceData21.setYear(deviceDataDto.getTimeWhenAppisOn().getYear());
                deviceData21.setDayOfWeek(deviceDataDto.getTimeWhenAppisOn().getDayOfWeek());
                deviceData21.setMonth(deviceDataDto.getTimeWhenAppisOn().getMonth());
                deviceData1Repository.save(deviceData21);
                int minu=60-deviceDataDto.getTimeWhenAppisOn().getMinute();
                long totalMinutes1=totalMinutes-minu;
                long totalhours=totalMinutes1/60;
                for (int i = 1; i <= totalhours+1; i++) {
                    DeviceData1 deviceData1 = new DeviceData1();
                    deviceData1.setUser(user);
                    deviceData1.setDeviceId(deviceDataDto.getDeviceId());
                    deviceData1.setTimestamp(LocalDateTime.now());
                    deviceData1.setEventValue(deviceDataDto.getEventValue());
                    deviceData1.setApplianceName(deviceDataDto.getApplianceName());
                    deviceData1.setConsumption((deviceDataDto.getEventValue() * deviceDataDto.getTimeInMinutes()));
                    deviceData1.setTimeWhenAppisOn(deviceDataDto.getTimeWhenAppisOn());
                    int hour = deviceDataDto.getTimeWhenAppisOn().getHour() + i;
                    int dayOfMonth = deviceDataDto.getTimeWhenAppisOn().getDayOfMonth();
                    int monthOfYear = deviceDataDto.getTimeWhenAppisOn().getMonthValue();
                    Month month=deviceDataDto.getTimeWhenAppisOn().getMonth();
                    DayOfWeek dayOfWeek=deviceDataDto.getTimeWhenAppisOn().getDayOfWeek();
                    int year = deviceDataDto.getTimeWhenAppisOn().getYear();
                    if (hour > 24) {

                        if (dayOfMonth > 31) {

                            monthOfYear = monthOfYear + 1;
                            month=month.plus(1);
                            if (monthOfYear > 12) {
                                year = year + 1;
                                monthOfYear = monthOfYear - 12;
                            }
                            dayOfMonth = dayOfMonth - 31;
                            hour = 1;
                        }
                        dayOfMonth = dayOfMonth + 1;
                        dayOfWeek=dayOfWeek.plus(1);
                        hour = hour - 24;
                    }
                    long totalminutesPerHour = totalMinutes1;//192-30=162
                    long min = 60;
                    long total = totalminutesPerHour;//162
                    total = total - ((i-1) * 60);//156-(2*60)==156-120==96
                    if (total < 60) {
                        min = total;
                    }
                    deviceData1.setTimeInMinutes(min);
                    deviceData1.setTotalTimeInMinutes(totalminutesPerHour);
                    deviceData1.setConsumption((deviceDataDto.getEventValue() * min)/1000);
                    deviceData1.setPrice(deviceData1.getConsumption()*2);
                    deviceData1.setCo2Emission(deviceData1.getConsumption()*0.8);
                    deviceData1.setPricePerUnit(2);
                    deviceData1.setCo2EmissionPerUnit(0.8);
                    deviceData1.setTotalTimeInMinutes(total);
                    deviceData1.setHour(hour);
                    deviceData1.setDayOfMonth(dayOfMonth);
                    deviceData1.setMonthOfYear(monthOfYear);
                    deviceData1.setYear(year);
                    deviceData1.setDayOfWeek(dayOfWeek);
                    deviceData1.setMonth(month);
                    deviceData1Repository.save(deviceData1);
                }
                //else if (totalHours<=24) {
//                for (int i = 0; i < totalHours; i++) {
//                    DeviceData deviceData1 = new DeviceData();
//                    deviceData1.setUser(user);
//                    deviceData1.setDeviceId(deviceDataDto.getDeviceId());
//                    deviceData1.setTimestamp(LocalDateTime.now());
//                    deviceData1.setEventValue(deviceDataDto.getEventValue());
//                    deviceData1.setApplianceName(deviceDataDto.getApplianceName());
//                    deviceData1.setConsumption((deviceDataDto.getEventValue() * deviceDataDto.getTimeInMinutes()));
//
////                    deviceData1.setTimeInMinutes(deviceDataDto.getTimeInMinutes());
//                    deviceData1.setTimeWhenAppisOn(deviceDataDto.getTimeWhenAppisOn());
//                    int hour = deviceDataDto.getTimeWhenAppisOn().getHour() + i;
//                    int dayOfMonth = deviceDataDto.getTimeWhenAppisOn().getDayOfMonth();
//                    int monthOfYear = deviceDataDto.getTimeWhenAppisOn().getMonthValue();
//                    int year = deviceDataDto.getTimeWhenAppisOn().getYear();
//                    if (hour > 24) {
//
//                        if (dayOfMonth > 31) {
//
//                            monthOfYear = monthOfYear + 1;
//                            if (monthOfYear > 12) {
//                                year = year + 1;
//                                monthOfYear = monthOfYear - 12;
//                            }
//                            dayOfMonth = dayOfMonth - 31;
//                            hour = 1;
//                        }
//                        dayOfMonth = dayOfMonth + 1;
//                        hour = hour - 24;
//                    }
//                    long totalminutesPerHour = deviceDataDto.getTimeInMinutes();
//                    long min = 60;
//                    long total = totalminutesPerHour;
//                    total = total - ((i + 1) * 60);
//                    if (total < 60) {
//                        min = total;
//                    }
//                    deviceData1.setTimeInMinutes(min);
//                    deviceData1.setTotalTimeInMinutes(totalminutesPerHour);
//                    deviceData1.setConsumption((deviceDataDto.getEventValue() * min));
//                    deviceData1.setPrice(deviceData1.getConsumption()*1);
//                    deviceData1.setCo2Emission(deviceData1.getConsumption()*0.5);
//                    deviceData1.setPricePerUnit(1);
//                    deviceData1.setCo2EmissionPerUnit(0.5);
//                    deviceData1.setTotalTimeInMinutes(total);
//                    deviceData1.setHour(hour);
//                    deviceData1.setDayOfMonth(dayOfMonth);
//                    deviceData1.setMonthOfYear(monthOfYear);
//                    deviceData1.setYear(year);
//                    deviceDataRepository.save(deviceData1);
//                }
//            } else if (totalHours<=730 && totalHours>24) {
//                for(int i=0;i<totalHours;i++){
//                    DeviceData deviceData1 = new DeviceData();
//                    deviceData1.setUser(user);
//                    deviceData1.setDeviceId(deviceDataDto.getDeviceId());
//                    deviceData1.setTimestamp(LocalDateTime.now());
//                    deviceData1.setEventValue(deviceDataDto.getEventValue());
//                    deviceData1.setApplianceName(deviceDataDto.getApplianceName());
//                    deviceData1.setConsumption((deviceDataDto.getEventValue() * deviceDataDto.getTimeInMinutes()));
//                    deviceData1.setTimeWhenAppisOn(deviceDataDto.getTimeWhenAppisOn());
//                    int hour=deviceDataDto.getTimeWhenAppisOn().getHour()+i;
//                    int dayOfMonth=deviceDataDto.getTimeWhenAppisOn().getDayOfMonth();
//                    int monthOfYear=deviceDataDto.getTimeWhenAppisOn().getMonthValue();
//                    int year=deviceDataDto.getTimeWhenAppisOn().getYear();
//
////                    static void factors(int n, int i){
////                        if (i <= n) {
////                            if (n % i == 0) {
////                                System.out.print(i + " ");}
////                            factors(n, i + 1);}}
//
//                    if(hour>24){
//                        if(dayOfMonth>30){
//                            monthOfYear=monthOfYear+1;
//                            if(monthOfYear>12){
//                                year=year+1;
//                                monthOfYear=monthOfYear-12;
//                            }
//                            else{
//                                dayOfMonth=dayOfMonth-30;
//                                hour=hour-24;
//                            }
//                        }
//                        else{
//                            dayOfMonth=dayOfMonth+1;
//                            hour=hour-24;
//                        }
//                    }
//                    else{
//                        hour=hour+i;
//                    }
//                    long totalminutesPerHour=deviceDataDto.getTimeInMinutes();
//                    long min=60;
//                    long total=totalminutesPerHour;
//                    total=total-((i+1)*60);
//                    if(total<60){
//                        min=total;
//                    }
//                    deviceData1.setTimeInMinutes(min);
//                    deviceData1.setConsumption((deviceDataDto.getEventValue() * min));
//                    deviceData1.setTotalTimeInMinutes(total);
//                    deviceData1.setHour(hour);
//                    deviceData1.setDayOfMonth(dayOfMonth);
//                    deviceData1.setMonthOfYear(monthOfYear);
//                    deviceData1.setYear(year);
//                    deviceDataRepository.save(deviceData1);
//                    //deviceData1.setTimeWhenAppisOn(LocalDateTime.now());
//                }

            }
//            else if (deviceDataDto.getTimestamp().getYear() == deviceDataDto.getTimeWhenAppisOn().getYear() &&
//                    deviceDataDto.getTimestamp().getMonth() == deviceDataDto.getTimeWhenAppisOn().getMonth() &&
//                    deviceDataDto.getTimestamp().getDayOfMonth() != deviceDataDto.getTimeWhenAppisOn().getDayOfMonth()) {
//                //else if (totalDays<=31) {
//                //totalDays<=31
//                for (int i = 0; i < totalHours; i++) {
//                    DeviceData deviceData1 = new DeviceData();
//                    deviceData1.setUser(user);
//                    deviceData1.setDeviceId(deviceDataDto.getDeviceId());
//                    deviceData1.setTimestamp(LocalDateTime.now());
//                    deviceData1.setEventValue(deviceDataDto.getEventValue());
//                    deviceData1.setApplianceName(deviceDataDto.getApplianceName());
//                    deviceData1.setConsumption((deviceDataDto.getEventValue() * deviceDataDto.getTimeInMinutes()));
//                    deviceData1.setTimeWhenAppisOn(deviceDataDto.getTimeWhenAppisOn());
//                    int hour = deviceDataDto.getTimeWhenAppisOn().getHour() + i;
//                    int dayOfMonth = deviceDataDto.getTimeWhenAppisOn().getDayOfMonth();
//                    int monthOfYear = deviceDataDto.getTimeWhenAppisOn().getMonthValue();
//                    int year = deviceDataDto.getTimeWhenAppisOn().getYear();
//                    int k;
//                    int j;
//                    if (hour >= 24) {
//                        dayOfMonth = dayOfMonth + (1 * (hour / 24));
//                        hour = hour - (24 * (hour / 24));
//                        if (dayOfMonth > 31) {
//                            monthOfYear = monthOfYear + 1;
//                            dayOfMonth = dayOfMonth - (31 * (dayOfMonth / 31));
//                            hour = (hour - (24 * (hour / 24)));
//                            if (monthOfYear > 12) {
//                                year = year + 1;
//                                monthOfYear = monthOfYear - 12;
//                                dayOfMonth = dayOfMonth - (31 * (dayOfMonth / 31));
//                                hour = (hour - (24 * (hour / 24)));
////                            } else {
////                                dayOfMonth = dayOfMonth + (1 * (hour / 24));
////                                hour = (hour - (24 * (hour / 24)));
////                            }
//                            }
////                        else {
////                            dayOfMonth = dayOfMonth + (1 * (hour / 24));
////                            hour = hour - (24 * (hour / 24));
////
////                        }
//                        }
//                    }
////                        if(hour>49 && hour<49+24){
////                            if(dayOfMonth>30){
////                                monthOfYear=monthOfYear+1;
////                                if(monthOfYear>12){
////                                    year=year+1;
////                                    monthOfYear=monthOfYear-12;
////                                }
////                                else{
////                                    dayOfMonth=dayOfMonth-30;
////                                    hour=(hour-(49));
////                                }
////                            }
////                            else{
////                                dayOfMonth=dayOfMonth+1;
////                                hour=hour-(49);
////                            }
////                        }
////                        if(hour>49 && hour<49+24){
////                            if(dayOfMonth>30){
////                                monthOfYear=monthOfYear+1;
////                                if(monthOfYear>12){
////                                    year=year+1;
////                                    monthOfYear=monthOfYear-12;
////                                }
////                                else{
////                                    dayOfMonth=dayOfMonth-30;
////                                    hour=(hour-(49));
////                                }
////                            }
////                            else{
////                                dayOfMonth=dayOfMonth+1;
////                                hour=hour-(49);
////                            }
////                        }
//                    long totalminutesPerHour = deviceDataDto.getTimeInMinutes();
//                    long min = 60;
//                    long total = totalminutesPerHour;
//                    total = total - ((i + 1) * 60);
//                    if (total < 60) {
//                        min = total;
//                    }
//                    deviceData1.setTimeInMinutes(min);
//                    deviceData1.setTotalTimeInMinutes(totalminutesPerHour);
//                    deviceData1.setConsumption((deviceDataDto.getEventValue() * min));
//                    deviceData1.setPrice(deviceData1.getConsumption()*1);
//                    deviceData1.setCo2Emission(deviceData1.getConsumption()*0.5);
//                    deviceData1.setPricePerUnit(1);
//                    deviceData1.setCo2EmissionPerUnit(0.5);
//                    deviceData1.setTotalTimeInMinutes(total);
//                    deviceData1.setHour(hour);
//                    deviceData1.setDayOfMonth(dayOfMonth);
//                    deviceData1.setMonthOfYear(monthOfYear);
//                    deviceData1.setYear(year);
//                    deviceDataRepository.save(deviceData1);
//                }
//            }

            else if (deviceDataDto.getTimestamp().getYear() == deviceDataDto.getTimeWhenAppisOn().getYear() &&
                    deviceDataDto.getTimestamp().getMonth() == deviceDataDto.getTimeWhenAppisOn().getMonth() &&
                    deviceDataDto.getTimestamp().getDayOfMonth() != deviceDataDto.getTimeWhenAppisOn().getDayOfMonth()) {
                //1 day==24 hours
                //1 hour=60 minutes
                LocalDateTime time1 = deviceDataDto.getTimeWhenAppisOn();
                int minute1 = 60 - time1.getMinute();
                int hour1 = 23 - time1.getHour();
                int day1 = time1.getDayOfMonth();
                LocalDateTime time2 = LocalDateTime.now();
                int minute2 = time2.getMinute();
                int hour2 = time2.getHour();
                long totalminuteslastday = (hour2 * 60) + minute2;
                //15-01-2023 07-43-09467---16-01-2023 12-40-0000
                long totalminuteson1stday = (hour1 * 60) + minute1;
                //totalMinutes=totalMinutes+totalminuteslastday;
                DeviceData1 deviceData21 = new DeviceData1();
                deviceData21.setUser(user);
                deviceData21.setDeviceId(deviceDataDto.getDeviceId());
                deviceData21.setTimestamp(LocalDateTime.now());
                deviceData21.setEventValue(deviceDataDto.getEventValue());
                deviceData21.setApplianceName(deviceDataDto.getApplianceName());
                deviceData21.setTimeWhenAppisOn(deviceDataDto.getTimeWhenAppisOn());
                deviceData21.setTimeInMinutes(totalminuteson1stday);
                deviceData21.setConsumption((deviceDataDto.getEventValue() * totalminuteson1stday)/1000);
                deviceData21.setPrice(deviceData21.getConsumption() * 2);
                deviceData21.setCo2Emission(deviceData21.getConsumption() * 0.8);
                deviceData21.setPricePerUnit(2);
                deviceData21.setCo2EmissionPerUnit(0.8);
                deviceData21.setTotalTimeInMinutes(totalMinutes);
                deviceData21.setHour(deviceDataDto.getTimeWhenAppisOn().getHour());
                deviceData21.setDayOfMonth(deviceDataDto.getTimeWhenAppisOn().getDayOfMonth());
                deviceData21.setMonthOfYear(deviceDataDto.getTimeWhenAppisOn().getMonthValue());
                deviceData21.setYear(deviceDataDto.getTimeWhenAppisOn().getYear());
                deviceData21.setDayOfWeek(deviceDataDto.getTimeWhenAppisOn().getDayOfWeek());
                deviceData21.setMonth(deviceDataDto.getTimeWhenAppisOn().getMonth());
                deviceData1Repository.save(deviceData21);
                long totalMinutes1 = totalMinutes - totalminuteson1stday;
                long totalhours = totalMinutes1 / 60;
                long totalDays1 = totalMinutes1 / 1440;
                for (int i = 1; i <= totalDays1 + 1; i++) {
                    DeviceData1 deviceData1 = new DeviceData1();
                    deviceData1.setUser(user);
                    deviceData1.setDeviceId(deviceDataDto.getDeviceId());
                    deviceData1.setTimestamp(LocalDateTime.now());
                    deviceData1.setEventValue(deviceDataDto.getEventValue());
                    deviceData1.setApplianceName(deviceDataDto.getApplianceName());
                    deviceData1.setConsumption((deviceDataDto.getEventValue() * deviceDataDto.getTimeInMinutes()));
                    deviceData1.setTimeWhenAppisOn(deviceDataDto.getTimeWhenAppisOn());
                    int hour = deviceDataDto.getTimeWhenAppisOn().getHour();
                    int dayOfMonth = deviceDataDto.getTimeWhenAppisOn().getDayOfMonth() + i;
                    DayOfWeek dayOfWeek=deviceDataDto.getTimeWhenAppisOn().getDayOfWeek().plus(i);
                    Month month=deviceDataDto.getTimeWhenAppisOn().getMonth();
                    int monthOfYear = deviceDataDto.getTimeWhenAppisOn().getMonthValue();
                    int year = deviceDataDto.getTimeWhenAppisOn().getYear();
                    if (dayOfMonth > 31) {
                        monthOfYear = monthOfYear + 1;
                        month=month.plus(1);
                        dayOfMonth = dayOfMonth - (31 * (dayOfMonth / 31));
                        if (monthOfYear > 12) {
                            year = year + 1;
                            monthOfYear = monthOfYear - 12;
                            dayOfMonth = dayOfMonth - (31 * (dayOfMonth / 31));
                        }
                    }
                    long totalminutesPerHour = totalMinutes1;
                    long min = 60 * 24;
                    long total = totalminutesPerHour;
                    total = total - ((i - 1) * 60 * 24);
                    if (total < 60 * 24) {
                        min = total;
                    }
                    deviceData1.setTimeInMinutes(min);
                    deviceData1.setConsumption((deviceDataDto.getEventValue() * min)/1000);
                    deviceData1.setPrice(deviceData1.getConsumption() * 2);
                    deviceData1.setCo2Emission(deviceData1.getConsumption() * 0.8);
                    deviceData1.setPricePerUnit(2);
                    deviceData1.setCo2EmissionPerUnit(0.8);
                    deviceData1.setTotalTimeInMinutes(total);
                    deviceData1.setHour(hour);
                    deviceData1.setDayOfMonth(dayOfMonth);
                    deviceData1.setMonthOfYear(monthOfYear);
                    deviceData1.setYear(year);
                    deviceData1.setMonth(month);
                    deviceData1.setDayOfWeek(dayOfWeek);
                    deviceData1Repository.save(deviceData1);
                }

            }else if(deviceDataDto.getTimestamp().getYear()==deviceDataDto.getTimeWhenAppisOn().getYear()&&
                    deviceDataDto.getTimestamp().getMonth()!=deviceDataDto.getTimeWhenAppisOn().getMonth()) {

            	LocalDateTime time1=deviceDataDto.getTimeWhenAppisOn(); 
                int minute1=60-time1.getMinute();  
                int hour1=23-time1.getHour(); 
                int day1=time1.getDayOfMonth();   
                LocalDateTime time2=LocalDateTime.now(); 
                int minute2= time2.getMinute();   
                int hour2= time2.getHour();  
                long totalminuteslastday=(hour2*60)+minute2;
                //15-01-2023 07-43-09467---16-01-2023 12-40-0000  
                long totalminuteson1stday=(hour1*60)+minute1;    //totalMinutes=totalMinutes+totalminuteslastday;   
                DeviceData1 deviceData21 = new DeviceData1();   
                deviceData21.setUser(user);  
                deviceData21.setDeviceId(deviceDataDto.getDeviceId());  
                deviceData21.setTimestamp(LocalDateTime.now()); 
                deviceData21.setEventValue(deviceDataDto.getEventValue()); 
                deviceData21.setApplianceName(deviceDataDto.getApplianceName()); 
                deviceData21.setTimeWhenAppisOn(deviceDataDto.getTimeWhenAppisOn());  
                deviceData21.setTimeInMinutes(totalminuteson1stday);    
                deviceData21.setConsumption((deviceDataDto.getEventValue() * totalminuteson1stday)/1000);
                deviceData21.setPrice(deviceData21.getConsumption() * 2);  
                deviceData21.setCo2Emission(deviceData21.getConsumption() * 0.8);   
                deviceData21.setPricePerUnit(2); 
                deviceData21.setCo2EmissionPerUnit(0.8);   
                deviceData21.setTotalTimeInMinutes(totalMinutes); 
                deviceData21.setHour(deviceDataDto.getTimeWhenAppisOn().getHour());   
                deviceData21.setDayOfMonth(deviceDataDto.getTimeWhenAppisOn().getDayOfMonth());
                deviceData21.setMonthOfYear(deviceDataDto.getTimeWhenAppisOn().getMonthValue()); 
                deviceData21.setYear(deviceDataDto.getTimeWhenAppisOn().getYear());  
                deviceData21.setDayOfWeek(deviceDataDto.getTimeWhenAppisOn().getDayOfWeek());  
                deviceData21.setMonth(deviceDataDto.getTimeWhenAppisOn().getMonth());   
                deviceData1Repository.save(deviceData21);   
                long totalMinutes1=totalMinutes - totalminuteson1stday;
                long totalhours = totalMinutes1 / 60;  
                long totalDays1=totalMinutes1/1440; 
                for (int i = 1; i <= totalDays1+1; i++) 
                {        DeviceData1 deviceData1 = new DeviceData1();  
                deviceData1.setUser(user);     
                deviceData1.setDeviceId(deviceDataDto.getDeviceId());  
                deviceData1.setTimestamp(LocalDateTime.now());    
                deviceData1.setEventValue(deviceDataDto.getEventValue());
                deviceData1.setApplianceName(deviceDataDto.getApplianceName());   
                deviceData1.setConsumption((deviceDataDto.getEventValue() * deviceDataDto.getTimeInMinutes()));
                deviceData1.setTimeWhenAppisOn(deviceDataDto.getTimeWhenAppisOn());     
                int hour = deviceDataDto.getTimeWhenAppisOn().getHour();      
                int dayOfMonth = deviceDataDto.getTimeWhenAppisOn().getDayOfMonth()+i;  
                DayOfWeek dayOfWeek=deviceDataDto.getTimeWhenAppisOn().getDayOfWeek().plus(i);  
                Month month=deviceDataDto.getTimeWhenAppisOn().getMonth();     
                int monthOfYear1 = deviceDataDto.getTimeWhenAppisOn().getMonthValue();  
                int monthOfYear=monthOfYear1;      
                int year1 = deviceDataDto.getTimeWhenAppisOn().getYear();   
                int year=year1;   
                if(dayOfMonth>31){//32--11--1,60--   
                	monthOfYear=monthOfYear+(dayOfMonth/31);
                	//--31/31==1--62/31==2       
                	month=month.plus(dayOfMonth/31);  
                	dayOfMonth=dayOfMonth-(31*(dayOfMonth/31));      
                	if(monthOfYear>12)
                	{                year=year+1;          
                	monthOfYear=monthOfYear-12;    
                	dayOfMonth = dayOfMonth - (31 * (dayOfMonth / 31));
                	}        
                	}     
                int dayOfMonth2=dayOfMonth; 
                int monthOfYear2=monthOfYear;    
                int year2=year;        
                Month month2=month;   
                DayOfWeek dayOfWeek2=dayOfWeek;  
                long totalminutesPerHour = totalMinutes1;    
                long min = 60*24;      
                long total = totalminutesPerHour;    
                total = total - ((i-1) * 60*24); 
                if (total < 60*24) 
                {            min = total;        }   
                deviceData1.setTimeInMinutes(min);   
                deviceData1.setConsumption((deviceDataDto.getEventValue() * min)/1000);   
                deviceData1.setPrice(deviceData1.getConsumption()*2);    
                deviceData1.setCo2Emission(deviceData1.getConsumption()*0.8);     
                deviceData1.setPricePerUnit(2);    
                deviceData1.setCo2EmissionPerUnit(0.8);    
                deviceData1.setTotalTimeInMinutes(total);  
                deviceData1.setHour(hour);     
                deviceData1.setDayOfMonth(dayOfMonth2);   
                deviceData1.setMonthOfYear(monthOfYear2);    
                deviceData1.setYear(year2);     
                deviceData1.setMonth(month2);   
                deviceData1.setDayOfWeek(dayOfWeek2);   
                deviceData1Repository.save(deviceData1); 
                }}
            
            else if (deviceDataDto.getTimestamp().getYear()!=deviceDataDto.getTimeWhenAppisOn().getYear()) {
                //1 day==24 hours
                //1 hour=60 minutes
                LocalDateTime time1=deviceDataDto.getTimeWhenAppisOn();
                int minute1=60-time1.getMinute();
                int hour1=23-time1.getHour();
                int day1=time1.getDayOfMonth();
                LocalDateTime time2=LocalDateTime.now();
                int minute2= time2.getMinute();
                int hour2= time2.getHour();
                long totalminuteslastday=(hour2*60)+minute2;
                //15-01-2023 07-43-09467---16-01-2023 12-40-0000
                long totalminuteson1stday=(hour1*60)+minute1;
                //totalMinutes=totalMinutes+totalminuteslastday;
                DeviceData1 deviceData21 = new DeviceData1();
                deviceData21.setUser(user);
                deviceData21.setDeviceId(deviceDataDto.getDeviceId());
                deviceData21.setTimestamp(LocalDateTime.now());
                deviceData21.setEventValue(deviceDataDto.getEventValue());
                deviceData21.setApplianceName(deviceDataDto.getApplianceName());
                deviceData21.setTimeWhenAppisOn(deviceDataDto.getTimeWhenAppisOn());
                deviceData21.setTimeInMinutes(totalminuteson1stday);
                deviceData21.setConsumption((deviceDataDto.getEventValue() * totalminuteson1stday)/1000);
                deviceData21.setPrice(deviceData21.getConsumption() * 2);
                deviceData21.setCo2Emission(deviceData21.getConsumption() * 0.8);
                deviceData21.setPricePerUnit(2);
                deviceData21.setCo2EmissionPerUnit(0.8);
                deviceData21.setTotalTimeInMinutes(totalMinutes);
                deviceData21.setHour(deviceDataDto.getTimeWhenAppisOn().getHour());
                deviceData21.setDayOfMonth(deviceDataDto.getTimeWhenAppisOn().getDayOfMonth());
                deviceData21.setMonthOfYear(deviceDataDto.getTimeWhenAppisOn().getMonthValue());
                deviceData21.setYear(deviceDataDto.getTimeWhenAppisOn().getYear());
                deviceData21.setDayOfWeek(deviceDataDto.getTimeWhenAppisOn().getDayOfWeek());
                deviceData21.setMonth(deviceDataDto.getTimeWhenAppisOn().getMonth());
                deviceData1Repository.save(deviceData21);
                long totalMinutes1=totalMinutes - totalminuteson1stday;
                long totalhours = totalMinutes1 / 60;
                long totalDays1=totalMinutes1/1440;
                for (int i = 1; i <= totalDays1+1; i++) {
                    DeviceData1 deviceData1 = new DeviceData1();
                    deviceData1.setUser(user);
                    deviceData1.setDeviceId(deviceDataDto.getDeviceId());
                    deviceData1.setTimestamp(LocalDateTime.now());
                    deviceData1.setEventValue(deviceDataDto.getEventValue());
                    deviceData1.setApplianceName(deviceDataDto.getApplianceName());
                    deviceData1.setConsumption((deviceDataDto.getEventValue() * deviceDataDto.getTimeInMinutes()));
                    deviceData1.setTimeWhenAppisOn(deviceDataDto.getTimeWhenAppisOn());
                    int hour = deviceDataDto.getTimeWhenAppisOn().getHour();
                    int dayOfMonth = deviceDataDto.getTimeWhenAppisOn().getDayOfMonth()+i;
                    DayOfWeek dayOfWeek=deviceDataDto.getTimeWhenAppisOn().getDayOfWeek().plus(i);
                    Month month=deviceDataDto.getTimeWhenAppisOn().getMonth();
                    int monthOfYear1 = deviceDataDto.getTimeWhenAppisOn().getMonthValue();
                    int monthOfYear=monthOfYear1;
                    int year1 = deviceDataDto.getTimeWhenAppisOn().getYear();
                    int year=year1;
                    if(dayOfMonth>30){//32--11--1,60--
                        monthOfYear=monthOfYear+(dayOfMonth/31);//--31/31==1--62/31==2
                        month=month.plus(1);
                        dayOfMonth=dayOfMonth-(31*(dayOfMonth/31));
                        if(monthOfYear>12){
                            year=year+1;
                            monthOfYear=monthOfYear-12;
                            dayOfMonth = dayOfMonth - (31 * (dayOfMonth / 31));
                        }
                    }
                    int dayOfMonth2=dayOfMonth;
                    int monthOfYear2=monthOfYear;
                    int year2=year;
                    Month month2=month;
                    DayOfWeek dayOfWeek2=dayOfWeek;
                    long totalminutesPerHour = totalMinutes1;
                    long min = 60*24;
                    long total = totalminutesPerHour;
                    total = total - ((i-1) * 60*24);
                    if (total < 60*24) {
                        min = total;
                    }
                    deviceData1.setTimeInMinutes(min);
                    deviceData1.setConsumption((deviceDataDto.getEventValue() * min)/1000);
                    deviceData1.setPrice(deviceData1.getConsumption()*2);
                    deviceData1.setCo2Emission(deviceData1.getConsumption()*0.8);
                    deviceData1.setPricePerUnit(2);
                    deviceData1.setCo2EmissionPerUnit(0.8);
                    deviceData1.setTotalTimeInMinutes(total);
                    deviceData1.setHour(hour);
                    deviceData1.setDayOfMonth(dayOfMonth2);
                    deviceData1.setMonthOfYear(monthOfYear2);
                    deviceData1.setYear(year2);
                    deviceData1.setMonth(month2);
                    deviceData1.setDayOfWeek(dayOfWeek2);
                    deviceData1Repository.save(deviceData1);
                }
            }
          //  kafkaProducer.sendMessage(deviceDataDto);
            return ResponseEntity.ok("Json message sent to kafka topic");
        }
    }
}













//    @PostMapping("/publish")
//    public ResponseEntity<?> publish(@RequestBody DeviceDataDto deviceDataDto, Principal principal){
//        deviceDataDto.setEmailId(principal.getName());
//        User user=userRepository.findByEmailId(deviceDataDto.getEmailId()).orElseThrow(
//                ()->new UsernameNotFoundException("Email not found")
//        );
//        if(user==null){
//            return new ResponseEntity<>("user doesnt exist", HttpStatus.NOT_FOUND);
//        }
//        String s= deviceDataDto.getDeviceId();
//        String[] parts = s.split("-");
//        String s1=parts[1];
//        deviceDataDto.setDeviceId(s1);
//        //List<DeviceData> deviceData2=deviceDataRepository.findByDeviceId(s1);
//        int Consumption=0;
//        deviceDataDto.setTimestamp(LocalDateTime.now());
//        deviceDataDto.setConsumption(Consumption);
//        int time=LocalDateTime.now().getHour();
//        Appliance appliance =applianceRepository.findByDeviceDeviceId(s1);
//        Device device=deviceRepository.findByDeviceId(s1);
//        deviceDataDto.setApplianceName(appliance.getApplianceName());
//        deviceDataDto.setApplianceName(appliance.getApplianceName());
//        if(device==null){
//            return new ResponseEntity<>("There is no existing device",HttpStatus.NOT_FOUND);
//        }
//        if(appliance==null) {
//            return new ResponseEntity<>("There is no appliance with the device attached", HttpStatus.NOT_FOUND);
//        }
//       kafkaProducer.sendMessage(deviceDataDto);
//        return ResponseEntity.ok("Json message sent to kafka topic");
//    }
//}





//        if(deviceData2.size()==0)
//        {
//            DeviceData deviceData1=new DeviceData();
//            deviceData1.setUser(user);
//            deviceData1.setDeviceId(deviceDataDto.getDeviceId());
//            deviceData1.setTimestamp(LocalDateTime.now());
//            deviceData1.setEventValue(deviceDataDto.getEventValue());
//            deviceData1.setApplianceName(deviceDataDto.getApplianceName());
//            deviceData1.setConsumption(deviceDataDto.getEventValue());
//            deviceDataRepository.save(deviceData1);
//            kafkaProducer.sendMessage(deviceDataDto);
//            return ResponseEntity.ok("Json message sent to kafka topic");
//        }
//        else{
//            DeviceData oldDeviceData=deviceData2.get(deviceData2.size()-1);
//            if(oldDeviceData==null){
//                DeviceData deviceData1=new DeviceData();
//                deviceData1.setUser(user);
//                deviceData1.setDeviceId(deviceDataDto.getDeviceId());
//                deviceData1.setTimestamp(LocalDateTime.now());
//                deviceData1.setEventValue(deviceDataDto.getEventValue());
//                deviceData1.setApplianceName(deviceDataDto.getApplianceName());
//                deviceData1.setConsumption(deviceDataDto.getEventValue());
//                deviceDataRepository.save(deviceData1);
//                kafkaProducer.sendMessage(deviceDataDto);
//                return ResponseEntity.ok("Json message sent to kafka topic");
//            }
//            else{
//                if(oldDeviceData!=null){
//                    if(oldDeviceData.getTimestamp().getHour()==deviceDataDto.getTimestamp().getHour()){
//                        //DeviceData oldDeviceData=deviceData2.get(deviceData2.size()-1);
//                        Consumption=deviceDataDto.getEventValue()+ oldDeviceData.getConsumption();
//                        oldDeviceData.setConsumption(Consumption);
//                        oldDeviceData.setEventValue(deviceDataDto.getEventValue());
//                        oldDeviceData.setTimestamp(LocalDateTime.now());
//                        deviceDataRepository.save(oldDeviceData);
//                        kafkaProducer.sendMessage(deviceDataDto);
//                        return ResponseEntity.ok("Json message sent to kafka topic");
//                    }
//                    DeviceData deviceData1=new DeviceData();
//                    deviceData1.setUser(user);
//                    deviceData1.setDeviceId(deviceDataDto.getDeviceId());
//                    deviceData1.setTimestamp(LocalDateTime.now());
//                    deviceData1.setEventValue(deviceDataDto.getEventValue());
//                    deviceData1.setApplianceName(deviceDataDto.getApplianceName());
//                    deviceData1.setConsumption(deviceDataDto.getEventValue());
//                    deviceDataRepository.save(deviceData1);
//                    kafkaProducer.sendMessage(deviceDataDto);
//                    return ResponseEntity.ok("Json message sent to kafka topic");
//                }
//
//            }
//            return ResponseEntity.ok("Successfully consumed");
//        }

//        if(deviceData==null){
//            DeviceData deviceData1=new DeviceData();
//            deviceData1.setUser(user);
//            deviceData1.setDeviceId(s1);
//            deviceData1.setTimestamp(LocalDateTime.now());
//            deviceData1.setEventValue(deviceDataDto.getEventValue());
//            deviceData1.setApplianceName(appliance.getApplianceName());
//            deviceData1.setConsumption(deviceDataDto.getEventValue());
//            deviceDataRepository.save(deviceData1);
//            kafkaProducer.sendMessage(deviceDataDto);
//            return ResponseEntity.ok("Json message sent to kafka topic");
//        }
//        else{
//            if(deviceData!=null){
//                if(deviceData.getTimestamp().getHour()==deviceDataDto.getTimestamp().getHour()){
//                    DeviceData oldDeviceData=deviceData2.get(deviceData2.size()-1);
//                    Consumption=deviceDataDto.getEventValue()+ oldDeviceData.getConsumption();
//                    oldDeviceData.setConsumption(Consumption);
//                    oldDeviceData.setEventValue(deviceDataDto.getEventValue());
//                    oldDeviceData.setTimestamp(LocalDateTime.now());
//                    deviceDataRepository.save(oldDeviceData);
//                    kafkaProducer.sendMessage(deviceDataDto);
//                    return ResponseEntity.ok("Json message sent to kafka topic");
//                }
//                DeviceData deviceData1=new DeviceData();
//                deviceData1.setUser(user);
//                deviceData1.setDeviceId(s1);
//                deviceData1.setTimestamp(LocalDateTime.now());
//                deviceData1.setEventValue(deviceDataDto.getEventValue());
//                deviceData1.setApplianceName(appliance.getApplianceName());
//                deviceData1.setConsumption(deviceDataDto.getEventValue());
//                deviceDataRepository.save(deviceData1);
//                kafkaProducer.sendMessage(deviceDataDto);
//                return ResponseEntity.ok("Json message sent to kafka topic");
//            }
//        }
       // return new ResponseEntity<>("Device Data Added Successfully",HttpStatus.OK);

//            for(Device d: deviceList){
//                if(d==device){
//                    if(appliance==null){
//                        return new ResponseEntity<>("There is no device attached to appliance",HttpStatus.NOT_FOUND);
//                    }
//                    deviceDataDto.setConsumption(Consumption);
//                    deviceDataDto.setApplianceName(appliance.getApplianceName());
//                    kafkaProducer.sendMessage(deviceDataDto);
//                    return ResponseEntity.ok("Json message sent to kafka topic");
//                }
//            }
//            return new ResponseEntity<>("There is not device present in the database",HttpStatus.NOT_FOUND);
//        } else if (deviceData.getTimestamp().getHour()-LocalDateTime.now().getHour()<=1) {
//            Consumption=Consumption+ deviceDataDto.getEventValue();
//            deviceDataDto.setConsumption(Consumption);
//
//        }
//        kafkaProducer.sendMessage(deviceDataDto);
//        return ResponseEntity.ok("Json message sent to kafka topic");
    