package com.zrj.coolweather.gson;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by a on 2017/3/31.
 */

public class Weather {
    public String status;

    public Basic basic;

    public AQI aqi;

    public Now now;

    public  Suggestion suggestion;

    @SerializedName("daily_forecast")
    public List<DialyForecast> dialyForecasts;
}

