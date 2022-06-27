package com.app.pinpotha_beta.util;

import android.os.Build;



import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

public class TimeData {

    public static String getEpochTime(Long epoch){
        String fin_Time="";
        LocalDateTime ldt = Instant.ofEpochMilli(epoch)
                .atZone(ZoneId.systemDefault()).toLocalDateTime();
        System.out.println(ldt);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEE, MMM dd, yyyy hh:mm:ss a");
        fin_Time = ldt.format(formatter);

        return fin_Time;
            }

            public static String getToday(){
                LocalDateTime myDateobj = LocalDateTime.now();
                DateTimeFormatter myformatobj = DateTimeFormatter.ofPattern("dd-MM-yyyy");

                String timeStamp = myDateobj.format(myformatobj);
                return timeStamp;
            }

    public static String getGreeting(String user,String morning,String afternoon,String evening,String night){
        String greeting="";
        String[] name=user.split(" ");
        Date date = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int hour = cal.get(Calendar.HOUR_OF_DAY);
        if(hour>= 12 && hour < 17){
            greeting = afternoon+" "+name[0];
        } else if(hour >= 16 && hour < 21){
            greeting = evening+" "+name[0];
        } else if(hour >= 21 && hour < 24){
            greeting = night+" "+name[0];
        } else {
            greeting = morning+" "+name[0];
        }
        return greeting;
    }
}
