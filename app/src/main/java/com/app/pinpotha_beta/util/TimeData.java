package com.app.pinpotha_beta.util;

import android.os.Build;



import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class TimeData {

    public static String getEpochTime(Long epoch){
        String fin_Time="";
        LocalDateTime ldt = Instant.ofEpochMilli(epoch)
                .atZone(ZoneId.systemDefault()).toLocalDateTime();
        System.out.println(ldt);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy hh:mm:ss a");
        fin_Time = ldt.format(formatter);

        return fin_Time;
            }

            public static String getToday(){
                LocalDateTime myDateobj = LocalDateTime.now();
                DateTimeFormatter myformatobj = DateTimeFormatter.ofPattern("dd-MM-yyyy");

                String timeStamp = myDateobj.format(myformatobj);
                return timeStamp;
            }


}
