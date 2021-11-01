package com.example.metarmac.model;

import static com.example.metarmac.XMLReader.convertStringToXMLDocument;

import android.os.Handler;
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

public class Metar {

    private boolean containData;

    private String raw_text;
    private String station_id;
    private String observation_time;
    private float latitude;
    private float longitude;
    private float temp_c;
    private float dewpoint_c;
    private int wind_dir_degrees;
    private int wind_speed_kt;
    private int wind_gust_kt;
    private float visibility_statute_mi;
    private float altim_in_hg;
    private float sea_level_pressure_mb;
    private String quality_control_flags;
    private String wx_string;

    private ArrayList<Sky_condition> sky_condition;

    private String flight_category;
    private float three_hr_pressure_tendency_mb;
    private float maxT_c;
    private float minT_c;
    private float maxT24hr_c;
    private float minT24hr_c;
    private float precip_in;
    private float pcp3hr_in;
    private float pcp6hr_in;
    private float pcp24hr_in;
    private float snow_in;
    private int vert_vis_ft;
    private String metar_type;
    private float elevation_m;


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

                //Log.d("Réponse METAR", responseData);

                Document doc = convertStringToXMLDocument(responseData);

                if(doc.getChildNodes().item(0).getChildNodes().item(13).getAttributes().item(0).getTextContent().equals("0")) {
                    containData = false;
                }
                else {
                    containData = true;

                    NodeList dataNodeList = doc.getChildNodes().item(0).getChildNodes().item(13).getChildNodes().item(1).getChildNodes();

                    //raw_text = dataNodeList.item(1).getTextContent();
                    //Log.d("<metar>", raw_text);

                    sky_condition = new ArrayList<>();

                    for(int i=0; i<dataNodeList.getLength(); i++) {
                        if(dataNodeList.item(i).getNodeName().equals("raw_text"))
                            raw_text = dataNodeList.item(i).getTextContent();

                        else if(dataNodeList.item(i).getNodeName().equals("station_id"))
                            station_id = dataNodeList.item(i).getTextContent();


                        if(dataNodeList.item(i).getNodeName().equals("observation_time"))
                            observation_time = dataNodeList.item(i).getTextContent();

                        else if(dataNodeList.item(i).getNodeName().equals("latitude"))
                            latitude = Float.parseFloat(dataNodeList.item(i).getTextContent());

                        else if(dataNodeList.item(i).getNodeName().equals("longitude"))
                            longitude = Float.parseFloat(dataNodeList.item(i).getTextContent());

                        else if(dataNodeList.item(i).getNodeName().equals("temp_c"))
                            temp_c = Float.parseFloat(dataNodeList.item(i).getTextContent());

                        else if(dataNodeList.item(i).getNodeName().equals("dewpoint_c"))
                            dewpoint_c = Float.parseFloat(dataNodeList.item(i).getTextContent());

                        else if(dataNodeList.item(i).getNodeName().equals("wind_dir_degrees"))
                            wind_dir_degrees = Integer.parseInt(dataNodeList.item(i).getTextContent());

                        else if(dataNodeList.item(i).getNodeName().equals("wind_speed_kt"))
                            wind_speed_kt = Integer.parseInt(dataNodeList.item(i).getTextContent());

                        else if(dataNodeList.item(i).getNodeName().equals("wind_gust_kt"))
                            wind_gust_kt = Integer.parseInt(dataNodeList.item(i).getTextContent());

                        else if(dataNodeList.item(i).getNodeName().equals("visibility_statute_mi"))
                            visibility_statute_mi = Float.parseFloat(dataNodeList.item(i).getTextContent());

                        else if(dataNodeList.item(i).getNodeName().equals("altim_in_hg"))
                            altim_in_hg = Float.parseFloat(dataNodeList.item(i).getTextContent());

                        else if(dataNodeList.item(i).getNodeName().equals("sea_level_pressure_mb"))
                            sea_level_pressure_mb = Float.parseFloat(dataNodeList.item(i).getTextContent());

                        /*
                        else if(dataNodeList.item(i).getNodeName().equals("quality_control_flags"))
                            quality_control_flags = dataNodeList.item(i).getTextContent();
                        //A CHECK PAR RAPPORT AU SITE
                        */

                        else if(dataNodeList.item(i).getNodeName().equals("wx_string"))
                            wx_string = dataNodeList.item(i).getTextContent();

                        else if(dataNodeList.item(i).getNodeName().equals("sky_condition"))
                            sky_condition.add(new Sky_condition(dataNodeList.item(i).getAttributes()));

                        else if(dataNodeList.item(i).getNodeName().equals("flight_category"))
                            flight_category = dataNodeList.item(i).getTextContent();

                        else if(dataNodeList.item(i).getNodeName().equals("three_hr_pressure_tendency_mb"))
                            three_hr_pressure_tendency_mb = Float.parseFloat(dataNodeList.item(i).getTextContent());

                        else if(dataNodeList.item(i).getNodeName().equals("maxT_c"))
                            maxT_c = Float.parseFloat(dataNodeList.item(i).getTextContent());

                        else if(dataNodeList.item(i).getNodeName().equals("minT_c"))
                            minT_c = Float.parseFloat(dataNodeList.item(i).getTextContent());

                        else if(dataNodeList.item(i).getNodeName().equals("maxT24hr_c"))
                            maxT24hr_c = Float.parseFloat(dataNodeList.item(i).getTextContent());

                        else if(dataNodeList.item(i).getNodeName().equals("minT24hr_c"))
                            minT24hr_c = Float.parseFloat(dataNodeList.item(i).getTextContent());

                        else if(dataNodeList.item(i).getNodeName().equals("precip_in"))
                            precip_in = Float.parseFloat(dataNodeList.item(i).getTextContent());

                        else if(dataNodeList.item(i).getNodeName().equals("pcp3hr_in"))
                            pcp3hr_in = Float.parseFloat(dataNodeList.item(i).getTextContent());

                        else if(dataNodeList.item(i).getNodeName().equals("pcp6hr_in"))
                            pcp6hr_in = Float.parseFloat(dataNodeList.item(i).getTextContent());

                        else if(dataNodeList.item(i).getNodeName().equals("pcp24hr_in"))
                            pcp24hr_in = Float.parseFloat(dataNodeList.item(i).getTextContent());

                        else if(dataNodeList.item(i).getNodeName().equals("snow_in"))
                            snow_in = Float.parseFloat(dataNodeList.item(i).getTextContent());

                        else if(dataNodeList.item(i).getNodeName().equals("vert_vis_ft"))
                            vert_vis_ft = Integer.parseInt(dataNodeList.item(i).getTextContent());

                        else if(dataNodeList.item(i).getNodeName().equals("metar_type"))
                            metar_type = dataNodeList.item(i).getTextContent();

                        else if(dataNodeList.item(i).getNodeName().equals("elevation_m"))
                            elevation_m = Float.parseFloat(dataNodeList.item(i).getTextContent());

                    }
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

    public int getWind_gust_kt() {
        return wind_gust_kt;
    }

    public float getVisibility_statute_mi() {
        return visibility_statute_mi;
    }

    public float getAltim_in_hg() {
        return altim_in_hg;
    }

    public float getSea_level_pressure_mb() {
        return sea_level_pressure_mb;
    }

    public String getQuality_control_flags() {
        return quality_control_flags;
    }

    public String getWx_string() {
        return wx_string;
    }

    public ArrayList<Sky_condition> getSky_condition() {
        return sky_condition;
    }

    public String getFlight_category() {
        return flight_category;
    }

    public float getThree_hr_pressure_tendency_mb() {
        return three_hr_pressure_tendency_mb;
    }

    public float getMaxT_c() {
        return maxT_c;
    }

    public float getMinT_c() {
        return minT_c;
    }

    public float getMaxT24hr_c() {
        return maxT24hr_c;
    }

    public float getMinT24hr_c() {
        return minT24hr_c;
    }

    public float getPrecip_in() {
        return precip_in;
    }

    public float getPcp3hr_in() {
        return pcp3hr_in;
    }

    public float getPcp6hr_in() {
        return pcp6hr_in;
    }

    public float getPcp24hr_in() {
        return pcp24hr_in;
    }

    public float getSnow_in() {
        return snow_in;
    }

    public int getVert_vis_ft() {
        return vert_vis_ft;
    }

    public String getMetar_type() {
        return metar_type;
    }

    public float getElevation_m() {
        return elevation_m;
    }
}















