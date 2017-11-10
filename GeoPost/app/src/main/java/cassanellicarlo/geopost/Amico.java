package cassanellicarlo.geopost;

/**
 * Created by carlo on 09/11/2017.
 */

public class Amico {

    private String username;
    private String msg;
    private double lat;
    private double lon;

    public Amico(String username, String msg, double lat, double lon) {
        this.username = username;
        this.msg = msg;
        this.lat = lat;
        this.lon = lon;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }
}