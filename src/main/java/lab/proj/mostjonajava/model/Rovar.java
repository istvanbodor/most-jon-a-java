package lab.proj.mostjonajava.model;

import java.util.ArrayList;
import java.util.List;

import static lab.proj.mostjonajava.utils.Logger.log;
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
        this.id = nextId++;
        this.honnan = tekton;
        this.rovarasz = rovarasz;
        log("Rovar letrejott");
    }

    /**
     * A rovar lepeseert felelos egyik tektonrol a masikra.
     * Feltételek: legyen a két tekton között fonál és ne legyen a rovar lebénulva.
     * @param hova
     */
    public void lepes(Tekton hova) {
        if (!honnan.vanFonalKozottuk(hova) || benulas) {
            log("A lepes sikertelen");
            return;
        }
        honnan.getRovarok().remove(this);
        hova.getRovarok().add(this);
        honnan = hova;
        sporaElfogyasztas(hova);
        log("Sikeresen lepett a rovar");
    }

    /**
     * Spora elfogyasztasa.
     * Feltételek: legyen a tektonon spóra.
     * @param tekton
     */
    public void sporaElfogyasztas(Tekton tekton) {
        if (!tekton.getSporak().isEmpty()) {
            List<Spora> sporak = tekton.getSporak();
            Spora utolsoSpora = sporak.get(sporak.size() - 1);
            List<Spora> torlendoSporak = new ArrayList<>(sporak);
            for (Spora spora : torlendoSporak) {
                rovarasz.setPont(rovarasz.getPont() + spora.getTapanyag());
                tekton.getSporak().remove(spora);
            }
            utolsoSpora.hatasKifejtese(this);
            log("A rovar megette a sporat / sporakat");
        }
    }

    /**
     * Elvagja a fonalat a kijelolt tekton es az a tekton kozott, ahol a rovar all.
     * Feltételek: legyen a kiválasztott tekton és ad adott tekton között fonál illetve tudjon a rovar fonalat vágni.
     * @param hova
     */
    public void fonalVagas(Tekton hova) {
        if (!honnan.vanFonalKozottuk(hova) || !vagoKepesseg) {
            log("Fonal vagas sikertelen");
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
        log("Fonalvagas vege: fonal(ak) sikeresen torolve.");
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
        return vagoKepesseg;
    }

    /**
     * Beállítjuk a rovar vágóképességét.
     * @param ertek
     */
    public void setVagoKepesseg(boolean ertek) {
        this.vagoKepesseg = ertek;
        log("A rovar elvesztette a vagokepesseget");
    }

    /**
     * Visszatér azzal, hogy a rovar bénult-e.
     * @return 
     */
    public boolean getBenulas(){
        return benulas;
    }

    /**
     * Beállítjuk a rovar bénulási állapotával.
     * @param ertek
     */
    public void setBenulas(boolean ertek) {
        this.benulas = ertek;
        log("A rovar megbenult");
    }

    /**
     * Visszatér a rovar rovarászával.
     * @return 
     */
    public Rovarasz getRovarasz(){
        return rovarasz;
    }

    /**
     * Visszatér azzal a tektonnal, amelyiken a rovar jeleneg van.
     * @return 
     */
    public Tekton getTekton(){
        return honnan;
    }

    /**
     * Visszatér a rovar lépésszámával.
     * @return 
     */
    public int getLepesSzam(){
        return lepesSzam;
    }

    /**
     * Beállítja a rovar lépésszámát.
     * @param ertek 
     */
    public void setLepesSzam(int ertek){
        lepesSzam = ertek;
    }

    /**
     * Minden kör végén resetelődik a lépések száma, bénulási és fonal vágási képesség.
     */
    public void korFrissites() {
        benulas = false;
        vagoKepesseg = true;
        lepesSzam = 2; 
    }
}

