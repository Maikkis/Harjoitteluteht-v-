package haroittelutehtava;

import java.util.ArrayList;
import java.util.List;

public class Annos {

    Integer id;
    String nimi;
    List<RaakaAine> raakaAineet;

    public Annos(Integer id, String nimi) {
        this.id = id;
        this.nimi = nimi;
        this.raakaAineet = new ArrayList<>();
    }

    public Integer getId() {
        return id;
    }

    
    public String getNimi() {
        return nimi;
    }

    public List<RaakaAine> getRaakaAineet() {
        return raakaAineet;
    }

    @Override
    public String toString() {
        return super.toString(); //To change body of generated methods, choose Tools | Templates.
    }

}
