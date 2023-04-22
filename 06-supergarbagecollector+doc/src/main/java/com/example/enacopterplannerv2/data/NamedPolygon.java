package com.example.enacopterplannerv2.data;

import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.overlay.Polygon;

import java.util.List;

/**
 * Cette classe permet de définir un Polygon nommé qui possède également un nom
 * @author alexyroman
 */
public class NamedPolygon extends Polygon {

    private final String name;

    /**
     * @param geoPoints liste de GeoPoint
     * @param name nom du Polygon
     */
    public NamedPolygon(List<GeoPoint> geoPoints, String name) {
        this.name = name;
        this.setPoints(geoPoints);
    }

    /**
     * Cette méthide retourne le nom de la Polyline
     * @return nom de la Polyline
     */
    public String getName() {
        return name;
    }

}
