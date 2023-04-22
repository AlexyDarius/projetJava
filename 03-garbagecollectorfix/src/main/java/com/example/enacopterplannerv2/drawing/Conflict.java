package com.example.enacopterplannerv2.drawing;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.Drawable;

import com.example.enacopterplannerv2.NonRemovableMarker;
import com.example.enacopterplannerv2.R;
import com.example.enacopterplannerv2.data.FetchedOverlayManager;

import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.Marker;
import org.osmdroid.views.overlay.Polyline;

import java.util.ArrayList;
import java.util.List;

public class Conflict {

    private static List<Marker> markers;
    private static Drawable conflictIcon;

    private MapView mapView;

    @SuppressLint("UseCompatLoadingForDrawables")
    public Conflict(MapView mapView, Context ctx) {
        conflictIcon = ctx.getResources().getDrawable(R.drawable.baseline_dangerous_36);
        this.mapView = mapView;
    }

    public static void gatherMarkers(){
        markers = FetchedOverlayManager.getMarkers();
    }

    public void detectConflicts(Polyline path){

        gatherMarkers();
        List<GeoPoint> pathPoints = new ArrayList<>(path.getPoints());

        for (Marker marker : markers){
            for (GeoPoint pathPoint : pathPoints){
                double distance = marker.getPosition().distanceToAsDouble(pathPoint);
                if (distance < 100.0){
                    NonRemovableMarker conflictMarker = new NonRemovableMarker(mapView);
                    conflictMarker.setPosition(marker.getPosition());
                    conflictMarker.setIcon(conflictIcon);
                    mapView.getOverlays().add(conflictMarker);
                }
            }
        }
    }

}
