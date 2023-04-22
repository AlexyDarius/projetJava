package com.example.enacopterplannerv2.drawing;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.Drawable;

import com.example.enacopterplannerv2.R;
import com.example.enacopterplannerv2.data.FetchedOverlayManager;

import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.overlay.Marker;
import org.osmdroid.views.overlay.Polyline;

import java.util.ArrayList;
import java.util.List;

public class Conflict {

    private static List<Marker> markers;
    private static Drawable conflictIcon;

    @SuppressLint("UseCompatLoadingForDrawables")
    public Conflict(Context ctx) {
        conflictIcon = ctx.getResources().getDrawable(R.drawable.baseline_dangerous_36);
    }

    public static void gatherMarkers(){
        markers = FetchedOverlayManager.getMarkers();
    }

    public static void detectConflicts(Polyline path){

        gatherMarkers();
        List<GeoPoint> pathPoints = new ArrayList<>(path.getPoints());

        for (Marker marker : markers){
            for (GeoPoint pathPoint : pathPoints){
                double distance = marker.getPosition().distanceToAsDouble(pathPoint);
                if (distance < 100.0){
                    System.out.println("conflict at : "+marker.getTitle());
                    marker.setIcon(conflictIcon);
                    marker.setInfoWindowAnchor(0.5f, 0.0f);
                }
            }
        }
    }

}
