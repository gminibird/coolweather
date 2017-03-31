package com.zrj.coolweather.gson;

import com.google.gson.annotations.SerializedName;

/**
 * Created by a on 2017/3/31.
 */

public class Suggestion {

    @SerializedName("comf")
    public Comfort comfort;

    @SerializedName("cw")
    public CarWash carWash;

    public Sport sport;

    public class Comfort {
        @SerializedName("txt")
        public String comfortInfo;
    }

    public class CarWash {
        @SerializedName("txt")
        public String carWashInfo;
    }

    public class Sport{
        @SerializedName("txt")
        public String sportInfo;
    }
}
