package cassanellicarlo.contacts;

import java.util.ArrayList;

/**
 * Created by carlo on 03/11/2017.
 */

public class ListOfContacts extends ArrayList<Contact> {

    public ListOfContacts (){
        super();

        this.add(new Contact ("Carlo",5.3,1));
        this.add(new Contact ("Marco",5.8,2));
        this.add(new Contact ("Giovanni",5.9,3));
        this.add(new Contact ("Paolo",6,5));
        this.add(new Contact ("Carlo",7.4,5));
        this.add(new Contact ("Mariangela",7.5,6));
        this.add(new Contact ("Linda",8,7));
        this.add(new Contact ("Elisabetta",8.3,8));
        this.add(new Contact ("Francesco",9.3,9));
        this.add(new Contact ("Matteo",9.9,10));


    }
}
