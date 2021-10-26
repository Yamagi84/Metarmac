package com.example.metarmac.model;

import static com.example.metarmac.XMLReader.convertStringToXMLDocument;

import android.util.Log;
import android.widget.Toast;

import org.jetbrains.annotations.NotNull;
import org.w3c.dom.Document;
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
    private float latitude;
    private float longitude;
    private float elevaton_m;

    private ArrayList<Forecast> lstForecast; // A instancier

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
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException { //VERIFICATION DU NOMBRE DE RESULTATS > 0 SINON AFFICHER ERREUR AU LIEU DE CRASH L'APP
                if (!response.isSuccessful()) {
                    throw new IOException("Unexpected code " + response);
                }
                final String responseData = response.body().string();

                Log.d("Réponse TAF", responseData);

                Document doc = convertStringToXMLDocument(responseData);

                if(doc.getChildNodes().item(0).getChildNodes().item(13).getAttributes().item(0).getTextContent().equals("0")) {
                    containData = false;
                }
                else {
                    containData = true;

                    NodeList dataNodeList = doc.getChildNodes().item(0).getChildNodes().item(13).getChildNodes().item(1).getChildNodes();

                    raw_text = dataNodeList.item(1).getTextContent();
                    station_id = dataNodeList.item(3).getTextContent();
                    issue_time = dataNodeList.item(5).getTextContent();
                    bulletin_time = dataNodeList.item(7).getTextContent();
                    valid_time_from = dataNodeList.item(9).getTextContent();
                    valid_time_to = dataNodeList.item(11).getTextContent();
                    latitude = Float.parseFloat(dataNodeList.item(13).getTextContent());
                    longitude = Float.parseFloat(dataNodeList.item(15).getTextContent());
                    elevaton_m = Float.parseFloat(dataNodeList.item(17).getTextContent());
                }

            }
        });

    }

    public boolean isContainData() {
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

    public float getLatitude() {
        return latitude;
    }

    public float getLongitude() {
        return longitude;
    }

    public float getElevaton_m() {
        return elevaton_m;
    }

    public ArrayList<Forecast> getLstForecast() { //Inutilisable pour le moment
        return lstForecast;
    }
}

class Forecast { // A faire plus tard

    private String fcst_time_from;
    private String fcst_time_to;
    private int wind_dir_degrees;
    private int wind_speed_kt;
    private float visibility_statute_mi;


    public Forecast(NodeList lstData) { // A Compléter

    }


}
