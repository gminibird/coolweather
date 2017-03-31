package com.zrj.coolweather.gson;

import com.google.gson.annotations.SerializedName;

/**
 * Created by a on 2017/3/31.
 */

public class AQI {

    public AQICity city;

    public class AQICity{
        public String aqi;
        public String pm25;
    }
}
