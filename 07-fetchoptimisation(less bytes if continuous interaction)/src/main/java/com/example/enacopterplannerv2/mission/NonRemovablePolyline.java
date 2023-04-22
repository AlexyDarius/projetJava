package com.example.enacopterplannerv2.mission;

import org.osmdroid.views.overlay.Polyline;

/**
 * Cette classe permet de définir une Polyline spécifique qui ne sera pas retirée par le garbage
 * collector. La classe est notamment utilisée pour les tracés.
 * @author alexyroman
 */
public class NonRemovablePolyline extends Polyline {

    public NonRemovablePolyline() {
    }

}
