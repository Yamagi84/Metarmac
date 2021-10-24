package com.example.metarmac;

import android.app.Application;

import com.example.metarmac.model.Airport;

import java.util.ArrayList;

public class GlobalApplication extends Application {

    private ArrayList<Airport> lstAirport = new ArrayList<>();

    public ArrayList<Airport> getLstAirport() {
        return lstAirport;
    }
}
