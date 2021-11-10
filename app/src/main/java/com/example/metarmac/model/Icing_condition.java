package com.example.metarmac.model;

import org.w3c.dom.NamedNodeMap;

public class Icing_condition {

    private String icing_intensity;
    private int icing_min_alt_ft_agl;
    private int icing_max_alt_ft_agl;

    public Icing_condition(NamedNodeMap attributes) {

        for (int j = 0; j < attributes.getLength(); j++) {
            if (attributes.item(j).getNodeName().equals("icing_intensity"))
                icing_intensity = attributes.item(j).getTextContent();

            else if (attributes.item(j).getNodeName().equals("icing_min_alt_ft_agl"))
                icing_min_alt_ft_agl = Integer.parseInt(attributes.item(j).getTextContent());

            else if (attributes.item(j).getNodeName().equals("icing_max_alt_ft_agl"))
                icing_max_alt_ft_agl = Integer.parseInt(attributes.item(j).getTextContent());
        }
    }

    public String getIcing_intensity() {
        return icing_intensity;
    }

    public int getIcing_min_alt_ft_agl() {
        return icing_min_alt_ft_agl;
    }

    public int getIcing_max_alt_ft_agl() {
        return icing_max_alt_ft_agl;
    }
}
