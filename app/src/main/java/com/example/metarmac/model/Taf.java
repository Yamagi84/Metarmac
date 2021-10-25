package com.example.metarmac.model;

import static com.example.metarmac.XMLReader.convertStringToXMLDocument;

import android.util.Log;

import org.jetbrains.annotations.NotNull;
import org.w3c.dom.Document;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class Taf {

    public Taf(String oaci) {

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("https://aviationweather.gov/adds/dataserver_current/httpparam?dataSource=tafs&requestType=retrieve&format=xml&stationString=" + oaci + "&hoursBeforeNow=24&mostRecent=true")
                .build();


        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException { //VERIFICATION DU NOMBRE DE RESULTATS > 0 SINON AFFICHER ERREUR AU LIEU DE CRASH L'APP
                if (!response.isSuccessful()) {
                    throw new IOException("Unexpected code " + response);
                }
                final String responseData = response.body().string();

                Log.d("Réponse TAF", responseData);

                Document doc = convertStringToXMLDocument(responseData);

                Log.d("NbResult", doc.getChildNodes().item(0).getChildNodes().item(13).getAttributes().item(0).getTextContent());


                String metar = doc.getChildNodes().item(0).getChildNodes().item(13).getChildNodes().item(1).getChildNodes().item(1).getTextContent();
                Log.d("<taf>", metar);

            }
        });

    }
}
