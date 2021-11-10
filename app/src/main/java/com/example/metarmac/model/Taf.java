package com.example.metarmac.model;

import static com.example.metarmac.XMLReader.convertStringToXMLDocument;

import android.util.Log;
import android.widget.Toast;

import org.jetbrains.annotations.NotNull;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.NodeList;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class Taf {

    private boolean containData;

    private String raw_text;
    private String station_id;
    private String issue_time;
    private String bulletin_time;
    private String valid_time_from;
    private String valid_time_to;
    private String remarks;
    private float latitude;
    private float longitude;
    private float elevaton_m;

    private ArrayList<Forecast> lstForecast;

    public Taf(String oaci) {

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("https://aviationweather.gov/adds/dataserver_current/httpparam?dataSource=tafs&requestType=retrieve&format=xml&stationString=" + oaci + "&hoursBeforeNow=24&mostRecent=true")
                .build();


        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                if (!response.isSuccessful()) {
                    throw new IOException("Unexpected code " + response);
                }
                final String responseData = response.body().string();

                Document doc = convertStringToXMLDocument(responseData);

                if(doc.getChildNodes().item(0).getChildNodes().item(13).getAttributes().item(0).getTextContent().equals("0")) {
                    containData = false;
                }
                else {
                    containData = true;

                    lstForecast = new ArrayList<>();

                    NodeList dataNodeList = doc.getChildNodes().item(0).getChildNodes().item(13).getChildNodes().item(1).getChildNodes();

                    for(int i=0; i<dataNodeList.getLength(); i++) {
                        if(dataNodeList.item(i).getNodeName().equals("raw_text"))
                            raw_text = dataNodeList.item(i).getTextContent();

                        else if(dataNodeList.item(i).getNodeName().equals("station_id"))
                            station_id = dataNodeList.item(i).getTextContent();

                        else if(dataNodeList.item(i).getNodeName().equals("issue_time"))
                            issue_time = dataNodeList.item(i).getTextContent();

                        else if(dataNodeList.item(i).getNodeName().equals("bulletin_time"))
                            bulletin_time = dataNodeList.item(i).getTextContent();

                        else if(dataNodeList.item(i).getNodeName().equals("valid_time_from"))
                            valid_time_from = dataNodeList.item(i).getTextContent();

                        else if(dataNodeList.item(i).getNodeName().equals("valid_time_to"))
                            valid_time_to = dataNodeList.item(i).getTextContent();

                        else if(dataNodeList.item(i).getNodeName().equals("remarks"))
                            remarks = dataNodeList.item(i).getTextContent();

                        else if(dataNodeList.item(i).getNodeName().equals("latitude"))
                            latitude = Float.parseFloat(dataNodeList.item(i).getTextContent());

                        else if(dataNodeList.item(i).getNodeName().equals("longitude"))
                            longitude = Float.parseFloat(dataNodeList.item(i).getTextContent());

                        else if(dataNodeList.item(i).getNodeName().equals("elevaton_m"))
                            elevaton_m = Float.parseFloat(dataNodeList.item(i).getTextContent());

                        else if(dataNodeList.item(i).getNodeName().equals("forecast"))
                            lstForecast.add(new Forecast(dataNodeList.item(i).getChildNodes()));

                    }
                }
            }
        });
    }

    public boolean doesContainData() {
        return containData;
    }

    public String getRaw_text() {
        return raw_text;
    }

    public String getStation_id() {
        return station_id;
    }

    public String getIssue_time() {
        return issue_time;
    }

    public String getBulletin_time() {
        return bulletin_time;
    }

    public String getValid_time_from() {
        return valid_time_from;
    }

    public String getValid_time_to() {
        return valid_time_to;
    }

    public String getRemarks() {
        return remarks;
    }

    public float getLatitude() {
        return latitude;
    }

    public float getLongitude() {
        return longitude;
    }

    public float getElevaton_m() {
        return elevaton_m;
    }

    public ArrayList<Forecast> getLstForecast() {
        return lstForecast;
    }
}


