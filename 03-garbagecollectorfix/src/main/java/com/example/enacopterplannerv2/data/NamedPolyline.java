package com.example.enacopterplannerv2.data;

import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.overlay.Polyline;

import java.util.List;

/**
 * Cette classe permet de définir une Polyline nommée qui possède également un id
 * @author alexyroman
 */
public class NamedPolyline extends Polyline {

    private final String id;

    /**
     * @param geoPoints liste de GeoPoint
     * @param id id de la Polyline
     */
    public NamedPolyline(List<GeoPoint> geoPoints, String id) {
        this.id = id;
        this.setPoints(geoPoints);
    }

    /**
     * Cette méthide retourne l'id de la Polyline
     * @return id de la Polyline
     */
    @Override
    public String getId() {
        return id;
    }

}
