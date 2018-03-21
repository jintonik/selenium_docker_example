package com.buggy.base;

public class Parameters
{
    private static final Parameters instance = new Parameters();

    private final static String SITE_URL = System.getProperty("target.url");

    //Test data
    private static final String LOGIN_EMAIL = "anton.sherpaev@gmail.com";

    private static final String LOGIN_PASSWORD = "1q2w3e";

    public static Parameters getInstance()
    {
        return instance;
    }

    public static String getSiteUrl()
    {
        return SITE_URL;
    }

    public static String getLoginEmail()
    {
        return LOGIN_EMAIL;
    }

    public static String getLoginPassword()
    {
        return LOGIN_PASSWORD;
    }
}
