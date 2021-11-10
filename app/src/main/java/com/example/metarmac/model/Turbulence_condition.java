package com.example.metarmac.model;

import org.w3c.dom.NamedNodeMap;

public class Turbulence_condition {

    private String turbulence_intensity;
    private int turbulence_min_alt_ft_agl;
    private int turbulence_max_alt_ft_agl;

    public Turbulence_condition(NamedNodeMap attributes) {

        for (int j = 0; j < attributes.getLength(); j++) {
            if (attributes.item(j).getNodeName().equals("turbulence_intensity"))
                turbulence_intensity = attributes.item(j).getTextContent();

            else if (attributes.item(j).getNodeName().equals("turbulence_min_alt_ft_agl"))
                turbulence_min_alt_ft_agl = Integer.parseInt(attributes.item(j).getTextContent());

            else if (attributes.item(j).getNodeName().equals("turbulence_max_alt_ft_agl"))
                turbulence_max_alt_ft_agl = Integer.parseInt(attributes.item(j).getTextContent());
        }
    }

    public String getTurbulence_intensity() {
        return turbulence_intensity;
    }

    public int getTurbulence_min_alt_ft_agl() {
        return turbulence_min_alt_ft_agl;
    }

    public int getTurbulence_max_alt_ft_agl() {
        return turbulence_max_alt_ft_agl;
    }
}
