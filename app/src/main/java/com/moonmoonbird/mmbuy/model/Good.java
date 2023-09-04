package com.moonmoonbird.mmbuy.model;

public class Good extends Base{
    private String Name;
    private String Descripton;
    private String Url;

    public Good(){

    }

    public Good(String name, String description, String url){
        Name = name;
        Descripton = description;
        Url = url;
    }

    public String getName(){
        return Name;
    }

    public String getDescripton() {
        return Descripton;
    }

    public String getUrl() {
        return Url;
    }

    public void setName(String name) {
        Name = name;
    }

    public void setDescripton(String descripton) {
        Descripton = descripton;
    }

    public void setUrl(String url) {
        Url = url;
    }
}
