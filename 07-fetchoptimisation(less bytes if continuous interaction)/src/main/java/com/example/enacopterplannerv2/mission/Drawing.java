package com.example.enacopterplannerv2.mission;

import android.annotation.SuppressLint;
import android.content.Context;
import android.widget.Button;
import android.widget.Switch;

import org.osmdroid.views.MapView;

/**
 * Cette classe initialise les boutons et composants graphiques liés au dessin et définit les
 * interactions qui y sont liées
 * @author alexyroman
 * @author gasparddannet
 * @author clementvuillaume
 */
public class Drawing {

    /**
     * @param mapView carte osmdroid
     * @param drawingSwitch switch pour activer le mode dessin
     * @param clearButton bouton effacer
     * @param undoButton bouton retour
     * @param finishButton bouton retourner au point de départ
     */
    public Drawing(MapView mapView, Context ctx, @SuppressLint("UseSwitchCompatOrMaterialCode")
    Switch drawingSwitch, Button clearButton, Button undoButton, Button finishButton,
                   Button conflictButton) {

        //Initialisation du dessin
        FreeDrawOverlay freeDrawOverlay = new FreeDrawOverlay(mapView);
        mapView.getOverlayManager().add(freeDrawOverlay);

        //Définition de l'interaction "engager/désengager le mode dessin"
        drawingSwitch.setOnCheckedChangeListener((buttonView, isChecked) ->
                freeDrawOverlay.setDrawingMode(isChecked));

        //Définition de l'interaction "effacer"
        clearButton.setOnClickListener(v -> freeDrawOverlay.clear());

        //Définition de l'interaction "retour" et "long retour"
        undoButton.setOnClickListener(v -> freeDrawOverlay.undo());
        undoButton.setOnLongClickListener(v -> {
            freeDrawOverlay.longUndo();
            return true;
        });

        //Definition de l'interaction "terminer le trajet"
        finishButton.setOnClickListener(v -> freeDrawOverlay.finish());

        //Definition de l'interaction "détecter les conflits"
        conflictButton.setOnClickListener(v -> {
            Conflict conflict = new Conflict(mapView, ctx);
            conflict.detectConflicts(freeDrawOverlay.getPath());
        });

    }

}
