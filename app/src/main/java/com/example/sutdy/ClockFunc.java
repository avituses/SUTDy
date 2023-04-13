package com.example.sutdy;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ClockFunc {
    public static int getCurrentDateTime() {
        Date currentDate = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        int currentDateInt = Integer.parseInt(dateFormat.format(currentDate));
        return currentDateInt;

    }
}
