package cassanellicarlo.myapplication;

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

    public void eval(View view) {
        EditText value = (EditText) findViewById(R.id.numero);

        int num = Integer.parseInt(value.getText().toString());

        switch(view.getId()){
            case R.id.aggiungi:
                num++;
                break;

            case R.id.sottrai:
                num--;
                break;

            case R.id.moltiplica:
                num*=2;
                break;

            case R.id.eleva:
                num*=num;
                break;
        }

        value.setText(""+num);

    }

}
