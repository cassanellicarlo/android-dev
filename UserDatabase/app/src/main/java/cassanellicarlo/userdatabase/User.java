package cassanellicarlo.userdatabase;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by carlo on 26/10/2017.
 */

@Entity
public class User {

    @PrimaryKey
    private int id;

    @ColumnInfo(name = "nome")
    private String nome;

    @ColumnInfo(name = "cognome")
    private String cognome;

    public User (int id, String nome, String cognome){
        this.id=id;
        this.nome=nome;
        this.cognome=cognome;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCognome() {
        return cognome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }
}