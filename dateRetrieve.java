package com.example.budgetsoftware;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class dateRetrieve {


    public String getCurrentDay()
    {
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("E, dd MMM yyyy HH:mm:ss z");
        String strDate = formatter.format(date);
        strDate = strDate.substring(0,16);
        return strDate;
    }

    public String[] getCurrentWeek()
    //String[]
    { //Returns week of any given day

        String[] arr = new String[8];
        String strDate;



        Calendar cal = Calendar.getInstance();


        //cal.setTime(new Date(fullDate.substring(fullDate.indexOf(","))));//Set specific Date if you want to
        // cal.setTime(new Date(2021 - 1900,11,19));

        for(int i = Calendar.SUNDAY; i <= Calendar.SATURDAY; i++) {
            cal.set(Calendar.DAY_OF_WEEK, i);
            strDate = String.valueOf(cal.getTime());//Returns Date
            //System.out.println(strDate);
            String dayOfWeek = strDate.substring(0,3);
            String month = strDate.substring(4,7);
            String dayOfMonth = strDate.substring(8,11);
            String year = strDate.substring(24,28);


            String templateString = dayOfWeek + ", " + dayOfMonth + month + " " + year;

            //System.out.println(dayOfWeek);
            //System.out.println(month);
            //System.out.println(dayOfMonth);
            //System.out.println(year);
            //System.out.println(templateString);



            arr[i] = templateString;
        }

        return arr;
    }
}
