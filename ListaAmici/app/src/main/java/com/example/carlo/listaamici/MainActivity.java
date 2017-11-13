package com.example.carlo.listaamici;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private String url; // URL della chiamata di rete
    private ArrayList<String> utenti; // Lista degli utenti (risposta del server)
    private AutoCompleteTextView textView;
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final RequestQueue queue = Volley.newRequestQueue(this);
        utenti = new ArrayList<String>();
        textView= (AutoCompleteTextView) findViewById(R.id.users);
        textView.setThreshold(1);
        // Aggiungo un Listener alla textView
        textView.addTextChangedListener(new TextWatcher() {

            // Metodo chiamato ad ogni modifica dell'AutoCompleteTextView
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                utenti.clear();
                String parametro=textView.getText().toString();
                Log.d("Parametro",parametro);
                url="https://ewserver.di.unimi.it/mobicomp/geopost/users?usernamestart=";
                url+=parametro;
                Log.d("URL Richiesta",url);

                JsonObjectRequest getRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                        new Response.Listener<JSONObject>()
                        {
                            @Override
                            public void onResponse(JSONObject response) {
                                try {
                                    // Inserisco gli utenti in un JSONArray
                                    JSONArray jsonArray=response.getJSONArray("usernames");

                                    // Dal JSONArray creo un ArrayList<String> con i nomi degli utenti cercati
                                    for(int i=0;i<jsonArray.length();i++){
                                        utenti.add(jsonArray.get(i).toString());
                                    }

                                    Log.d("Response", utenti+"");
                                    // Adapter per l'AutoCompleteTextView
                                    adapter = new ArrayAdapter<String>(getBaseContext(),
                                            android.R.layout.simple_dropdown_item_1line,utenti);
                                    textView.setAdapter(adapter);
                                    adapter.notifyDataSetChanged();



                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }


                            }
                        },
                        new Response.ErrorListener()
                        {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Log.d("Error.Response", error.getMessage());
                            }
                        }



                );

                queue.add(getRequest);

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }
}
