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

                //Log.d("Réponse TAF", responseData);

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

                        else if(dataNodeList.item(i).getNodeName().equals("latitude"))
                            latitude = Float.parseFloat(dataNodeList.item(i).getTextContent());

                        else if(dataNodeList.item(i).getNodeName().equals("longitude"))
                            longitude = Float.parseFloat(dataNodeList.item(i).getTextContent());

                        else if(dataNodeList.item(i).getNodeName().equals("elevaton_m"))
                            elevaton_m = Float.parseFloat(dataNodeList.item(i).getTextContent());

                        else if(dataNodeList.item(i).getNodeName().equals("forecast"))
                            lstForecast.add(new Forecast(dataNodeList.item(i).getChildNodes()));

                    }

                    Log.d("<Taf>", "LstForecast Size : " + lstForecast.size());

                    /*
                    raw_text = dataNodeList.item(1).getTextContent();
                    station_id = dataNodeList.item(3).getTextContent();
                    issue_time = dataNodeList.item(5).getTextContent();
                    bulletin_time = dataNodeList.item(7).getTextContent();
                    valid_time_from = dataNodeList.item(9).getTextContent();
                    valid_time_to = dataNodeList.item(11).getTextContent();
                    latitude = Float.parseFloat(dataNodeList.item(13).getTextContent());
                    longitude = Float.parseFloat(dataNodeList.item(15).getTextContent());
                    elevaton_m = Float.parseFloat(dataNodeList.item(17).getTextContent());
                    */

                    //Log.d("<TAF>", "Size : " + dataNodeList.getLength());
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
    private String change_indicator;
    private int wind_dir_degrees;
    private int wind_speed_kt;
    private int wind_gust_kt;
    private float visibility_statute_mi;
    private String wx_string;
    private String not_decoded;

    private String sky_condition__sky_cover;
    private int sky_condition__cloud_base_ft_agl;
    private String sky_condition__cloud_type;


    public Forecast(NodeList dataNodeList) { // A Compléter

        for(int i=0; i<dataNodeList.getLength(); i++) {

            if(dataNodeList.item(i).getNodeName().equals("fcst_time_from"))
                fcst_time_from = dataNodeList.item(i).getTextContent();

            else if(dataNodeList.item(i).getNodeName().equals("fcst_time_to"))
                fcst_time_to = dataNodeList.item(i).getTextContent();

            else if(dataNodeList.item(i).getNodeName().equals("change_indicator"))
                change_indicator = dataNodeList.item(i).getTextContent();

            else if(dataNodeList.item(i).getNodeName().equals("wind_dir_degrees"))
                wind_dir_degrees = Integer.parseInt(dataNodeList.item(i).getTextContent());

            else if(dataNodeList.item(i).getNodeName().equals("wind_speed_kt"))
                wind_speed_kt = Integer.parseInt(dataNodeList.item(i).getTextContent());

            else if(dataNodeList.item(i).getNodeName().equals("wind_gust_kt"))
                wind_gust_kt = Integer.parseInt(dataNodeList.item(i).getTextContent());

            else if(dataNodeList.item(i).getNodeName().equals("visibility_statute_mi"))
                visibility_statute_mi = Float.parseFloat(dataNodeList.item(i).getTextContent());

            else if(dataNodeList.item(i).getNodeName().equals("wx_string"))
                wx_string = dataNodeList.item(i).getTextContent();

            else if(dataNodeList.item(i).getNodeName().equals("not_decoded"))
                not_decoded = dataNodeList.item(i).getTextContent();

            else if(dataNodeList.item(i).getNodeName().equals("sky_condition")) {
                //Log.d("Forecast", dataNodeList.item(i).getAttributes().item(0).getNodeName());

                NamedNodeMap sky_condition__attributes = dataNodeList.item(i).getAttributes();

                for(int j=0; j<sky_condition__attributes.getLength(); j++) {
                    if (sky_condition__attributes.item(j).getNodeName().equals("sky_cover"))
                        sky_condition__sky_cover = sky_condition__attributes.item(j).getTextContent();

                    else if (sky_condition__attributes.item(j).getNodeName().equals("cloud_base_ft_agl"))
                        sky_condition__cloud_base_ft_agl = Integer.parseInt(sky_condition__attributes.item(j).getTextContent());

                    else if (sky_condition__attributes.item(j).getNodeName().equals("cloud_type"))
                        sky_condition__cloud_type = sky_condition__attributes.item(j).getTextContent();
                }
            }


        }
    }


}
