package com.example.carlo.map;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback, LocationListener, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener{

    private ArrayList<Utente> utenti;
    private GoogleMap mMap = null;
    private static final int LOCATION_PERMISSION = 1;
    private GoogleApiClient mGoogleApiClient=null;
    private TextView myTextView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Testo per messaggio sui permessi (concessi o meno)
        myTextView = (TextView)findViewById(R.id.messaggio);

        // Get the SupportMapFragment and request notification
        // when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        inserisciUtenti();
    }

    protected void onStart() {
        if(mGoogleApiClient!=null) mGoogleApiClient.connect();
        super.onStart();
    }

    protected void onStop() {
        if(mGoogleApiClient!=null) mGoogleApiClient.disconnect();
        super.onStop();
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

    // Quando la mappa è pronta abilito i bottoni e inizializzo la mappa
    @Override
    public void onMapReady(GoogleMap googleMap) {

        mMap=googleMap;

        // Abilito il bottone 'Mostra utenti' e 'La mia posizione'
        findViewById(R.id.bottone).setEnabled(true);
        findViewById(R.id.bottone2).setEnabled(true);

    }

    // Aggiungo i marker nella mappa --> PRIMO BOTTONE: 'Mostra Utenti'
    public void addMarkers(View view) {

        // per ogni utente aggiungio un Marker con Lat e Lon e come titolo il nome utente
        for(int i=0;i<utenti.size();i++){
            LatLng posizione=utenti.get(i).getLatLon();
            String nome=utenti.get(i).getNome();
            mMap.addMarker(new MarkerOptions().position(posizione).title(nome));
        }

        // Calcolo la mia area per mostrare tutti i marker
        LatLngBounds myBounds=myArea();

        // Centro la mappa sull'area che ho calcolato
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(myBounds.getCenter(),0));
    }

    // Click del bottone 'La mia posizione' --> Chiedo i permessi all'utente per
    // mostrare la sua posizione
    public void myPosition(View view) {

        // Controllo Permessi per Location
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {

            Log.d("Main Activity", "Permission granted!");
            myTextView.setText("Permessi per mostrare la tua posizione ottenuti. Posizione mostrata sulla mappa");
            startGoogleApiClient();


        } else{

            Log.d("Main Activity", "Not Granted yet.");

            // Richiesta dei permessi
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    LOCATION_PERMISSION);
        }


    }

    public void startGoogleApiClient (){

        // Creazione di un'istanza di GoogleAPIClient: permette di accedere
        // ai servizi di Google Play Services
        if (mGoogleApiClient == null) {
            mGoogleApiClient = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();

            mGoogleApiClient.connect();
        }


    }

    // Metodo chiamato quando il Google Api Client è pronto
    @Override
    public void onConnected(@Nullable Bundle bundle) {
        Log.d("onConnected:", "Google API Client Ready!");

        // Setto i parametri per l'acquisizione della posizione
        LocationRequest mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(10000);
        mLocationRequest.setFastestInterval(5000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {

            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);

        }
    }

    // Il metodo onLocationChanged viene richiamato quando c’è una
    // nuova posizione, passando come argomento la posizione stessa.
    // Aggiungo un marker per la posizione dell'utente e centro la mappa in quella posizione
    @Override
    public void onLocationChanged(Location location) {
        LatLng myPosition=new LatLng(location.getLatitude(),location.getLongitude());

        // Aggiungo il marker settando i parametri
        mMap.addMarker(new MarkerOptions()
                .position(myPosition)
                .title("La mia posizione")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));

        // Centro la mappa sulla mia posizione
        mMap.moveCamera(CameraUpdateFactory.newLatLng(myPosition));

        // Zommo la mappa con un'animazione
        mMap.animateCamera(CameraUpdateFactory.zoomTo(10), 2000, null);


    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.d("onConnectionSuspended", "Connessione sospesa Google API Client");
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.d("onConnectionFailed", "Connessione fallita Google API Client");
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case LOCATION_PERMISSION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    startGoogleApiClient();

                    // Permessi Garantiti
                    Log.d("Main Activity", "Permessi OK");
                    myTextView.setText("Permessi per mostrare la tua posizione ottenuti. Posizione mostrata sulla mappa");

                } else {
                    // Permessi Non Garantiti
                    Log.d("Main Activity", "Permessi NON garantiti");

                    myTextView.setText("Non hai dato i permessi per utilizzare la posizione. Non posso mostrare dove ti trovi");
                }
                return;
            }

        }
    }
}
