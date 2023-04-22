package com.example.enacopterplannerv2.map;

import android.os.Handler;

import com.example.enacopterplannerv2.mission.NonRemovableMarker;
import com.example.enacopterplannerv2.mission.NonRemovablePolyline;

import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.Marker;
import org.osmdroid.views.overlay.Overlay;
import org.osmdroid.views.overlay.Polygon;
import org.osmdroid.views.overlay.Polyline;

import java.util.List;

/**
 * Cette classe définit un garbage collector qui tourne à intervalle de temps régulier lors d'un
 * changement du champ de vision et qui permet de retirer des Overlay à la carte afin d'éviter
 * une surcharge de l'application
 * @author alexyroman
 */
public class GarbageCollector {

    private final MapView mapView;
    private final Handler garbageHandler = new Handler();

    /**
     * @param mapView carte osmdroid
     */
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

            //Refresh des polygons pour éviter la superposition qui génère de l'opacité
            for(Overlay overlay : overlays) {
                if (overlay instanceof Polygon) {
                    mapView.getOverlayManager().remove(overlay);
                }
            }

            //Compatge du nombre d'overlay pour chaque type
            for (Overlay overlay : overlays) {
                if (overlay instanceof Polyline && !(overlay instanceof NonRemovablePolyline)) {
                    polylineCount ++;
                } else if (overlay instanceof Marker && !(overlay instanceof NonRemovableMarker)) {
                    markerCount++;
                }
            }

            //Nettoyage des polylines
            if (polylineCount > maxPolylines) {
                //ajout d'un offset pour éviter de déclencher le garbage collector avec de petits mouvements
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
                //ajout d'un offset pour éviter de déclencher le garbage collector avec de petits mouvements
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
     * Cette méthode permet de lancer le garbage collector
     */
    public void collect(){
        garbageHandler.removeCallbacks(garbageAction);
        garbageHandler.postDelayed(garbageAction, 100);
    }

}
