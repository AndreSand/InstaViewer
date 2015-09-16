package andres_sjsu.mylistdemo;

import java.util.ArrayList;

/**
 * Created by andres on 9/14/15.
 */
public class Contact {
    public String name;
    public String number;
    public String homeTown;

    public Contact(String name, String number, String homeTown) {
        this.name = name;
        this.number = number;
        this.homeTown = homeTown;
    }

    //Contact.getFakeUsers();
    public static ArrayList<Contact> getFakeUsers(){
        ArrayList<Contact> contacts =new ArrayList<>();

        contacts.add(new Contact("Andres","4081231","San Jose"));
        contacts.add(new Contact("Marco","222333","Seattle"));
        contacts.add(new Contact("Nico","12345","Long Beach"));

        return contacts;

    }


}
