package com.zrj.coolweather.gson;

import com.google.gson.annotations.SerializedName;

/**
 * Created by a on 2017/3/31.
 */

public class Basic {
    @SerializedName("city")
    public String cityName;

    @SerializedName("id")
    public String weatherId;

    public Update update;

    public class Update{
        @SerializedName("loc")
        public String updateTime;
    }
}
