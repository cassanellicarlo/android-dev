package cassanellicarlo.userdatabase;

import android.arch.persistence.room.Room;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    public AppDatabase db = null;
    public boolean alreadyInitialized=false; // Per creare il database una volta sola

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    public void cerca(View view) {

        // Prendo il valore dell'id nel campo di testo
        EditText eD = (EditText)findViewById(R.id.idUtente);
        String value= eD.getText().toString();
        int idUtente=Integer.parseInt(value);

        new dbInit().execute(idUtente);
    }

    private class dbInit extends AsyncTask <Integer,Void,User> {

        @Override
        protected User doInBackground(Integer... userids) {

            // Id dell'utente da cercare (preso dal campo di testo)
            int userId=userids[0];
            
            if(!alreadyInitialized){ // Se il DB non è stato ancora inizializzato
                // Istanza del database
                db = Room.databaseBuilder(getApplicationContext(),
                        AppDatabase.class, "user-db").build();

                // Creazione degli utenti
                User[] utenti =new User[5];
                utenti[0]=new User(1,"Carlo","Cassanelli");
                utenti[1]=new User(2,"Matteo","Sausto");
                utenti[2]=new User(3,"Matteo","Mazza");
                utenti[3]=new User(4,"Sergio","Mascetti");
                utenti[4]=new User(5,"Francesco","Urso");
                db.userDao().insertAll(utenti);

                alreadyInitialized=true;
            }

            // Cerco nel database l'utente con quell' ID
            User utenteCercato=db.userDao().findById(userId);

            // Passo alla onPostExecute l'User con quell'ID
            return utenteCercato;
        }

        protected void onPostExecute (User u){
            // Stampo il nome dell'utente cercato
            TextView nome=(TextView)findViewById(R.id.nomeUtente);
            nome.setText(u.getNome());

            // Stampo il cognome dell'utente cercato
            TextView cognome=(TextView)findViewById(R.id.cognomeUtente);
            cognome.setText(u.getCognome());

        }
    }
}
