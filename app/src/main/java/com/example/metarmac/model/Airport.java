package com.example.metarmac.model;

public class Airport {

    private String name;
    private String oaci;

    private Metar metar;
    private Taf taf;

    public Airport(String name, String oaci) {
        this.name = name;
        this.oaci = oaci;

        this.metar = new Metar(this.oaci);
        this.taf = new Taf(this.oaci);
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
