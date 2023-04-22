package com.example.enacopterplannerv2.data;

import org.osmdroid.util.GeoPoint;

/**
 * Cette classe permet de définir un GeoPoint nommé qui possède également une nature (pylône, antenne...)
 * @author alexyroman
 * @author quentinmirambell
 * @author clementvuillaume
 */
public class NamedGeoPoint extends GeoPoint {
    private final String nature;

    /**
     * @param latitude latitude du point
     * @param longitude longitude du point
     * @param nature nature du point
     */
    public NamedGeoPoint(double latitude, double longitude, String nature) {
        super(latitude, longitude);
        this.nature = nature;
    }

    /**
     * Cette méthide retourne la nature du point (pylône, antenne...)
     * @return nature du point
     */
    public String getNature() {
        return nature;
    }

}