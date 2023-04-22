package com.example.enacopterplannerv2.map;

import org.osmdroid.api.IMapController;
import org.osmdroid.util.BoundingBox;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;


/**
 * Cette classe définit les réglages intiaux de la carte comme le point de départ, le zoom initial,
 * ou les contraintes de zoom
 * @author alexyroman
 */
public class MapSetter {

    /**
     * @param mapView carte osmdroid
     */
    public MapSetter(MapView mapView) {

        //Réglages de la carte
        mapView.setHorizontalMapRepetitionEnabled(true);
        mapView.setVerticalMapRepetitionEnabled(false);
        mapView.setScrollableAreaLimitLatitude(MapView.getTileSystem().getMaxLatitude(), MapView.getTileSystem().getMinLatitude(), 0);
        mapView.setMinZoomLevel(8.);
        mapView.setExpectedCenter(new GeoPoint(45., 0.));
        mapView.setScrollableAreaLimitDouble(new BoundingBox(51.9014, 10.4944, 40.6045, -5.8762));


        //Initialisation de la vue de départ
        IMapController mapController = mapView.getController();
        mapController.setZoom(12.5);
        GeoPoint startPoint = new GeoPoint(43.5983, 1.4744);
        mapController.setCenter(startPoint);

    }

}
