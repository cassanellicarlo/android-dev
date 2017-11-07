package cassanellicarlo.contacts;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Creo un nuovo Intent
        final Intent intent=new Intent(this,ContactDetails.class);

        final ListOfContacts contatti=new ListOfContacts();

        ContactsAdapter myAdapter=new ContactsAdapter(this,
                android.R.layout.list_content, contatti );

        ListView listView=(ListView)findViewById(R.id.contactList);
        listView.setAdapter(myAdapter);

        listView.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Contact c=contatti.get(position);

                        Log.d("Item Click Listener",c.getUsername()+" -"+c.getDistance()+" - "+c.getDays());

                        intent.putExtra("Contact",c);

                        startActivity(intent);
                    }
                }
        );
    }
}
