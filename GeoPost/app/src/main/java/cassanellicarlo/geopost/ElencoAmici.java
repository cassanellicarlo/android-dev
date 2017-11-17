package cassanellicarlo.geopost;

/**
 * Created by carlo on 09/11/2017.
 */

import android.content.Context;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;

public class ElencoAmici extends Fragment{

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.amici_elenco, container, false);

        //creaLista();

        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();


    }

    @Override
    public void onResume() {
        super.onResume();

    }

    // Crea la lista degli amici basata su un Adapter personalizzato (AmiciAdapter)
    public void creaLista (){

        Log.d("ElencoAmici",DatiUtente.getInstance().isAmiciScaricati()+"");

        if(DatiUtente.getInstance().isAmiciScaricati()){

            AmiciAdapter myAdapter=new AmiciAdapter(getActivity(),
                    android.R.layout.list_content, DatiUtente.getInstance().getAmiciSeguiti() );

            ListView listView=(ListView)getView().findViewById(R.id.listaAmici);
            listView.setAdapter(myAdapter);
        }


    }
}
