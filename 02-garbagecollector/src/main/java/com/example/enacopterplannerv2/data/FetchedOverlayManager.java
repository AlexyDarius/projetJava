package com.example.enacopterplannerv2.data;

import android.app.Activity;
import android.content.Context;
import android.graphics.Point;

import org.osmdroid.api.IMapView;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.ItemizedOverlay;
import org.osmdroid.views.overlay.Marker;
import org.osmdroid.views.overlay.Overlay;
import org.osmdroid.views.overlay.OverlayItem;
import org.osmdroid.views.overlay.Polyline;

import java.util.ArrayList;
import java.util.List;

/**
 * Cette classe définit l'ensemble des méthodes de traitement des données récupées sur le serveur,
 * de la récupération de la chaîne de caractère brute à la construction d'objets osmdroid et leur
 * affichage.
 * @author alexyroman
 */
public class FetchedOverlayManager extends Activity {

    private final MapView mapView;
    private final DataFetcher dataFetcher;
    private static List<Marker> markers;
    private static List<Polyline> polylines;

    /**
     * @param mapView carte osmdroid
     * @param dataFetcher données récuperées
     */
    public FetchedOverlayManager(MapView mapView, DataFetcher dataFetcher) {
        this.mapView = mapView;
        this.dataFetcher = dataFetcher;
        markers = new ArrayList<>();
        polylines = new ArrayList<>();
    }

    /**
     * Cette méthode permet d'accéder aux données récuperées depuis les serveur
     * @return fetchedData les données récuperées sur le serveur
     */
    private String fetchData() {
        return dataFetcher.getFetchedData();
    }

    /**
     * Cette méthode utilise le parser de points pour construire la liste de GeoPoint nommés depuis
     * la chaîne de caractère récuperée sur le serveur
     * @param fetchedData les données récuperées sur le serveur
     * @return List-NamedGeoPoint- la liste des GeoPoint nommés
     */
    private List<NamedGeoPoint> createNamedGeoPointList(String fetchedData){
        JSONPointParser parser = new JSONPointParser();
        return parser.parse(fetchedData);
    }

    /**
     * Cette méthode permet de construire des marqueurs osmdroid depuis une liste de GeoPoint nommés
     * @param namedGeoPointList la liste de GeoPoint nommés
     * @return markers la liste de marqueurs (objets osmdroid)
     */
    private List<Marker> createMarkers(List<NamedGeoPoint> namedGeoPointList){

        List<Marker> localMarkers = new ArrayList<>();

        //Parcours de la liste de GeoPoint nommés
        for (NamedGeoPoint ngp : namedGeoPointList){
            Marker marker = new Marker(mapView);

            marker.setPosition(ngp);
            marker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM);

            //Titrage du marqueur par sa nature
            marker.setTitle(ngp.getNature());
            marker.setSnippet("Snippet");

            //Ajout du marqueur à la liste de marqueurs
            localMarkers.add(marker);
        }

        //Mise à jour de la liste des marqueurs
        setMarkers(localMarkers);
        return markers;
    }

    /**
     * Cette méthode utilise le parser de lignes pour construire la liste de Polylines nommés depuis
     * la chaîne de caractère récuperée sur le serveur
     * @param fetchedData les données récuperées sur le serveur
     * @return List-NamedPolyline- la liste des Polyline nommées
     */
    private List<NamedPolyline> createNamedPolylineList(String fetchedData){
        JSONLineParser parser = new JSONLineParser();
        return parser.parse(fetchedData);
    }

    /**
     * Cette méthode permet de construire des Polylines osmdroid depuis une liste de Polylines nommés
     * @param namedPolylines la liste de Polylines nommés
     * @return polylines la liste de polylines (objets osmdroid)
     */
    private List<Polyline> createPolylines(List<NamedPolyline> namedPolylines){

        List<Polyline> localPolylines = new ArrayList<>();

        //Parcours de la liste de Polylines nommées
        for (NamedPolyline npl : namedPolylines){
            Polyline polyline = new Polyline(mapView);

            polyline.setPoints(npl.getPoints());

            //Titrage de la polyline par son id
            polyline.setTitle(npl.getId());
            polyline.setSnippet("Snippet");

            //Ajout de la polyline à la liste des polylines
            localPolylines.add(polyline);
        }

        //Mise à jour de la liste des polylines
        setPolylines(localPolylines);
        return polylines;
    }

    /**
     * Cette méthode permet d'afficher les objets osmdroid sur la carte
     * @param overlays liste des overlays à afficher (polylines ou marqueurs)
     */
    private void createOverlays(List<? extends Overlay> overlays){
        mapView.getOverlays().addAll(overlays);
        mapView.invalidate();
    }

    /**
     * Application de la methode createOverlays aux marqueurs
     */
    public void plotMarkers(){
        createOverlays(createMarkers(createNamedGeoPointList(fetchData())));
    }

    /**
     * Application de la methode createOverlays aux polylines
     */
    public void plotPolylines(){
        createOverlays(createPolylines(createNamedPolylineList(fetchData())));
    }

    public static List<Marker> getMarkers() {
        return markers;
    }

    public List<Polyline> getPolylines() {
        return polylines;
    }

    /**
     * Cette méthode permet de mettre à jour la liste de marqueurs
     * @param markers liste de marqueurs
     */
    public void setMarkers(List<Marker> markers) {
        FetchedOverlayManager.markers = markers;
    }

    /**
     * Cette méthode permet de mettre à jour la liste de polylines
     * @param polylines liste de polylines
     */
    public void setPolylines(List<Polyline> polylines) {
        FetchedOverlayManager.polylines = polylines;
    }
}
