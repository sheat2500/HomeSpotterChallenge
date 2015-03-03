package com.lesmtech.homespotterchallenge.Model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Te on 2/16/15.
 */
public class WeatherInfoModel{

    public String name;
    public String id;
    public Main main;
    public List<Weather> weather;
    public Sys sys;

    public class Main {
        public String temp;
        public String humidity;
        public String pressure;
        public String temp_min;
        public String temp_max;
    }

    public class Weather {
        public String id;
        public String main;
        public String description;
        public String icon;
    }

    public class Sys {
        public String type;
        public String id;
        public String message;
        public String country;
        public String sunrise;
        public String sunset;
    }

}
