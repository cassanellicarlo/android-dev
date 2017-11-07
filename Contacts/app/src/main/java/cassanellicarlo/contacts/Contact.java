package cassanellicarlo.contacts;

import java.io.Serializable;

/**
 * Created by carlo on 03/11/2017.
 */

public class Contact implements Serializable{

    private String username;
    private double distance;
    private int days;

    public Contact(String username, double distance, int days) {
        this.username = username;
        this.distance = distance;
        this.days = days;
    }


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public int getDays() {
        return days;
    }

    public void setDays(int days) {
        this.days = days;
    }
}
