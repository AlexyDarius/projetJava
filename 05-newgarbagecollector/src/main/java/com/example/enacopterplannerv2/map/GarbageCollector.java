package com.example.enacopterplannerv2.map;

import android.os.Handler;

import com.example.enacopterplannerv2.drawing.NonRemovableMarker;
import com.example.enacopterplannerv2.drawing.NonRemovablePolyline;

import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.Marker;
import org.osmdroid.views.overlay.Overlay;
import org.osmdroid.views.overlay.Polygon;
import org.osmdroid.views.overlay.Polyline;

import java.util.List;

public class GarbageCollector {

    private MapView mapView;
    private final Handler garbageHandler = new Handler();

    public GarbageCollector(MapView mapView) {
        this.mapView = mapView;
    }

    /**
     * Définit un programme qui lance un garbage collector
     */
    private final Runnable garbageAction = new Runnable() {
        /**
         * Garbage collector basé sur un nombre maximum d'objets affichés
         */
        @Override
        public void run() {

            //Initialisation des listes et des seuils
            List<Overlay> overlays = mapView.getOverlays();
            int maxPolylines = 150;
            int maxMarkers = 50;
            int polylineCount = 0;
            int markerCount = 0;

            //Empecher superpoisiton de polygons
            for(Overlay overlay : overlays) {
                if (overlay instanceof Polygon) {
                    mapView.getOverlayManager().remove(overlay);
                }
            }

            //Compatge du nombre de chaque type d'overlay
            for (Overlay overlay : overlays) {
                if (overlay instanceof Polyline && !(overlay instanceof NonRemovablePolyline)) {
                    polylineCount ++;
                } else if (overlay instanceof Marker && !(overlay instanceof NonRemovableMarker)) {
                    markerCount++;
                }
            }

            //Nettoyage des polylines
            if (polylineCount > maxPolylines) {
                int numToDelete = polylineCount - maxPolylines+30;
                int cpt = 0;
                for (Overlay overlay : overlays) {
                    if (overlay instanceof Polyline && !(overlay instanceof NonRemovablePolyline)) {
                        mapView.getOverlayManager().remove(overlay);
                        cpt++;
                        if(cpt >= numToDelete){
                            break;
                        }
                    }
                }
            }

            //nettoyage des marqueurs
            if (markerCount > maxMarkers) {
                int numToDelete = markerCount - maxMarkers+10;
                int cpt = 0;
                for (Overlay overlay : overlays) {
                    if (overlay instanceof Marker && !(overlay instanceof NonRemovableMarker)) {
                        mapView.getOverlayManager().remove(overlay);
                        cpt++;
                        if(cpt >= numToDelete){
                            break;
                        }
                    }
                }
            }

        }
    };

    /**
     * Cette méthode permet de lancer le garbage collector chaque seconde
     */
    public void collect(){

        garbageHandler.removeCallbacks(garbageAction);
        garbageHandler.postDelayed(garbageAction, 100);

    }

}
