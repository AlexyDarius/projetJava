package com.example.enacopterplannerv2;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.Button;
import android.widget.Switch;

import androidx.appcompat.app.AppCompatActivity;

import com.example.enacopterplannerv2.data.PlotManager;
import com.example.enacopterplannerv2.mission.Drawing;
import com.example.enacopterplannerv2.map.Compass;
import com.example.enacopterplannerv2.map.MapSetter;
import com.example.enacopterplannerv2.map.MapUI;
import com.example.enacopterplannerv2.map.MapUpdater;

import org.osmdroid.config.Configuration;
import org.osmdroid.views.MapView;

/**
 * Cette classe définit la mainActivity du programme dans laquelle sont instanciées tous les
 * composants graphiques et plusieurs objets notamment ceux liés à l'interface de la carte mais
 * aussi les outils de récupération et d'affichage de données ainsi que les outils de dessin.
 * @author alexyroman
 * @author gasparddannet
 * @author quentinmirambelle
 * @author clementvuillaume
 * @author tamaitidelorme
 */
public class MainActivity extends AppCompatActivity {

    /**
     *
     * @param savedInstanceState If the activity is being re-initialized after
     *     previously being shut down then this Bundle contains the data it most
     *     recently supplied in {@link #onSaveInstanceState}.  <b><i>Note: Otherwise it is null.</i></b>
     *
     */

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        //Initialisation des composants graphiques
        MapView mapView = findViewById(R.id.mapview);
        @SuppressLint("UseSwitchCompatOrMaterialCode")
        Switch drawingSwitch = findViewById(R.id.drawing_switch);
        Button clearButton = findViewById(R.id.clear_button);
        Button undoButton = findViewById(R.id.undo_button);
        Button finishButton = findViewById(R.id.finish_button);
        Button compassButton = findViewById(R.id.compass_button);
        Button conflictButton = findViewById(R.id.conflict_button);


        //initialisation du context osmdroid
        Context ctx = getApplicationContext();
        Configuration.getInstance().load(ctx, PreferenceManager.getDefaultSharedPreferences(ctx));

        //Initialisation interface de la map
        new MapSetter(mapView);
        new Compass(mapView, ctx, compassButton);
        new MapUI(mapView, ctx);
        new MapUpdater(mapView, ctx);

        //Initialisation des fonctions de dessin
        new Drawing(mapView, ctx, drawingSwitch, clearButton, undoButton, finishButton, conflictButton);

        //Initialisation des données
        new PlotManager(mapView, ctx);

    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

}
