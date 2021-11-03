package com.example.metarmac.model;

import org.w3c.dom.NamedNodeMap;

public class Sky_condition { // Used in Metar.java and Taf.java

    private String sky_cover;
    private int cloud_base_ft_agl;
    private String cloud_type;

    public Sky_condition(NamedNodeMap sky_condition__attributes) {

        for(int j=0; j<sky_condition__attributes.getLength(); j++) {
            if (sky_condition__attributes.item(j).getNodeName().equals("sky_cover"))
                sky_cover = sky_condition__attributes.item(j).getTextContent();

            else if (sky_condition__attributes.item(j).getNodeName().equals("cloud_base_ft_agl"))
                cloud_base_ft_agl = Integer.parseInt(sky_condition__attributes.item(j).getTextContent());

            else if (sky_condition__attributes.item(j).getNodeName().equals("cloud_type"))
                cloud_type = sky_condition__attributes.item(j).getTextContent();
        }
    }
}
