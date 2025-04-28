package lab.proj.mostjonajava.model;

import java.util.ArrayList;
import java.util.List;

//import static lab.proj.mostjonajava.utils.Logger.hivasLog;
//import static lab.proj.mostjonajava.utils.Logger.log;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

//@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class Rovar {

    public static int nextId = 1;
    private int id;

    @ToString.Exclude
    private Rovarasz rovarasz;
    private Tekton honnan;
    private int lepesSzam = 2;
    private boolean vagoKepesseg = true;
    private boolean benulas = false;

    /**
     * Rovar konstruktora.
     * @param tekton
     * @param rovarasz
     */
    public Rovar(Tekton tekton, Rovarasz rovarasz) {
        //hivasLog("Rovar(Tekton tekton, Rovarasz rovarasz)", List.of("tekton: Tekton - " + id + "rovarasz: Rovarasz - " + id), 1);
        this.id = nextId++;
        this.honnan = tekton;
        this.rovarasz = rovarasz;
        //log("Rovar letrejott");
    }

    /**
     * A rovar lepeseert felelos egyik tektonrol a masikra.
     * Feltételek: legyen a két tekton között fonál és ne legyen a rovar lebénulva.
     * @param hova
     */
    public void lepes(Tekton hova) {
        //hivasLog("lepes(Tekton hova)", List.of("tekton: Tekton - " + honnan.toString()), 0);
        if (!honnan.vanFonalKozottuk(hova) || benulas) {
            //log("A lepes sikertelen");
            return;
        }
        honnan.getRovarok().remove(this);
        hova.getRovarok().add(this);
        honnan = hova;
        sporaElfogyasztas(hova);
        //log("Sikeresen lepett a rovar");
    }

    /**
     * Spora elfogyasztasa.
     * Feltételek: legyen a tektonon spóra.
     * @param tekton
     */
    public void sporaElfogyasztas(Tekton tekton) {
        //hivasLog("sporaElfogyasztas(Tekton tekton)", List.of("tekton: Tekton - " + tekton.toString()), 0);
        if (!tekton.getSporak().isEmpty()) {
            List<Spora> sporak = tekton.getSporak();
            Spora utolsoSpora = sporak.get(sporak.size() - 1);
            List<Spora> torlendoSporak = new ArrayList<>(sporak);
            for (Spora spora : torlendoSporak) {
                rovarasz.setPont(rovarasz.getPont() + spora.getTapanyag());
                tekton.getSporak().remove(spora);
            }
            utolsoSpora.hatasKifejtese(this);
            //log("A rovar megette a sporat / sporakat");
        }
    }

    /**
     * Elvagja a fonalat a kijelolt tekton es az a tekton kozott, ahol a rovar all.
     * Feltételek: legyen a kiválasztott tekton és ad adott tekton között fonál illetve tudjon a rovar fonalat vágni.
     //* @param tekton
     */
    public void fonalVagas(Tekton hova) {
        //hivasLog("fonalVagas(Tekton tekton)", List.of("tekton: Tekton - " + hova.toString()), 0);
        if (!honnan.vanFonalKozottuk(hova) || !vagoKepesseg) {
            //log("Fonal vagas sikertelen");
            return;
        }

        GombaFonal torlendoFonal = null;
        for (GombaFonal fonal : honnan.getGombafonalak()) {
            if ((fonal.getHonnan().equals(honnan) && fonal.getHova().equals(hova)) || (fonal.getHova().equals(honnan) && fonal.getHonnan().equals(hova))) {
                torlendoFonal = fonal;
                break;
            }
        }
        if (torlendoFonal == null) {  return; }

        torlendoFonal.getGombatest().fonalTorles(torlendoFonal);
        //log("Fonalvagas vege: fonal(ak) sikeresen torolve.");
    }

    /**
     * Visszatér a rovar id-jával.
     * @return 
     */
    public int getId(){ return id; }

    /**
     * Visszatér a rovar vágóképességével.
     * @return 
     */
    public boolean getVagoKepesseg(){
        //hivasLog("getVagoKepesseg()", List.of(), 1);
        return vagoKepesseg;
    }

    /**
     * Beállítjuk a rovar vágóképességét.
     * @param ertek
     */
    public void setVagoKepesseg(boolean ertek) {
        //hivasLog("setVagoKepesseg(boolean ertek)", List.of("ertek: boolean"), 1);
        this.vagoKepesseg = ertek;
        //log("A rovar elvesztette a vagokepesseget");
    }

    /**
     * Visszatér azzal, hogy a rovar bénult-e.
     * @return 
     */
    public boolean getBenulas(){
        //hivasLog("getBenulas()", List.of(), 1);
        return benulas;
    }

    /**
     * Beállítjuk a rovar bénulási állapotával.
     * @param ertek
     */
    public void setBenulas(boolean ertek) {
        //hivasLog("setBenulas(boolean ertek)", List.of("ertek: boolean"), 1);
        this.benulas = ertek;
        //log("A rovar megbenult");
    }

    /**
     * Visszatér a rovar rovarászával.
     * @return 
     */
    public Rovarasz getRovarasz(){
       // hivasLog("getRovarasz()", List.of(), 1);
        return rovarasz;
    }

    /**
     * Visszatér azzal a tektonnal, amelyiken a rovar jeleneg van.
     * @return 
     */
    public Tekton getTekton(){
        //hivasLog("getTekton()", List.of(), 1);

        return honnan;
    }

    /**
     * Visszatér a rovar lépésszámával.
     * @return 
     */
    public int getLepesSzam(){
        //hivasLog("getLepesSzam()", List.of(), 1);
        return lepesSzam;
    }

    /**
     * Beállítja a rovar lépésszámát.
     * @param ertek 
     */
    public void setLepesSzam(int ertek){
        //hivasLog("setLepesSzam(int ertek)", List.of("ertek: int"), 1);
        lepesSzam = ertek;
    }

    /**
     * Minden kör végén resetelődik a lépések száma, bénulási és fonal vágási képesség.
     */
    public void korFrissites() {
       // hivasLog("korFrissites()", List.of(), 0);
        benulas = false;
        vagoKepesseg = true;
        lepesSzam = 2; 
    }
}

