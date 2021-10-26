package com.example.metarmac.model;

import static com.example.metarmac.XMLReader.convertStringToXMLDocument;

import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import org.jetbrains.annotations.NotNull;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class Metar {

    private boolean containData;

    private String raw_text;
    private String observation_time;
    private float latitude;
    private float longitude;
    private float temp_c;
    private float dewpoint_c;
    private int wind_dir_degrees;
    private int wind_speed_kt;
    private float visibility_statute_mi;
    private float altim_in_hg;


    public Metar(String oaci) {

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("https://aviationweather.gov/adds/dataserver_current/httpparam?dataSource=metars&requestType=retrieve&format=xml&stationString=" + oaci + "&hoursBeforeNow=24&mostRecent=true")
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

                Log.d("Réponse METAR", responseData);

                Document doc = convertStringToXMLDocument(responseData);

                if(doc.getChildNodes().item(0).getChildNodes().item(13).getAttributes().item(0).getTextContent().equals("0")) {
                    containData = false;
                }
                else {

                    NodeList dataNodeList = doc.getChildNodes().item(0).getChildNodes().item(13).getChildNodes().item(1).getChildNodes();

                    raw_text = dataNodeList.item(1).getTextContent();
                    Log.d("<metar>", raw_text);

                    observation_time = dataNodeList.item(5).getTextContent();
                    latitude = Float.parseFloat(dataNodeList.item(7).getTextContent());
                    longitude = Float.parseFloat(dataNodeList.item(9).getTextContent());
                    temp_c = Float.parseFloat(dataNodeList.item(11).getTextContent());
                    dewpoint_c = Float.parseFloat(dataNodeList.item(13).getTextContent());
                    wind_dir_degrees = Integer.parseInt(dataNodeList.item(15).getTextContent());
                    wind_speed_kt = Integer.parseInt(dataNodeList.item(17).getTextContent());
                    visibility_statute_mi = Float.parseFloat(dataNodeList.item(19).getTextContent());
                    altim_in_hg = Float.parseFloat(dataNodeList.item(21).getTextContent());

                    containData = true;

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

    public String getObservation_time() {
        return observation_time;
    }

    public float getLatitude() {
        return latitude;
    }

    public float getLongitude() {
        return longitude;
    }

    public float getTemp_c() {
        return temp_c;
    }

    public float getDewpoint_c() {
        return dewpoint_c;
    }

    public int getWind_dir_degrees() {
        return wind_dir_degrees;
    }

    public int getWind_speed_kt() {
        return wind_speed_kt;
    }

    public float getVisibility_statute_mi() {
        return visibility_statute_mi;
    }

    public float getAltim_in_hg() {
        return altim_in_hg;
    }
}
