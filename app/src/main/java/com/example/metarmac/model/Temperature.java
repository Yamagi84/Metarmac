package com.example.metarmac.model;

import org.w3c.dom.NodeList;

public class Temperature {

    private String valid_time;
    private float sfc_temp_c;
    private float max_temp_c;
    private float min_temp_c;

    public Temperature(NodeList dataNodeList) {

        for (int i = 0; i < dataNodeList.getLength(); i++) {

            if (dataNodeList.item(i).getNodeName().equals("valid_time"))
                valid_time = dataNodeList.item(i).getTextContent();

            else if (dataNodeList.item(i).getNodeName().equals("sfc_temp_c"))
                sfc_temp_c = Float.parseFloat(dataNodeList.item(i).getTextContent());

            else if (dataNodeList.item(i).getNodeName().equals("max_temp_c"))
                max_temp_c = Float.parseFloat(dataNodeList.item(i).getTextContent());

            else if (dataNodeList.item(i).getNodeName().equals("min_temp_c"))
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
