package cassanellicarlo.geopost.activities;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

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

import cassanellicarlo.geopost.fragments.ElencoAmici;
import cassanellicarlo.geopost.fragments.MappaAmici;
import cassanellicarlo.geopost.R;
import cassanellicarlo.geopost.models.Amico;
import cassanellicarlo.geopost.models.DatiUtente;

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
    private FloatingActionButton fab;


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

       // Nuovo stato (status_update)
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("FloatingButton","NUOVO STATO");
                Intent intent=new Intent(getApplicationContext(),NuovoStato.class);
                startActivity(intent);
            }
        });




    }



    public void scaricaAmici (){

        String session_id= DatiUtente.getInstance().getSession_id();
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

                                if(!noMessage){
                                    listaAmici.add(new Amico(username,msg,lat,lon));
                                }
                            }

                            // Aggiorno la lista degli amici seguiti nel singleton
                            DatiUtente.getInstance().setAmiciSeguiti(listaAmici);
                            // Stampa gli amici che l'utente segue nel LOG
                            DatiUtente.getInstance().stampaAmiciSeguiti();

                            DatiUtente.getInstance().setAmiciScaricati(true);

                            // Creo lista degli amici elenco.creaLista();


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

    public void profilo(View view) {
        Intent intent=new Intent(this, Profilo.class);
        startActivity(intent);
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater=getMenuInflater();
        menuInflater.inflate(R.menu.amici_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch(item.getItemId()){
            case R.id.nuovoAmico:
                Intent intent=new Intent(getApplicationContext(),NuovoAmico.class);
                startActivity(intent);
                break;
            case R.id.profilo:
                Intent intent2=new Intent(getApplicationContext(),Profilo.class);
                startActivity(intent2);
                break;
        }
        return true;
    }
}
