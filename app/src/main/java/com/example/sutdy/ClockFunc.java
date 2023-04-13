package com.example.sutdy;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ClockFunc {
    public static String getCurrentDateTime() {
        Date currentDate = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        return dateFormat.format(currentDate);

    }
}
