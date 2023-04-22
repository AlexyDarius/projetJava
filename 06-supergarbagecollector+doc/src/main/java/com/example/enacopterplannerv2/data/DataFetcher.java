package com.example.enacopterplannerv2.data;

import androidx.annotation.NonNull;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * Cette classe permet de récupérer les données sur un serveur grâce à la librairie OKHTTP.
 * @author alexyroman
 */
public class DataFetcher {
    private final OkHttpClient client = new OkHttpClient();
    private String fetchedData;
    private final PlotManager plotManager;

    /**
     * @param plotManager afficheur de données
     */
    public DataFetcher(PlotManager plotManager) {
        this.plotManager = plotManager;
    }

    /**
     * @param url url de la base de donnée
     */
    public void fetchData(String url) {
        //Initialisation de la requête
        Request request = new Request.Builder()
                .url(url)
                .build();

        //Lancement de la requête
        Call call = client.newCall(request);
        call.enqueue(new Callback() {

            /**
             * Cette méthode définit la procédure en cas d'échec de la connexion (pas de connexion
             * internet par exemple)
             * @param call appel de la requête
             * @param e exception
             */
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                System.out.println("network error");
                e.printStackTrace();
            }

            /**
             * cette méthode défnit la procédure en cas de réussite de connexion au serveur
             * @param call appel de la requête
             * @param response réponse du serveur
             * @throws IOException erreur de réponse (erreur de syntaxe de requête par exemple)
             */
            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                ResponseBody responseBody = response.body();
                if (!response.isSuccessful() || responseBody == null) {
                    System.out.println("response error");
                    return;
                }

                //Ajout de la réponse à l'attribut fetchedData
                fetchedData = responseBody.string();
                //Mise à jour du flag pour le handler de l'afficheur
                plotManager.setFetchOK(true);
            }
        });
    }

    /**
     * Cette méthode permet d'obtenir les données récuperées
     * @return fetchedData les données récuperées
     */
    public String getFetchedData() {
        return fetchedData;
    }

}
