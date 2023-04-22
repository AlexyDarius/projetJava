package com.example.enacopterplannerv2.map;

import android.os.Handler;

import com.example.enacopterplannerv2.drawing.NonRemovableMarker;
import com.example.enacopterplannerv2.drawing.NonRemovablePolyline;

import org.osmdroid.util.BoundingBox;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.Marker;
import org.osmdroid.views.overlay.Overlay;
import org.osmdroid.views.overlay.Polygon;
import org.osmdroid.views.overlay.Polyline;

import java.util.ArrayList;
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
            int maxPolylines = 250;
            int maxMarkers = 100;
            List<Polyline> polylines = new ArrayList<>();
            List<Marker> markers = new ArrayList<>();

            //Remplissage des listes avec les objets présents sur la carte
            for (int i = 0; i < overlays.size(); i++) {
                if (overlays.get(i) instanceof Polyline) {
                    polylines.add((Polyline) overlays.get(i));
                } else if (overlays.get(i) instanceof Marker) {
                    markers.add((Marker) overlays.get(i));
                }
            }

            //Nettoyage des polylines
            if (polylines.size() > maxPolylines) {
                int numToDelete = polylines.size() - maxPolylines;
                for (int i = 0; i < numToDelete; i++) {
                    if (!(polylines.get(i) instanceof NonRemovablePolyline)) {
                        overlays.remove(polylines.get(i));
                    }
                }
            }

            //nettoyage des marqueurs
            if (markers.size() > maxMarkers) {
                int numToDelete = markers.size() - maxMarkers;
                for (int i = 0; i < numToDelete; i++) {
                    overlays.remove(markers.get(i));
                }
            }

        }
    };

    private final Runnable garbageAction2 = new Runnable() {
        /**
         * Garbage collector basé sur un nombre maximum d'objets affichés
         */
        @Override
        public void run() {

            //Initialisation des listes et des seuils
            List<Overlay> overlays = mapView.getOverlays();
//            int polylinesCount = 0;
//            int  markersCount = 0;
            int maxPolylines = 150;
            int maxMarkers = 50;
            List<Polyline> polylines = new ArrayList<>();
            List<Marker> markers = new ArrayList<>();

            //Remplissage des listes avec les objets présents sur la carte
            for (int i = 0; i < overlays.size(); i++) {
                if (overlays.get(i) instanceof Polyline ) {
                    polylines.add((Polyline) overlays.get(i));
//                    polylinesCount++;
                } else if (overlays.get(i) instanceof Marker) {
                    markers.add((Marker) overlays.get(i));
//                    markersCount++;
                }
            }

            //Nettoyage des polylines
            if (polylines.size() > maxPolylines) {
                int numToDelete = polylines.size() - maxPolylines;
                for (int i = 0; i < numToDelete;) {
                    if (!(polylines.get(i) instanceof NonRemovablePolyline)) {
                        i++;
                        overlays.remove(polylines.get(i));
                    }
                }
            }

            //nettoyage des marqueurs
            if (markers.size() > maxMarkers) {
                int numToDelete = markers.size() - maxMarkers;
                for (int i = 0; i < numToDelete;) {
                    if (!(markers.get(i) instanceof NonRemovableMarker)) {
                        overlays.remove(markers.get(i));
                        i++;
                    }
                }
            }

        }
    };

    private final Runnable garbageAction3 = new Runnable() {
        /**
         * Garbage collector basé sur un nombre maximum d'objets affichés
         */
        @Override
        public void run() {

            //Initialisation des listes et des seuils
            List<Overlay> overlays = mapView.getOverlays();
            int maxPolylines = 150;
            int maxMarkers = 50;
            List<Polyline> polylines = new ArrayList<>();
            List<Marker> markers = new ArrayList<>();
            BoundingBox bbox = mapView.getBoundingBox();

            for(Overlay overlay: overlays){
                if (overlay instanceof Polygon){
                    mapView.getOverlayManager().remove(overlay);
                }
            }

            //Remplissage des listes avec les objets présents sur la carte
            for (int i = 0; i < overlays.size(); i++) {
                if (overlays.get(i) instanceof Polyline) {
                    polylines.add((Polyline) overlays.get(i));
                } else if (overlays.get(i) instanceof Marker) {
                    markers.add((Marker) overlays.get(i));
                }
            }

            //Nettoyage des polylines
            if (polylines.size() > maxPolylines) {
                int numToDelete = polylines.size() - maxPolylines;
                for (int i = 0; i < numToDelete; i++) {
                    if (!(polylines.get(i) instanceof NonRemovablePolyline)) {
                        overlays.remove(polylines.get(i));
                    }
                }
            }

            //nettoyage des marqueurs
            if (markers.size() > maxMarkers) {
                int numToDelete = markers.size() - maxMarkers;
                //System.out.println("marker nb : " + markers.size());
                //System.out.println("nb to delete : " + numToDelete);
                for (int i = 0; i < numToDelete; i++) {
                    if (!(markers.get(i) instanceof NonRemovableMarker)) {
                        overlays.remove(markers.get(i));
                    }
                }
            }

        }
    };

    /**
     * Cette méthode permet de lancer le garbage collector chaque seconde
     */
    public void collect(){

        garbageHandler.removeCallbacks(garbageAction3);
        garbageHandler.postDelayed(garbageAction3, 100);

    }

}
