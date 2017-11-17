package cassanellicarlo.geopost;

import android.support.design.widget.TabLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ListView;
import android.widget.TextView;

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
import java.util.HashMap;
import java.util.Map;

public class Amici extends AppCompatActivity {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    private MappaAmici mappa=null;
    private ElencoAmici elenco=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_amici);

        scaricaAmici();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);

        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });




    }


    public void scaricaAmici (){

        String session_id=DatiUtente.getInstance().getSession_id();
        Log.d("SESSIONID:",session_id);

        RequestQueue queue = Volley.newRequestQueue(this);
        final String url = "https://ewserver.di.unimi.it/mobicomp/geopost/followed?session_id="+session_id;

        final ArrayList<Amico> listaAmici=new ArrayList<Amico>();

        // prepare the Request
        JsonObjectRequest getRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>()
                {
                    @Override
                    public void onResponse(JSONObject response) {
                        // display response
                        Log.d("Response", response.toString());

                        try {
                            JSONArray jsonArray=response.getJSONArray("followed");
                            for(int i=0;i<jsonArray.length();i++){
                                Log.d("JSON ARRAY",jsonArray.get(i).toString());
                                JSONObject amico=jsonArray.getJSONObject(i);
                                boolean noMessage=false;
                                String username=amico.getString("username");
                                String msg=amico.getString("msg");
                                double lat=0;
                                double lon=0;
                                if(!amico.getString("lat").equals("null"))
                                    lat=amico.getDouble("lat");
                                else
                                    noMessage=true;
                                if(!amico.getString("lon").equals("null"))
                                    lon=amico.getDouble("lon");
                                else
                                    noMessage=true;
                                Log.d("Dati utente:",username+" "+msg+" "+lat+" "+lon);

                                // Controllo se l'amico ha aggiunto un messaggio ( msg, lat, lon )
                                // Aggiungo solo gli amici con un messaggio
                                if(!noMessage)
                                    listaAmici.add(new Amico(username,msg,lat,lon));
                            }

                            // Aggiorno la lista degli amici seguiti nel singleton
                            DatiUtente.getInstance().setAmiciSeguiti(listaAmici);
                            // Stampa gli amici che l'utente segue nel LOG
                            DatiUtente.getInstance().stampaAmiciSeguiti();

                            DatiUtente.getInstance().setAmiciScaricati(true);

                            // Creo lista degli amici
                            elenco.creaLista();

                            // Creo mappa degli amici
                            mappa.creaMappaAmici();



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

        // add it to the RequestQueue
        queue.add(getRequest);
    }


    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {

            // returning the current tab
            switch (position){
                case 0:
                    mappa=new MappaAmici();
                    return mappa;
                case 1:
                    elenco=new ElencoAmici();
                    return elenco;
                default:
                    return null;
            }

        }

        @Override
        public int getCount() {
            // Show 2 total pages.
            return 2;
        }
    }
}
