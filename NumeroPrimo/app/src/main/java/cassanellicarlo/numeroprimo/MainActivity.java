package cassanellicarlo.numeroprimo;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void click(View view) {
        EditText eT=(EditText) findViewById(R.id.numero);

        String value=eT.getText().toString();
        int num= Integer.parseInt(value);
        Log.d("Numero letto da click:",""+num);

        new numeroPrimo().execute(num);
    }

    private class numeroPrimo extends AsyncTask <Integer, Void, Boolean>{


        protected Boolean doInBackground(Integer... numero) {

            int num=numero[0];
            Log.d("Numero letto background",""+num);

            boolean notPrimo=false;
            for(int i=2;i<num-1;i++){
                if(num%i==0) notPrimo=true;
            }

            if(notPrimo) return false;
            else return true;


        }


        protected void onPostExecute (Boolean isPrimo) {
            TextView myTextView=(TextView)findViewById(R.id.risultato);

            if(isPrimo) myTextView.setText("Primo");
            else myTextView.setText("Non Primo");

        }
    }
}
