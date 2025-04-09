package lab.proj.mostjonajava.model;

import java.util.List;

import static lab.proj.mostjonajava.utils.Logger.hivasLog;
import static lab.proj.mostjonajava.utils.Logger.log;
import lombok.Data;
import lombok.NoArgsConstructor;
//@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class Rovar {

    private static int nextId = 1;
    private int id;

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
     * @param hova
     */
    public void lepes(Tekton hova) {
        hivasLog("lepes(Tekton hova)", List.of("tekton: Tekton - " + honnan.toString()), 0);
        if (!honnan.vanFonalKozottuk(hova)) {
            log("Nem koti ossze fonal a tektonokat, a rovar nem tud lepni koztuk");
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
     * @param tekton
     */
    public void sporaElfogyasztas(Tekton tekton) {
        hivasLog("sporaElfogyasztas(Tekton tekton)", List.of("tekton: Tekton - " + tekton.toString()), 0);
        if (!tekton.getSporak().isEmpty()) {
            List<Spora> sporak = tekton.getSporak();
            Spora utolsoSpora = sporak.get(sporak.size() - 1);
            for (Spora spora : sporak) {
                rovarasz.setPont(rovarasz.getPont() + spora.getTapanyagErtek());
                tekton.getSporak().remove(spora);
            }
            utolsoSpora.hatasKifejtese(this);
            log("A rovar megette a sporat / sporakat");
        }
    }

    /**
     * Elvagja a fonalat a kijelolt tekton es az a tekton kozott, ahol a rovar all.
     * @param tekton
     */
    public void fonalVagas(Tekton hova) {
        hivasLog("fonalVagas(Tekton tekton)", List.of("tekton: Tekton - " + hova.toString()), 0);
        if (!honnan.vanFonalKozottuk(hova)) {
            log("Nem letezik fonal a ket tekton kozott, nem lehet vagni.");
            return;
        }

        GombaFonal torlendoFonal = null;
        for (GombaFonal fonal : honnan.getGombafonalak()) {
            if ((fonal.getHonnan().equals(honnan) && fonal.getHova().equals(hova)) || (fonal.getHova().equals(honnan) && fonal.getHonnan().equals(hova))) {
                torlendoFonal = fonal;
                break;
            }
        }
        if (torlendoFonal == null) { log("Nem talalhato konkret GombaFonal a ket tekton kozott."); return; }

        honnan.fonalTorlese(torlendoFonal);
        hova.fonalTorlese(torlendoFonal);
        torlendoFonal.getGombatest().fonalTorles(torlendoFonal);
        log("Fonalvagas vege: fonal(ak) sikeresen torolve.");
    }

    /**
     * Visszatér a rovar vágóképességével.
     * @return 
     */
    public boolean getVagoKepesseg(){ return vagoKepesseg; }

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
    public boolean getBenulas(){ return benulas; }

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
    public Rovarasz getRovarasz(){ return rovarasz; }

    /**
     * Visszatér azzal a tektonnal, amelyiken a rovar jeleneg van.
     * @return 
     */
    public Tekton getTekton(){ return honnan; }

    /**
     * Visszatér a rovar lépésszámával.
     * @return 
     */
    public int getLepesSzam(){ return lepesSzam; }

    /**
     * Beállítja a rovar lépésszámát.
     * @param ertek 
     */
    public void setLepesSzam(int ertek){ lepesSzam = ertek; }

}

