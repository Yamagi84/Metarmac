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
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
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

class Forecast {

    private String fcst_time_from;
    private String fcst_time_to;
    private String change_indicator;
    private String time_becoming;
    private int probability;
    private int wind_dir_degrees;
    private int wind_speed_kt;
    private int wind_gust_kt;
    private int wind_shear_hgt_ft_agl;
    private int wind_shear_dir_degrees;
    private int wind_shear_speed_kt;
    private float visibility_statute_mi;
    private float altim_in_hg;
    private int vert_vis_ft;
    private String wx_string;
    private String not_decoded;

    private ArrayList<Sky_condition> sky_condition;
    private ArrayList<Turbulence_condition> turbulence_condition;
    private ArrayList<Icing_condition> icing_condition;

    private Temperature temperature;

    public Forecast(NodeList dataNodeList) {

        sky_condition = new ArrayList<>();

        for(int i=0; i<dataNodeList.getLength(); i++) {

            if(dataNodeList.item(i).getNodeName().equals("fcst_time_from"))
                fcst_time_from = dataNodeList.item(i).getTextContent();

            else if(dataNodeList.item(i).getNodeName().equals("fcst_time_to"))
                fcst_time_to = dataNodeList.item(i).getTextContent();

            else if(dataNodeList.item(i).getNodeName().equals("change_indicator"))
                change_indicator = dataNodeList.item(i).getTextContent();

            else if(dataNodeList.item(i).getNodeName().equals("time_becoming"))
                time_becoming = dataNodeList.item(i).getTextContent();

            else if(dataNodeList.item(i).getNodeName().equals("probability"))
                probability = Integer.parseInt(dataNodeList.item(i).getTextContent());

            else if(dataNodeList.item(i).getNodeName().equals("wind_dir_degrees"))
                wind_dir_degrees = Integer.parseInt(dataNodeList.item(i).getTextContent());

            else if(dataNodeList.item(i).getNodeName().equals("wind_speed_kt"))
                wind_speed_kt = Integer.parseInt(dataNodeList.item(i).getTextContent());

            else if(dataNodeList.item(i).getNodeName().equals("wind_gust_kt"))
                wind_gust_kt = Integer.parseInt(dataNodeList.item(i).getTextContent());

            else if(dataNodeList.item(i).getNodeName().equals("wind_shear_hgt_ft_agl"))
                wind_shear_hgt_ft_agl = Integer.parseInt(dataNodeList.item(i).getTextContent());

            else if(dataNodeList.item(i).getNodeName().equals("wind_shear_dir_degrees"))
                wind_shear_dir_degrees = Integer.parseInt(dataNodeList.item(i).getTextContent());

            else if(dataNodeList.item(i).getNodeName().equals("wind_shear_speed_kt"))
                wind_shear_speed_kt = Integer.parseInt(dataNodeList.item(i).getTextContent());

            else if(dataNodeList.item(i).getNodeName().equals("visibility_statute_mi"))
                visibility_statute_mi = Float.parseFloat(dataNodeList.item(i).getTextContent());

            else if(dataNodeList.item(i).getNodeName().equals("altim_in_hg"))
                altim_in_hg = Float.parseFloat(dataNodeList.item(i).getTextContent());

            else if(dataNodeList.item(i).getNodeName().equals("vert_vis_ft"))
                vert_vis_ft = Integer.parseInt(dataNodeList.item(i).getTextContent());

            else if(dataNodeList.item(i).getNodeName().equals("wx_string"))
                wx_string = dataNodeList.item(i).getTextContent();

            else if(dataNodeList.item(i).getNodeName().equals("not_decoded"))
                not_decoded = dataNodeList.item(i).getTextContent();

            else if(dataNodeList.item(i).getNodeName().equals("sky_condition"))
                sky_condition.add(new Sky_condition(dataNodeList.item(i).getAttributes()));

            else if(dataNodeList.item(i).getNodeName().equals("turbulence_condition"))
                turbulence_condition.add(new Turbulence_condition(dataNodeList.item(i).getAttributes()));

            else if(dataNodeList.item(i).getNodeName().equals("icing_condition"))
                icing_condition.add(new Icing_condition(dataNodeList.item(i).getAttributes()));

            else if(dataNodeList.item(i).getNodeName().equals("temperature"))
                temperature = new Temperature(dataNodeList.item(i).getChildNodes());
        }
    }

