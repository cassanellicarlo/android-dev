package cassanellicarlo.richiestahttpvolley;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void richiesta(View view) {

        final TextView result = (TextView) findViewById(R.id.risultato);

        // Instanza di RequestQueue
        RequestQueue queue= Volley.newRequestQueue(this);
        String url="http://mobidev2014.appspot.com/helloworld";

        // Richiesta di una risposta (String) dall'URL
        StringRequest stringRequest=new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        result.setText(response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                result.setText("Errore!");
            }
        });

        // Aggiunge la richiesta alla RequestQueue
        queue.add(stringRequest);



    }
}
