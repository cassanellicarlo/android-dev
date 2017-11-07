package cassanellicarlo.myapplication;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class NuovaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nuova);

        // Prendo l'intent che ha iniziato questa attività e estraggo la stringa
        Intent intent = getIntent();
        String message = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);

        // Prendo la TextView del layout e setto la stringa come suo testo
        TextView textView = (TextView) findViewById(R.id.textView);
        textView.setText(message);



    }

    public void chiama(View view) {
        String number="119";
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:" +number));
        startActivity(intent);

    }
}
