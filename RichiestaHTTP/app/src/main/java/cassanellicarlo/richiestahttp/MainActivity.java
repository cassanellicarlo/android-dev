package cassanellicarlo.richiestahttp;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void richiesta(View view) {

        new httpRequest().execute();
    }


    private class httpRequest extends AsyncTask<Void, Void, String> {
        @Override
        protected String doInBackground(Void... voids) {

            String url = "http://mobidev2014.appspot.com/helloworld";
            URL requestURL = null;
            try {
                requestURL = new URL(url);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            HttpURLConnection conn = null;
            try {
                conn = (HttpURLConnection) requestURL.openConnection();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                conn.setRequestMethod("GET");
            } catch (ProtocolException e) {
                e.printStackTrace();
            }
            conn.setDoInput(true);
            InputStream is = null;
            try {
                is = conn.getInputStream();
            } catch (IOException e) {
                e.printStackTrace();
            }
            BufferedReader rd = new BufferedReader(new InputStreamReader(is));
            String line;
            StringBuffer response = new StringBuffer();
            try {
                while ((line = rd.readLine()) != null) {
                    response.append(line);
                    response.append('\r');
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                rd.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            String result = response.toString();
            return result;
        }

        protected void onPostExecute(String result) {
            TextView myTextView = (TextView) findViewById(R.id.risultato);

            myTextView.setText(result);

        }
    }
}
