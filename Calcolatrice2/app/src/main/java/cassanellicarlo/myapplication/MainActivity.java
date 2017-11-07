package cassanellicarlo.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText value;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        value = (EditText) findViewById(R.id.numero);

        ((Button) findViewById(R.id.aggiungi)).setOnClickListener(this);
        ((Button) findViewById(R.id.sottrai)).setOnClickListener(this);
        ((Button) findViewById(R.id.moltiplica)).setOnClickListener(this);
        ((Button) findViewById(R.id.eleva)).setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        int num = Integer.parseInt(value.getText().toString());

        switch(v.getId()){
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
