package it.unimi.di.ewlab.mc.debugme1;

/**
 * Created by carlo on 13/11/2017.
 */

public class Numero {

    private int valore;
    private static Numero istanza;

    private Numero (){

    }

    public static Numero getIstance (){

        if(istanza==null){
            istanza = new Numero();
        }

        return istanza;
    }

    public int getValore() {
        return valore;
    }

    public String getValoreString (){
        return String.valueOf(valore);
    }

    public void setValore(int valore) {
        this.valore = valore;
    }
}
