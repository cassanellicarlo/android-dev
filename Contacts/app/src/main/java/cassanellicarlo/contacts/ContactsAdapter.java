package cassanellicarlo.contacts;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by carlo on 03/11/2017.
 */

public class ContactsAdapter extends ArrayAdapter<Contact>{

    public ContactsAdapter(@NonNull Context context, int resource) {
        super(context, resource);
    }

    public ContactsAdapter(@NonNull Context context, int resource, int textViewResourceId) {
        super(context, resource, textViewResourceId);
    }

    public ContactsAdapter(@NonNull Context context, int resource, @NonNull Contact[] objects) {
        super(context, resource, objects);
    }

    public ContactsAdapter(@NonNull Context context, int resource, int textViewResourceId, @NonNull Contact[] objects) {
        super(context, resource, textViewResourceId, objects);
    }

    public ContactsAdapter(@NonNull Context context, int resource, @NonNull List<Contact> objects) {
        super(context, resource, objects);
    }

    public ContactsAdapter(@NonNull Context context, int resource, int textViewResourceId, @NonNull List<Contact> objects) {
        super(context, resource, textViewResourceId, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View v=convertView;

        if(v==null){
            LayoutInflater vi;
            vi=LayoutInflater.from(getContext());
            v=vi.inflate(R.layout.list_contacts,null);
        }

        Contact p=getItem(position);

        if(p!=null){
            TextView tt1=(TextView)v.findViewById(R.id.username);
            TextView tt2=(TextView)v.findViewById(R.id.distance);
            TextView tt3=(TextView)v.findViewById(R.id.days);

            tt1.setText(p.getUsername());
            tt2.setText(p.getDistance()+" km");
            tt3.setText(p.getDays() +" giorni fa");
        }

        return v;
    }
}