    public String getFcst_time_from() {
        return fcst_time_from;
    }

    public String getFcst_time_to() {
        return fcst_time_to;
    }

    public String getChange_indicator() {
        return change_indicator;
    }

    public String getTime_becoming() {
        return time_becoming;
    }

    public int getProbability() {
        return probability;
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

    public int getWind_shear_hgt_ft_agl() {
        return wind_shear_hgt_ft_agl;
    }

    public int getWind_shear_dir_degrees() {
        return wind_shear_dir_degrees;
    }

    public int getWind_shear_speed_kt() {
        return wind_shear_speed_kt;
    }

    public float getVisibility_statute_mi() {
        return visibility_statute_mi;
    }

    public float getAltim_in_hg() {
        return altim_in_hg;
    }

    public int getVert_vis_ft() {
        return vert_vis_ft;
    }

    public String getWx_string() {
        return wx_string;
    }

    public String getNot_decoded() {
        return not_decoded;
    }

    public ArrayList<Sky_condition> getSky_condition() {
        return sky_condition;
    }

    public ArrayList<Turbulence_condition> getTurbulence_condition() {
        return turbulence_condition;
    }

    public ArrayList<Icing_condition> getIcing_condition() {
        return icing_condition;
    }

    public Temperature getTemperature() {
        return temperature;
    }
}



class Turbulence_condition {

    private String turbulence_intensity ;
    private int turbulence_min_alt_ft_agl ;
    private int turbulence_max_alt_ft_agl  ;

    public Turbulence_condition(NamedNodeMap attributes) {

        for(int j=0; j<attributes.getLength(); j++) {
            if (attributes.item(j).getNodeName().equals("turbulence_intensity"))
                turbulence_intensity = attributes.item(j).getTextContent();

            else if (attributes.item(j).getNodeName().equals("turbulence_min_alt_ft_agl"))
                turbulence_min_alt_ft_agl = Integer.parseInt(attributes.item(j).getTextContent());

            else if (attributes.item(j).getNodeName().equals("turbulence_max_alt_ft_agl"))
                turbulence_max_alt_ft_agl = Integer.parseInt(attributes.item(j).getTextContent());
        }
    }
}

class Icing_condition {

    private String icing_intensity;
    private int icing_min_alt_ft_agl;
    private int icing_max_alt_ft_agl;

    public Icing_condition(NamedNodeMap attributes) {

        for(int j=0; j<attributes.getLength(); j++) {
            if (attributes.item(j).getNodeName().equals("icing_intensity"))
                icing_intensity = attributes.item(j).getTextContent();

            else if (attributes.item(j).getNodeName().equals("icing_min_alt_ft_agl"))
                icing_min_alt_ft_agl = Integer.parseInt(attributes.item(j).getTextContent());

            else if (attributes.item(j).getNodeName().equals("icing_max_alt_ft_agl"))
                icing_max_alt_ft_agl = Integer.parseInt(attributes.item(j).getTextContent());
        }
    }
}

class Temperature {

    private String valid_time;
    private float sfc_temp_c;
    private float max_temp_c;
    private float min_temp_c;

    public Temperature(NodeList dataNodeList) {

        for (int i = 0; i < dataNodeList.getLength(); i++) {

            if(dataNodeList.item(i).getNodeName().equals("valid_time"))
                valid_time = dataNodeList.item(i).getTextContent();

            else if(dataNodeList.item(i).getNodeName().equals("sfc_temp_c"))
                sfc_temp_c = Float.parseFloat(dataNodeList.item(i).getTextContent());

            else if(dataNodeList.item(i).getNodeName().equals("max_temp_c"))
                max_temp_c = Float.parseFloat(dataNodeList.item(i).getTextContent());

            else if(dataNodeList.item(i).getNodeName().equals("min_temp_c"))
                min_temp_c = Float.parseFloat(dataNodeList.item(i).getTextContent());
        }
    }

    public String getValid_time() {
        return valid_time;
    }

    public float getSfc_temp_c() {
        return sfc_temp_c;
    }

    public float getMax_temp_c() {
        return max_temp_c;
    }

    public float getMin_temp_c() {
        return min_temp_c;
    }
}