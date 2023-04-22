package com.example.enacopterplannerv2.mission;

import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.Marker;

/**
 * Cette classe permet de définir un Marker spécifique qui ne sera pas retiré par le garbage
 * collector. La classe est notamment utilisée pour les marqueurs de conflits.
 * @author alexyroman
 */
public class NonRemovableMarker extends Marker {

    /**
     * @param mapView carte osmdroid
     */
    public NonRemovableMarker(MapView mapView) {
        super(mapView);
    }
}
