package cassanellicarlo.contacts;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import org.w3c.dom.Text;

public class ContactDetails extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_details);

        Intent intent=getIntent();

        Contact contatto= (Contact) intent.getSerializableExtra("Contact");

        TextView t1=(TextView)findViewById(R.id.nomeContatto);
        t1.setText(contatto.getUsername());

        TextView t2=(TextView)findViewById(R.id.distanzaContatto);
        t2.setText(contatto.getDistance()+" km");

        TextView t3=(TextView)findViewById(R.id.giorniContatto);
        t3.setText(contatto.getDays() +" giorni fa");

    }
}
