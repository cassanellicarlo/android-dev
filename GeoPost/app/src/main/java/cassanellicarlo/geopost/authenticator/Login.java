package cassanellicarlo.geopost.authenticator;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

import cassanellicarlo.geopost.activities.Amici;
import cassanellicarlo.geopost.models.DatiUtente;
import cassanellicarlo.geopost.R;

public class Login extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_login);
    }

    // Click del pulsante LOGIN
    public void login(View view) {
        final String username=((EditText)findViewById(R.id.username)).getText().toString();
        final String password=((EditText)findViewById(R.id.password)).getText().toString();

        Log.d("User:",username);
        Log.d("Psw:",password);

        // Post Request for login with Volley
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "https://ewserver.di.unimi.it/mobicomp/geopost/login";
        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        // response
                        Log.d("Response", response); // Ritorna un Session ID
                        DatiUtente.getInstance().setSession_id(response);

                        // Se torna un session id --> login corretto

                        if(response.equals("")){
                            Context context = getApplicationContext();
                            CharSequence text = "Dati errati!";
                            int duration = Toast.LENGTH_SHORT;

                            Toast toast = Toast.makeText(context, text, duration);
                            toast.show();
                        }

                        else{ // login corretto -> Passo all'activity "Amici"
                            Intent intent = new Intent (getApplicationContext(),Amici.class);
                            startActivity(intent);
                        }


                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
                        Log.d("Error.Response",error.getMessage());

                        Context context = getApplicationContext();
                        CharSequence text = "Errore di rete!";
                        int duration = Toast.LENGTH_SHORT;

                        Toast toast = Toast.makeText(context, text, duration);
                        toast.show();
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String>  params = new HashMap<String, String>();
                params.put("username", username);
                params.put("password", password);

                return params;
            }
        };
        queue.add(postRequest);

    }
}
