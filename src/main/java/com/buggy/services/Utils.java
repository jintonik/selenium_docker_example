package com.buggy.services;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Utils
{
    public static final int SCREEN_LOAD_TIMEOUT = 30;

    public static final String TIME_STAMP = new SimpleDateFormat("yyyyMMdd_HHmmss").format(
            Calendar.getInstance().getTime());

}
