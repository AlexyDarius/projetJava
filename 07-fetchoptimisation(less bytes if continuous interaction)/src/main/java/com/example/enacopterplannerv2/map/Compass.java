package com.example.enacopterplannerv2.map;

import android.content.Context;
import android.graphics.Canvas;
import android.widget.Button;

import org.osmdroid.views.MapView;
import org.osmdroid.views.Projection;
import org.osmdroid.views.overlay.compass.CompassOverlay;

/**
 * Cette classe initialise une boussole sur la carte avec une interaction permettant de ré-orienter
 * la carte vers le nord
 * @author tamaitidelorme
 */

public class Compass {

    /**
     * @param mapView carte osmdroid
     * @param ctx context de l'application
     * @param compassButton bouton pour ré-orienter la carte
     */
    public Compass(MapView mapView, Context ctx, Button compassButton) {

        //Initialisation de la boussole et dessin sur la carte
        CompassOverlay mCompassOverlay = new CompassOverlay(ctx, mapView) {
            @Override
            public void draw(Canvas c, Projection pProjection) {
                drawCompass(c, -mapView.getMapOrientation(), pProjection.getScreenRect());
            }
        };

        //Initialisation de la boussole
        mCompassOverlay.enableCompass();
        mCompassOverlay.setPointerMode(false);
        mCompassOverlay.setEnabled(true);
        mapView.getOverlays().add(mCompassOverlay);

        //Définition de l'interaction "ré-orienter la carte"
        compassButton.setOnClickListener(v -> mapView.setMapOrientation(0f));

    }

}
