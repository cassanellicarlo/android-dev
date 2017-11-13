package it.unimi.di.ewlab.mc.debugme1;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/*
Il codice di questa classe è VOLUTAMENTE scritto in modo disordinato
Questo rende più complesso trovare gli errori
Se volete, potete rendere il codice più ordinato, ma questo è opzionale.
Lo scopo è trovare 2 errori gravi:
- prima testate l'applicazione, per far emergere gli errori
- poi correggeteli
 */

public class MoltiplicaActivity extends AppCompatActivity implements View.OnClickListener{


    private EditText mEditText = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_moltiplica);
        mEditText = (EditText)findViewById(R.id.editText);

        mEditText.setText(Numero.getIstance().getValoreString());

        ((Button)findViewById(R.id.btn1)).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if(checkInput()){
                    String input=mEditText.getText().toString();
                    int i=Integer.parseInt(input)*2;
                    String s=Integer.toString(i);
                    mEditText.setText(s);

                    Numero.getIstance().setValore(i);
                }

            }
        });

        ((Button)findViewById(R.id.btn2)).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(checkInput()){
            String input=mEditText.getText().toString();
            int i=Integer.parseInt(input)*10;
            String s=Integer.toString(i);
            mEditText.setText(s);

            Numero.getIstance().setValore(i);
        }

    }

    public boolean checkInput (){

       if( TextUtils.isEmpty(mEditText.getText().toString()) ){
           Context context = getApplicationContext();
           CharSequence text = "Devi inserire un numero";
           int duration = Toast.LENGTH_LONG;

           Toast toast = Toast.makeText(context, text, duration);
           toast.show();
           return false;
       }

       else return true;
    }

    public void somma(View view) {

        // Al click del bottone somma faccio partire l'altra activity
        Intent intent=new Intent(this,MainActivity.class);
        startActivity(intent);
    }
}
