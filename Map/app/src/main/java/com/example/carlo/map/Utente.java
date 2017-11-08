package com.example.carlo.map;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by carlo on 08/11/2017.
 */

public class Utente {

    private String nome;
    private double lat;
    private double lon;

    public Utente(String nome, double lat, double lon) {
        this.nome = nome;
        this.lat = lat;
        this.lon = lon;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
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

    public LatLng getLatLon (){
        return new LatLng (this.getLat(),this.getLon());
    }
}
