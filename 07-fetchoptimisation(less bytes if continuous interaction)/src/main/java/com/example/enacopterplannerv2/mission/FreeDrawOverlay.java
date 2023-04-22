package com.example.enacopterplannerv2.mission;

import android.graphics.Color;
import android.view.MotionEvent;

import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.Overlay;
import org.osmdroid.views.overlay.Polyline;

import java.util.ArrayList;
import java.util.List;

/**
 * Cette classe permet d'effectuer un dessin sur la carte sous la forme d'une Polyline directement
 * inscrite dans la carte.
 * @author alexyroman
 * @author gasparddannet
 * @author clementvuillaume
 */
public class FreeDrawOverlay extends Overlay {
    //Flag relié au switch "mode dessin"
    private boolean isDrawingMode;
    private final MapView mapView;
    private final ArrayList<GeoPoint> geoPoints;
    private final NonRemovablePolyline path;

    /**
     * Initialisation du mode dessin à "off"
     * @param mapView carte osmdroid
     */
    public FreeDrawOverlay(MapView mapView) {
        isDrawingMode = false;
        this.mapView = mapView;
        geoPoints = new ArrayList<>();
        path = new NonRemovablePolyline();
    }

    /**
     * Permet d'activer le mode dessin
     * @param drawingMode flag du mode dessin
     */
    public void setDrawingMode(boolean drawingMode) {
        isDrawingMode = drawingMode;
    }

    public Polyline getPath() {
        return path;
    }

    /**
     * Cette méthode définit l'interaction de dessin qui consiste à créer des GeoPoints sur le tracé
     * fait au doigt puis de construire la Polyline depuis ces GeoPoints
     * @param motionEvent événement ici appui sur la carte
     * @param mapView carte osmdroid
     * @return bool
     */
    @Override
    public boolean onTouchEvent(MotionEvent motionEvent, MapView mapView) {
        //Vérification de l'activation du "mode dessin"
        if (!isDrawingMode) {
            return super.onTouchEvent(motionEvent, mapView);
        }

        int action = motionEvent.getActionMasked();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:
            case MotionEvent.ACTION_UP:
                //Construction des GeoPoint par une conversion entre les scènes
                geoPoints.add(((GeoPoint) this.mapView.getProjection().fromPixels((int) motionEvent.getX(),(int) motionEvent.getY())));
                break;
        }

        //Construction de la Polyline
        path.setPoints(geoPoints);
        path.setColor(Color.BLUE);
        this.mapView.getOverlays().add(path);
        //refresh de la map
        mapView.invalidate();

        return true;
    }

    /**
     * Cette méthode eprmet d'effacer le dessin
     */
    public void clear() {
        geoPoints.clear();
        path.setPoints(new ArrayList<>());
        mapView.invalidate();
    }

    /**
     * Cette méthode permet de revenir en arirère de 1 point sur le tracé
     */
    public void undo(){
        //Retrait du GeoPoint
        if(!geoPoints.isEmpty()) {
            geoPoints.remove(geoPoints.size() - 1);
        }

        //Mise à jour du tracé
        List<GeoPoint> newPath = path.getPoints();
        if (!path.getPoints().isEmpty()) {
            newPath.remove(newPath.size() - 1);
            path.setPoints(newPath);
            mapView.invalidate();
        }
    }

    /**
     * Cette méthode permet de revenir en arirère de 10 points sur le tracé
     */
    public void longUndo(){

        //Retrait des GeoPoint
        if(geoPoints.size() > 10) {
            geoPoints.remove(geoPoints.size() - 1);
        }

        //mise à jour du tracé
        List<GeoPoint> newPath = path.getPoints();
        if (path.getPoints().size()> 10 ) {
            for(int i = 0; i<10; i++){
                newPath.remove(newPath.size() - 1);
                geoPoints.remove(geoPoints.size()-1);
            }
            path.setPoints(newPath);
            mapView.invalidate();
        }
        else{
            clear();
            mapView.invalidate();
        }
    }

    /**
     * Cette méthode permet de terminer le tracé en revenant au point de départ
     */
    public void finish(){
        //Récupération du premier point et bouclage du tracé
        geoPoints.add(geoPoints.get(0));
        path.setPoints(geoPoints);
        mapView.getOverlays().add(path);
        mapView.invalidate();
    }

}
