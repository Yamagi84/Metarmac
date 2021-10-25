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
        this.name = data.item(9).getTextContent(); //Check pour récupérer le bon nom (boucle for pr trouver la bonne balise ?)
        this.oaci = data.item(1).getTextContent().toUpperCase(Locale.ROOT);
        this.country = data.item(11).getTextContent();

        this.latitude = Float.parseFloat(data.item(3).getTextContent());
        this.longitude = Float.parseFloat(data.item(5).getTextContent());

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
