package com.zrj.coolweather.gson;

import com.google.gson.annotations.SerializedName;

/**
 * Created by a on 2017/3/31.
 */

public class DialyForecast {
    public String date;

    @SerializedName("cond")
    public Condition condition;

    @SerializedName("tmp")
    public Temperature temperature;

    public class Condition{
        @SerializedName("txt_d")
        public String conditionInfo;
    }

    public class Temperature{
        @SerializedName("max")
        public String maxTemp;

        @SerializedName("min")
        public String minTemp;
    }
}

