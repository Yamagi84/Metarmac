package com.example.metarmac.model;

import org.w3c.dom.NodeList;

import java.util.Locale;

public class Airport {

    private String station_id;
    private String wmo_id;
    private float latitude;
    private float longitude;
    private float elevation_m;
    private String site;
    private String state;
    private String country;
    private String site_type;

    private Metar metar;
    private Taf taf;

    public Airport(NodeList data) {

        for(int i=0; i<data.getLength(); i++) {
            if(data.item(i).getNodeName().equals("station_id"))
                this.station_id = data.item(i).getTextContent();

            else if(data.item(i).getNodeName().equals("wmo_id"))
                this.wmo_id = data.item(i).getTextContent();

            else if (data.item(i).getNodeName().equals("latitude"))
                this.latitude = Float.parseFloat(data.item(i).getTextContent());

            else if (data.item(i).getNodeName().equals("longitude"))
                this.longitude = Float.parseFloat(data.item(i).getTextContent());

            else if (data.item(i).getNodeName().equals("elevation_m"))
                this.elevation_m = Float.parseFloat(data.item(i).getTextContent());

            else if(data.item(i).getNodeName().equals("site"))
                this.site = data.item(i).getTextContent();

            else if(data.item(i).getNodeName().equals("state"))
                this.state = data.item(i).getTextContent();

            else if (data.item(i).getNodeName().equals("country"))
                this.country = data.item(i).getTextContent();

            else if(data.item(i).getNodeName().equals("site_type"))
                this.site_type = data.item(i).getTextContent();
        }

        this.metar = new Metar(this.station_id);
        this.taf = new Taf(this.station_id);

    }

    public String getStation_id() {
        return station_id;
    }

    public String getWmo_id() {
        return wmo_id;
    }

    public float getLatitude() {
        return latitude;
    }

    public float getLongitude() {
        return longitude;
    }

    public float getElevation_m() {
        return elevation_m;
    }

    public String getSite() {
        return site;
    }

    public String getState() {
        return state;
    }

    public String getCountry() {
        return country;
    }

    public String getSite_type() {
        return site_type;
    }

    public Metar getMetar() {
        return metar;
    }

    public Taf getTaf() {
        return taf;
    }
}
