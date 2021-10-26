package com.example.metarmac.model;

import org.w3c.dom.NodeList;

import java.util.Locale;

public class Airport {

    private String name;
    private String oaci;
    private String country;

    private float latitude;
    private float longitude;

    private Metar metar;
    private Taf taf;

    public Airport(String name, String oaci) {
        this.name = name;
        this.oaci = oaci;

        this.metar = new Metar(this.oaci);
        this.taf = new Taf(this.oaci);
    }

    public Airport(NodeList data) {

        this.oaci = data.item(1).getTextContent().toUpperCase(Locale.ROOT);

        for(int i=0; i<data.getLength(); i++) {
            if(data.item(i).getNodeName().equals("station_id"))
                this.oaci = data.item(i).getTextContent();  //.toUpperCase(Locale.ROOT)

            else if(data.item(i).getNodeName().equals("site"))
                this.name = data.item(i).getTextContent();

            else if (data.item(i).getNodeName().equals("country"))
                this.country = data.item(i).getTextContent();

            else if (data.item(i).getNodeName().equals("latitude"))
                this.latitude = Float.parseFloat(data.item(i).getTextContent());

            else if (data.item(i).getNodeName().equals("longitude"))
                this.longitude = Float.parseFloat(data.item(i).getTextContent());
        }

        this.metar = new Metar(this.oaci);
        this.taf = new Taf(this.oaci);

    }


    public String getName() {
        return name;
    }

    public String getOaci() {
        return oaci;
    }

    public String getCountry() {
        return country;
    }

    public float getLatitude() {
        return latitude;
    }

    public float getLongitude() {
        return longitude;
    }

    public Metar getMetar() {
        return metar;
    }

    public Taf getTaf() {
        return taf;
    }
}
