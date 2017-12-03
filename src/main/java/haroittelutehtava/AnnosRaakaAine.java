
package haroittelutehtava;

public class AnnosRaakaAine {
    
    Integer annosId;
    Integer raakaAineId;
    Integer jarjestys;
    Integer maara;
    String ohje;
    
    public AnnosRaakaAine(Integer annosId, Integer raakaAineId, Integer jarjestys, Integer maara, String ohje){
        this.annosId = annosId;
        this.raakaAineId = raakaAineId;
        this.jarjestys = jarjestys;
        this.maara = maara;
        this.ohje = ohje;
    }



    public Integer getAnnosId() {
        return annosId;
    }

    public Integer getRaakaAineId() {
        return raakaAineId;
    }

    public Integer getJarjestys() {
        return jarjestys;
    }

    public Integer getMaara() {
        return maara;
    }

    public String getOhje() {
        return ohje;
    }
    
}
