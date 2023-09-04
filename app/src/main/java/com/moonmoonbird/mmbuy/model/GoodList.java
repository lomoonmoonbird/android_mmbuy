package com.moonmoonbird.mmbuy.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;


public class GoodList extends Base{
    public ArrayList<GoodData> data = new ArrayList<>();


    public static class GoodData {
        @SerializedName("name")
        public String Name;
        @SerializedName("description")
        public String Description;
        @SerializedName("url")
        public String Url;
    }
}
