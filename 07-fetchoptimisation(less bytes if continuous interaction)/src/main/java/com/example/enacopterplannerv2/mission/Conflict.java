package com.example.enacopterplannerv2.mission;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.Drawable;

import com.example.enacopterplannerv2.R;
import com.example.enacopterplannerv2.data.FetchedOverlayManager;

import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.Marker;
import org.osmdroid.views.overlay.Polyline;

import java.util.ArrayList;
import java.util.List;

/**
 * Cette classe permet de détecter les conflits entre un tracé et un ensemble d'obstacles comme des
 * Marker, des Polyline ou des Polygon.
 * @author alexyroman
 *  * @TODO optimiser la détection de conflit en se restreignant à la bbox du tracé
 *  * @TODO ajouter les Polyline et Polygon
 */
public class Conflict {

    private static List<Marker> markers;
    private static Drawable conflictIcon;
    private final MapView mapView;

    /**
     * @param mapView carte osmdroid
     * @param ctx context de l'application
     */
    @SuppressLint("UseCompatLoadingForDrawables")
    public Conflict(MapView mapView, Context ctx) {
        //Icone de conflit
        conflictIcon = ctx.getResources().getDrawable(R.drawable.baseline_dangerous_36);
        this.mapView = mapView;
    }

    /**
     * Cette méthode permet de récupérer la liste des Marker
     */
    public static void gatherMarkers(){
        markers = FetchedOverlayManager.getMarkers();
    }

    /**
     * Cette méthode permet de réaliser une détection de conflit entre le tracé d'une misison et
     * l'ensemble des objets présents sur la carte.
     * @param path tracé de la mission
     */
    public void detectConflicts(Polyline path){

        //Récupération des Marker
        gatherMarkers();
        List<GeoPoint> pathPoints = new ArrayList<>(path.getPoints());

        //Vérification de la distance de chaque point du tracé à chaque MArker
        for (Marker marker : markers){
            for (GeoPoint pathPoint : pathPoints){
                double distance = marker.getPosition().distanceToAsDouble(pathPoint);
                if (distance < 100.0){  //100 mètres
                    //Ajout d'un Marker de conflit
                    NonRemovableMarker conflictMarker = new NonRemovableMarker(mapView);
                    conflictMarker.setPosition(marker.getPosition());
                    conflictMarker.setIcon(conflictIcon);
                    mapView.getOverlays().add(conflictMarker);
                }
            }
        }
    }

}
