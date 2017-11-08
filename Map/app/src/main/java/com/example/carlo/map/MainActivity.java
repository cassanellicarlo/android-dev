package com.example.carlo.map;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback{

    private ArrayList<Utente> utenti;
    private GoogleMap mMap = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Get the SupportMapFragment and request notification
        // when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        inserisciUtenti();
    }

    // Inserisco 5 Utenti con Nome, Latitutine e Longitudine
    public void inserisciUtenti (){
        utenti = new ArrayList<Utente>();
        utenti.add(new Utente("Carlo",-33.852,151.211));
        utenti.add(new Utente("Marco",-37.20,150.211));
        utenti.add(new Utente("Francesco",-38.852,158.211));
        utenti.add(new Utente("Anna",-31.852,120.211));
        utenti.add(new Utente("Matteo",-35.852,159.211));
    }

    // per centrare la mappa e far vedere tutti i punti che voglio mostrare
    public LatLngBounds myArea (){

        // calcolo la minima longitudine e la minima latitudine
        double minLon=utenti.get(0).getLon();
        double minLat=utenti.get(0).getLat();
        for(int i=1;i<utenti.size();i++){
            if(minLon>utenti.get(i).getLon())
                minLon=utenti.get(i).getLon();

            if(minLat>utenti.get(i).getLat())
                minLat=utenti.get(i).getLat();

        }
        Log.d("MINMAX","Minima Longitudine:"+minLon);
        Log.d("MINMAX","Minima Latitudine:"+minLat);

        // calcolo la massima longitudine e la massima latitudine
        double maxLon=utenti.get(0).getLon();
        double maxLat=utenti.get(0).getLat();
        for (int i=1;i<utenti.size();i++){
            if(utenti.get(i).getLon() > maxLon)
                maxLon=utenti.get(i).getLon();

            if(utenti.get(i).getLat() > maxLat)
                maxLat=utenti.get(i).getLat();
        }

        Log.d("MINMAX","Massima Longitudine:"+maxLon);
        Log.d("MINMAX","Massima Latitudine:"+maxLat);

        LatLngBounds myBounds = new LatLngBounds(new LatLng(minLat, minLon), new LatLng(maxLat, maxLon));

        return myBounds;

    }

    // Quando la mappa è pronta abilito il bottone e chiamo la funzione addMarkers()
    @Override
    public void onMapReady(GoogleMap googleMap) {

        mMap=googleMap;

        // Abilito il bottone 'Mostra'
        findViewById(R.id.bottone).setEnabled(true);

    }

    // Aggiungo i marker nella mappa
    public void addMarkers(View view) {

        // per ogni utente aggiungio un Marker con Lat e Lon e come titolo il nome utente
        for(int i=0;i<utenti.size();i++){
            LatLng posizione=utenti.get(i).getLatLon();
            String nome=utenti.get(i).getNome();
            mMap.addMarker(new MarkerOptions().position(posizione).title(nome));
        }

        // Calcolo la mia area per mostrare tutti i marker
        LatLngBounds myBounds=myArea();

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(myBounds.getCenter(),0));
    }

}
