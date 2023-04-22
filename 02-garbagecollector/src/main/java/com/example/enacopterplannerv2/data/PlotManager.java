package com.example.enacopterplannerv2.data;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.os.Handler;
import android.os.Looper;

import com.example.enacopterplannerv2.R;
import com.example.enacopterplannerv2.map.MapUpdater;

import org.json.JSONException;
import org.json.JSONObject;
import org.osmdroid.views.MapView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Cette classe permet d'afficher les données récuperées sur le serveur. Elle utilise notamment des
 * méthodes de communication par handler et flags pour afficher les données uniquement après
 * qu'elles aient été récupérées.
 * La classe hérite de Activity piur pouvoir utiliser les handlers.
 * @author alexyroman
 */
public class PlotManager extends Activity {

    private final FetchedOverlayManager markerFetchedOverlayManager;
    private final FetchedOverlayManager polylineFetchedOverlayManager;
    private final Handler handler;
    private static boolean fetchOK = false;

    /**
     * Cette méthode initialise l'afficheur de données
     * @param mapView carte osmdroid
     * @param ctx context de l'applciation
     */
    public PlotManager (MapView mapView, Context ctx) {

        //Initialisation des téléchargeurs de données
        DataFetcher markerDataFetcher = new DataFetcher(this);
        markerFetchedOverlayManager = new FetchedOverlayManager(mapView, markerDataFetcher);
        DataFetcher polylineDataFetcher = new DataFetcher(this);
        polylineFetchedOverlayManager = new FetchedOverlayManager(mapView, polylineDataFetcher);

        //Récupération des urls dans le fichier JSON
        try {
            Resources res = ctx.getResources();
            InputStream inputStream = res.openRawResource(R.raw.urls);
            JSONObject jsonObject = new JSONObject(convertStreamToString(inputStream));

            //Récupération de la BBOX courante
            String BBOX = MapUpdater.getBBOX();

            //Téléchargement des données
            String urlElevatedPoints = jsonObject.getString("urlElevatedPoints");
            markerDataFetcher.fetchData(urlElevatedPoints+"&"+ BBOX+"&Count=150");
            String urlElectricLines = jsonObject.getString("urlElectricLines");
            polylineDataFetcher.fetchData(urlElectricLines+"&"+ BBOX+"&Count=500");

        } catch (
                JSONException e) {
            e.printStackTrace();
        }

        //Initialisation du handler d'affichage
        handler = new Handler(Looper.getMainLooper());
        final int delay = 100; //millisecondes

        //Définition du programme du handler
        final Runnable runnable = new Runnable() {
            /**
             * Cette méthode définit le programme du handler qui vérifie si le flag de données reçues
             * est actif et affiche les données
             */
            @Override
            public void run() {
                //Vérification du flag
                if (fetchOK) {

                    //Bloc permettant la résolution d'un problème de synchronisation, le flag étant
                    //vérifié quelques instants avant la communiation des données dans le programme
                    ScheduledThreadPoolExecutor exec = new ScheduledThreadPoolExecutor(1);
                    exec.schedule(() -> {
                        markerFetchedOverlayManager.plotMarkers();
                        polylineFetchedOverlayManager.plotPolylines();
                    }, 50, TimeUnit.MILLISECONDS);

                    //Désactivation du flag
                    fetchOK = false;
                }
                handler.postDelayed(this, delay);
            }
        };
        handler.postDelayed(runnable, delay);
    }

    /**
     * Cette méthode permet de changer la valeur du flag
     * @param value valeur booléenne du flag
     */
    public void setFetchOK(boolean value) {
        fetchOK = value;
    }

    /**
     * Cette méthode permet de lire dans le ficheir JSON des urls.
     * @param inputStream données d'entrée
     * @return string chaîne de caractère construite depuis le JSON
     */
    private String convertStreamToString(InputStream inputStream) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        StringBuilder sb = new StringBuilder();
        String line;

        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line).append('\n');
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }

}
