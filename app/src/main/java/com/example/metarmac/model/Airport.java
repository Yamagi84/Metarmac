package com.example.metarmac.model;

public class Airport {

    private String name;
    private String oaci;

    public Airport(String name, String oaci) {
        this.name = name;
        this.oaci = oaci;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOaci() {
        return oaci;
    }

    public void setOaci(String oaci) {
        this.oaci = oaci;
    }
}
