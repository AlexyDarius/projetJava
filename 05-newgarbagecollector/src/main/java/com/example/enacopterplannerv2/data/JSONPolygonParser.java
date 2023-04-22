package com.example.enacopterplannerv2.data;

import org.osmdroid.util.GeoPoint;

import java.util.ArrayList;
import java.util.List;

public class JSONPolygonParser {

    private final List<NamedPolygon> namedPolygons = new ArrayList<>();

    public List<NamedPolygon> parse(String data) {

        //Split des lignes
        String[] parts = data.split("\"features\":\\[");
        String[] features = parts[1].split("\\},\\{");

        //Parcours des lignes
        for (String feature : features) {

            //Regexp pour parser l'id
            String name = feature.split("\"toponyme\":\"")[1].split("\"")[0];

            //Initialisation de la liste de GeoPoint
            ArrayList<GeoPoint> geoPoints = new ArrayList<>();
            String[] coordinates = feature.split("\"coordinates\":\\[\\[\\[\\[")[1].split("]]")[0].split("],\\[");

            //Parcours des coordonnées et ajout à la liste de GeoPoint
            for (String coordinate : coordinates) {
                double lon = Double.parseDouble(coordinate.split(",")[0]);
                double lat = Double.parseDouble(coordinate.split(",")[1]);
                GeoPoint geoPoint = new GeoPoint(lat, lon);
                geoPoints.add(geoPoint);
            }

            //Ajout de la ligne à la liste de Polyline
            namedPolygons.add(new NamedPolygon(geoPoints, name));

        }

        return namedPolygons;
    }

}
