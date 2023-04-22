package com.example.enacopterplannerv2.data;

import java.util.ArrayList;
import java.util.List;

/**
 * Cette classe permet de définir un parser qui construit la liste des NamedGeoPoint depuis la
 * chaîne de caractères récuperée suite à la requête au serveur.
 * @author alexyroman
 * @author quentinmirambell
 */
public class JSONPointParser implements IParser{

    private final List<NamedGeoPoint> namedGeoPoints = new ArrayList<>();

    /**
     * @param data donnée récuperées depuis le serveur
     * @return namedGeoPointList la liste de GeoPoint avec un nom
     */
    public List<NamedGeoPoint> parse(String data) {

        int startIndex = data.indexOf("\"coordinates\":");

        while (startIndex != -1) {

            //Regexp pour parser les cooronnées
            startIndex += 14;
            int endIndex = data.indexOf("]", startIndex);
            String coordString = data.substring(startIndex, endIndex + 1);
            coordString = coordString.replace("[", "");
            coordString = coordString.replace("]", "");
            String[] coords = coordString.split(",");
            double latitude = Double.parseDouble(coords[1]);
            double longitude = Double.parseDouble(coords[0]);

            //Regexp pour parser la nature du point
            startIndex = data.indexOf("\"nature\":\"", endIndex);
            startIndex += 10;
            endIndex = data.indexOf("\"", startIndex);
            String nature = data.substring(startIndex, endIndex);
            startIndex = data.indexOf("\"coordinates\":", endIndex);

            //Construction de la liste de NamedGeoPoint
            NamedGeoPoint namedGeoPoint = new NamedGeoPoint(latitude, longitude , nature);
            namedGeoPoints.add(namedGeoPoint);
        }

        return namedGeoPoints;
    }

}
