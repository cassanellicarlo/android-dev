package cassanellicarlo.geopost.activities;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cassanellicarlo.geopost.models.DatiUtente;
import cassanellicarlo.geopost.R;

public class NuovoAmico extends AppCompatActivity {

    private String url; // URL della chiamata di rete
    private ArrayList<String> utenti; // Lista degli utenti (risposta del server)
    private AutoCompleteTextView textView;
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nuovo_amico);

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

    public void seguiAmico(View view) {
        final Context context = getApplicationContext();
        AutoCompleteTextView autoCompleteTextView=(AutoCompleteTextView)findViewById(R.id.users);
        String amicoDaSeguire=autoCompleteTextView.getText().toString();
        Log.d("AMICODASEGUIRE",amicoDaSeguire);

        String session_id= DatiUtente.getInstance().getSession_id();
        Log.d("SESSIONID:",session_id);

        RequestQueue queue = Volley.newRequestQueue(this);
        final String url = "https://ewserver.di.unimi.it/mobicomp/geopost/follow?session_id="
                +session_id+"&username="+amicoDaSeguire;
        Log.d("URL FOLLOWFRIEND",url);

        // prepare the Request
        StringRequest getRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        // display response
                        Log.d("Response", response);
                        Log.d("LunghezzaResponse",response.length()+"");

                        if(response.equals("OK")) {

                            CharSequence text = "Amico seguito!";
                            int duration = Toast.LENGTH_SHORT;

                            Toast toast = Toast.makeText(context, text, duration);
                            toast.show();

                        }

                        else if(response.equals("KO")) {

                            CharSequence text = "Utente non presente!";
                            int duration = Toast.LENGTH_SHORT;

                            Toast toast = Toast.makeText(context, text, duration);
                            toast.show();
                        }


                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("Error.Response", error.getMessage());

                        Context context = getApplicationContext();
                        CharSequence text = "Errore!";
                        int duration = Toast.LENGTH_SHORT;

                        Toast toast = Toast.makeText(context, text, duration);
                        toast.show();
                    }
                }
        );

        // add it to the RequestQueue
        queue.add(getRequest);

    }
}


