package com.example.metarmac.model;

import org.w3c.dom.NodeList;

import java.util.ArrayList;

public class Forecast {

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

        for (int i = 0; i < dataNodeList.getLength(); i++) {

            if (dataNodeList.item(i).getNodeName().equals("fcst_time_from"))
                fcst_time_from = dataNodeList.item(i).getTextContent();

            else if (dataNodeList.item(i).getNodeName().equals("fcst_time_to"))
                fcst_time_to = dataNodeList.item(i).getTextContent();

            else if (dataNodeList.item(i).getNodeName().equals("change_indicator"))
                change_indicator = dataNodeList.item(i).getTextContent();

            else if (dataNodeList.item(i).getNodeName().equals("time_becoming"))
                time_becoming = dataNodeList.item(i).getTextContent();

            else if (dataNodeList.item(i).getNodeName().equals("probability"))
                probability = Integer.parseInt(dataNodeList.item(i).getTextContent());

            else if (dataNodeList.item(i).getNodeName().equals("wind_dir_degrees"))
                wind_dir_degrees = Integer.parseInt(dataNodeList.item(i).getTextContent());

            else if (dataNodeList.item(i).getNodeName().equals("wind_speed_kt"))
                wind_speed_kt = Integer.parseInt(dataNodeList.item(i).getTextContent());

            else if (dataNodeList.item(i).getNodeName().equals("wind_gust_kt"))
                wind_gust_kt = Integer.parseInt(dataNodeList.item(i).getTextContent());

            else if (dataNodeList.item(i).getNodeName().equals("wind_shear_hgt_ft_agl"))
                wind_shear_hgt_ft_agl = Integer.parseInt(dataNodeList.item(i).getTextContent());

            else if (dataNodeList.item(i).getNodeName().equals("wind_shear_dir_degrees"))
                wind_shear_dir_degrees = Integer.parseInt(dataNodeList.item(i).getTextContent());

            else if (dataNodeList.item(i).getNodeName().equals("wind_shear_speed_kt"))
                wind_shear_speed_kt = Integer.parseInt(dataNodeList.item(i).getTextContent());

            else if (dataNodeList.item(i).getNodeName().equals("visibility_statute_mi"))
                visibility_statute_mi = Float.parseFloat(dataNodeList.item(i).getTextContent());

            else if (dataNodeList.item(i).getNodeName().equals("altim_in_hg"))
                altim_in_hg = Float.parseFloat(dataNodeList.item(i).getTextContent());

            else if (dataNodeList.item(i).getNodeName().equals("vert_vis_ft"))
                vert_vis_ft = Integer.parseInt(dataNodeList.item(i).getTextContent());

            else if (dataNodeList.item(i).getNodeName().equals("wx_string"))
                wx_string = dataNodeList.item(i).getTextContent();

            else if (dataNodeList.item(i).getNodeName().equals("not_decoded"))
                not_decoded = dataNodeList.item(i).getTextContent();

            else if (dataNodeList.item(i).getNodeName().equals("sky_condition"))
                sky_condition.add(new Sky_condition(dataNodeList.item(i).getAttributes()));

            else if (dataNodeList.item(i).getNodeName().equals("turbulence_condition"))
                turbulence_condition.add(new Turbulence_condition(dataNodeList.item(i).getAttributes()));

            else if (dataNodeList.item(i).getNodeName().equals("icing_condition"))
                icing_condition.add(new Icing_condition(dataNodeList.item(i).getAttributes()));

            else if (dataNodeList.item(i).getNodeName().equals("temperature"))
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
