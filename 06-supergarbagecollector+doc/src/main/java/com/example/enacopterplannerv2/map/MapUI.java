package com.example.enacopterplannerv2.map;

import android.content.Context;
import android.util.DisplayMetrics;

import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.Overlay;
import org.osmdroid.views.overlay.ScaleBarOverlay;
import org.osmdroid.views.overlay.gestures.RotationGestureOverlay;

/**
 * Cette classe initialise les interactions de bases avec la carte ainsi que certains composants
 * comme l'échelle
 * @author alexyroman
 */

public class MapUI {

    /**
     * @param mapView carte osmdroid
     * @param ctx context de l'application
     */
    public MapUI(MapView mapView, Context ctx) {

        //GESTURES
        Overlay mRotationGestureOverlay = new RotationGestureOverlay(mapView);
        mRotationGestureOverlay.setEnabled(true);
        mapView.setMultiTouchControls(true);
        mapView.getOverlays().add(mRotationGestureOverlay);

        //SCALEBAR
        final DisplayMetrics dm = ctx.getResources().getDisplayMetrics();
        ScaleBarOverlay mScaleBarOverlay = new ScaleBarOverlay(mapView);
        mScaleBarOverlay.setCentred(true);
        mScaleBarOverlay.setScaleBarOffset(dm.widthPixels / 2, 10);
        mapView.getOverlays().add(mScaleBarOverlay);

    }

}
