package com.example.enacopterplannerv2.data;

import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.overlay.Polygon;

import java.util.List;

public class NamedPolygon extends Polygon {

    private final String name;

    public NamedPolygon(List<GeoPoint> geoPoints, String name) {
        this.name = name;
        this.setPoints(geoPoints);
    }

    public String getName() {
        return name;
    }

}
