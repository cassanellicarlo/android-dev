package cassanellicarlo.position;

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
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity implements LocationListener, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    private static final int LOCATION_PERMISSION = 1;
    private GoogleApiClient mGoogleApiClient=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Controllo Permessi per Location
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {

            Log.d("Main Activity", "Permission granted!");
            startGoogleApiClient();


        } else{

            Log.d("Main Activity", "Not Granted yet.");

            // Richiesta dei permessi
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    LOCATION_PERMISSION);
        }

    }

    protected void onStart() {
        if(mGoogleApiClient!=null) mGoogleApiClient.connect();
        super.onStart();
    }

    protected void onStop() {
        if(mGoogleApiClient!=null) mGoogleApiClient.disconnect();
        super.onStop();
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


                } else {
                    // Permessi Non Garantiti
                    Log.d("Main Activity", "Permessi NON garantiti");
                }
                return;
            }

        }
    }



    @Override
    // Metodo chiamato quando il Google Api Client è pronto
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

    @Override
    public void onConnectionSuspended(int i) {
        Log.d("onConnectionSuspended", "Connessione sospesa Google API Client");
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.d("onConnectionFailed", "Connessione fallita Google API Client");
    }

    @Override
    // Il metodo onLocationChanged viene richiamato quando c’è una
    // nuova posizione, passando come argomento la posizione stessa.
    public void onLocationChanged(Location location) {
        TextView myTextView=(TextView)findViewById(R.id.userPosition);
        myTextView.setText(location.toString());

    }
}

