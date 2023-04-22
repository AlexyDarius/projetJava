package com.example.enacopterplannerv2.map;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;

import com.example.enacopterplannerv2.data.PlotManager;

import org.osmdroid.events.MapListener;
import org.osmdroid.events.ScrollEvent;
import org.osmdroid.events.ZoomEvent;
import org.osmdroid.util.BoundingBox;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.Marker;
import org.osmdroid.views.overlay.Overlay;
import org.osmdroid.views.overlay.Polyline;

import java.util.ArrayList;
import java.util.List;

/**
 * Cette classe permet la mise à jour dynamique des données sur la carte lors des interactions
 * (scroll et zoom) en chargeant les données depuis le serveur uniquement dans la champ de vision
 * actuel. Elle instancie également un garbage collector qui tente d'effacer un maximu de données
 * qui ne sont pas utiles sur la carte, comme ceux en dehors du champ de vision.
 * La classe hérite de Activity pour pouvoir appelr des Handler.
 * @author alexyroman
 */

public class MapUpdater extends Activity {

    private static String BBOX = "BBOX=43.547421,1.351668,43.651348,1.564528";
    private final MapView mapView;
    private final Context ctx;
    private final Handler interactionHandler = new Handler();
    private final Handler garbageHandler = new Handler();

    /**
     * @param mapView carte osmdroid
     * @param ctx context de l'application
     */
    public MapUpdater(MapView mapView, Context ctx){
        this.mapView = mapView;
        this.ctx = ctx;

        mapView.setMapListener(new MapListener() {
            /**
             * Cette méthode appelle la fonction de mise à jour des données lors du scroll
             * @param event événement, ici scroll de la carte
             * @return bool
             */
            @Override
            public boolean onScroll(ScrollEvent event) {
                //appel de la fonction de mise à jour des données au maximum chaque 0,2 seconde
                interactionHandler.removeCallbacks(interactionRunnable);
                interactionHandler.postDelayed(interactionRunnable, 200);
                return false;
            }

            /**
             * Cette méthode appelle la fonction de mise à jour des données lors du zoom
             * @param event événement, ici zoom de la carte
             * @return bool
             */
            @Override
            public boolean onZoom(ZoomEvent event) {
                //appel de la fonction de mise à jour des données au maximum chaque 0,2 seconde
                interactionHandler.removeCallbacks(interactionRunnable);
                interactionHandler.postDelayed(interactionRunnable, 200);
                return false;
            }
        });

    }

    /**
     * Cette méthode retourne la bounding box au format string utilisable dans la requête au serveur
     * @return BBOX
     */
    public static String getBBOX() {
        return BBOX;
    }

    /**
     * Cette méthode permet de fixer la bbox osmdroid
     * @param bbox la bounding box osmdroid
     */
    public static void setBBOX(String bbox) {
        BBOX = bbox;
    }

    /**
     * Définit un programme qui met à jour la bbox qui sert aux requêtes, appel une requête serveur
     * sur cette bbox et lance le garbage collector.
     */
    private final Runnable interactionRunnable = new Runnable() {
        /**
         * Mise à jour de la bbox qui sert aux requêtes, appel une requête serveur
         *      * sur cette bbox et lance le garbage collector.
         */
        @Override
        public void run() {
            BoundingBox boundingBox = mapView.getBoundingBox();
            //mise à jour de la bbox
            setBBOX("BBOX="+
                    boundingBox.getLatSouth() +
                    ","+ boundingBox.getLonWest() +
                    ","+ boundingBox.getLatNorth() +
                    ","+ boundingBox.getLonEast());
            //Appel de la requête serveur
            new PlotManager(mapView, ctx);
            //Lancement du garbage collector
            //garbageCollector();
        }
    };

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
                    overlays.remove(polylines.get(i));
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

    /**
     * Cette méthode permet de lancer le garbage collector chaque seconde
     */
    public void garbageCollector(){

        garbageHandler.removeCallbacks(garbageAction);
        garbageHandler.postDelayed(garbageAction, 1000);

    }

}
