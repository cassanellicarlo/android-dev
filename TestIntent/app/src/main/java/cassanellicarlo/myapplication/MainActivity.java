package cassanellicarlo.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    public static final String EXTRA_MESSAGE = "cassanellicarlo.myapplication.MESSAGE";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.d("Sono Main Activity", "Metodo onCreate");
    }

    protected void onStart(){
        super.onStart();
        Log.d("Sono Main Activity", "Metodo onStart");
    }

    protected void onRestart(){
        super.onRestart();
        Log.d("Sono Main Activity", "Metodo onRestart");
    }

    protected void onResume(){
        super.onResume();
        Log.d("Sono Main Activity", "Metodo onResume");
    }

    protected void onPause(){
        super.onPause();
        Log.d("Sono Main Activity", "Metodo onPause");
    }

    protected void onStop(){
        super.onStop();
        Log.d("Sono Main Activity", "Metodo onStop");
    }

    protected void onDestroy(){
        super.onDestroy();
        Log.d("Sono Main Activity", "Metodo onDestroy");
    }

    // Metodo che viene chiamato al click del bottone
    public void myTap(View view) {
        Log.d("Sono Main Activity", "myTap");

        //Creo un nuovo Intent: due parametri. Un Context (in questo caso this) e una classe (l'attività da far partire)
        Intent intent=new Intent(this, NuovaActivity.class);

        // Prendo il valore del testo e lo converto in stringa
        EditText editText=(EditText) findViewById(R.id.editText);
        String message=editText.getText().toString();

        // Aggiunge il testo del messaggio all'intent. Un Intent può contenere dati come coppia di chiavi chiamate extras
        intent.putExtra(EXTRA_MESSAGE,message);

        //Creo un'istanza della nuova attività
        startActivity(intent);
    }
}
