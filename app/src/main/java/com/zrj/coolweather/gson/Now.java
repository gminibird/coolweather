package com.zrj.coolweather.gson;

import com.google.gson.annotations.SerializedName;

/**
 * Created by a on 2017/3/31.
 */

public class Now {

    @SerializedName("tmp")
    public String temperature;

    @SerializedName("cond")
    public Condition condition;

    public class Condition{
        @SerializedName("txt")
        public String conditionInfo;
    }
}
