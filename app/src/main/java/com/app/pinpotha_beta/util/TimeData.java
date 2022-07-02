package com.app.pinpotha_beta.util;

import android.util.Log;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

public class TimeData {

    public static String getEpochTime(Long epoch) {
        String fin_Time = "";
        LocalDateTime ldt = Instant.ofEpochMilli(epoch)
                .atZone(ZoneId.systemDefault()).toLocalDateTime();
        System.out.println(ldt);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEE, MMM dd, yyyy hh:mm:ss a");
        fin_Time = ldt.format(formatter);

        return fin_Time;
    }

    public static String getToday() {
        LocalDateTime myDateobj = LocalDateTime.now();
        DateTimeFormatter myformatobj = DateTimeFormatter.ofPattern("dd-MM-yyyy");

        String timeStamp = myDateobj.format(myformatobj);
        return timeStamp;
    }

    public static String getGreeting(String user, String morning, String afternoon, String evening, String night) {
        String greeting = "";
        String[] name = user.split(" ");
        Date date = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int hour = cal.get(Calendar.HOUR_OF_DAY);
        if (hour >= 12 && hour < 17) {
            greeting = afternoon + " " + name[0];
        } else if (hour >= 17 && hour < 24) {
            greeting = evening + " " + name[0];
        } else {
            greeting = morning + " " + name[0];
        }
        return greeting;
    }

    public static boolean CompareTwoDates(String StartDate, String EndDate) throws ParseException {
        SimpleDateFormat sdformat = new SimpleDateFormat("dd-MM-yyyy");
        Date d1 = sdformat.parse(StartDate);
        Date d2 = sdformat.parse(EndDate);
        Log.d("search","Inside StartDate:"+StartDate+",EndDate:"+EndDate);
        if(d1.compareTo(d2) > 0) {
            //Date 1 occurs after Date 2"

            return false;
        } else //Both dates are equal
            if(d1.compareTo(d2) < 0) {
            //Date 1 occurs before Date 2"

            return true;
        } else return d1.compareTo(d2) == 0;
    }

    public static Long conDateDB(String date){
        String[] uiDate = date.split("-");
        String rtnDate=uiDate[2]+uiDate[1]+uiDate[0];
        Log.d("rtnDate","rtnDate:"+rtnDate);
        return Long.parseLong(rtnDate);
    }

    public static String conUiDate(Long date){
        String dbDate = String.valueOf(date);
        String rtnDate=dbDate.substring(6,8)
                +"-" +
                dbDate.substring(4,6)
                +"-" +
                dbDate.substring(0,4);
        Log.d("rtnDate","rtnDate:"+rtnDate);
        return rtnDate;
    }

}
