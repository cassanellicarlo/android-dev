package cassanellicarlo.geopost;

import android.util.Log;

import java.util.ArrayList;

/**
 * Created by carlo on 14/11/2017.
 */

class DatiUtente {
    private static final DatiUtente ourInstance = new DatiUtente();

    private String session_id;

    private ArrayList<Amico> amiciSeguiti=null;

    static DatiUtente getInstance() {
        return ourInstance;
    }

    private DatiUtente() {
        amiciSeguiti=new ArrayList<Amico>();
    }

    public ArrayList<Amico> getAmiciSeguiti() {
        return amiciSeguiti;
    }

    public void setAmiciSeguiti(ArrayList<Amico> amiciSeguiti) {
        this.amiciSeguiti = amiciSeguiti;
    }

    public void stampaAmiciSeguiti (){
        for(int i=0;i<amiciSeguiti.size();i++){
            String username=amiciSeguiti.get(i).getUsername();
            String msg=amiciSeguiti.get(i).getMsg();
            double lat=amiciSeguiti.get(i).getLat();
            double lon=amiciSeguiti.get(i).getLon();
            Log.d("AMICO:",username+ " "+msg+" "+lat+ " "+lon );
        }
    }

    public String getSession_id() {
        return session_id;
    }

    public void setSession_id(String session_id) {
        this.session_id = session_id;
    }
}
