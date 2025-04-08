package lab.proj.mostjonajava.model;

import java.util.ArrayList;
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
    private Tekton honnan;
    private Rovarasz rovarasz;
    private int lepesSzam = 2;
    private boolean vagoKepesseg = true;
    private boolean benulas = false;

    /**
     * Rovar konstruktora.
     * @param nev
     */
    public Rovar(Tekton tekton, Rovarasz rovarasz) {
        this.id = nextId++;
        this.honnan = tekton;
        this.rovarasz = rovarasz;
        tekton.setRovar(this);
        rovarasz.setRovar(this);
        log("Rovar letrejott");
    }

    /**
     * A rovar lepeseert felelos egyik tektonrol a masikra.
     * @param tekton
     */
    public void lepes(Tekton hova) {
        hivasLog("lepes(Tekton hova)", List.of("tekton: Tekton - " + honnan.toString()), 0);
        if (!honnan.vanFonalKozottuk(hova)) {
            log("Nem koti ossze fonal a tektonokat, a rovar nem tud lepni koztuk");
            return;
        }
        honnan.rovarTorlese(this);
        hova.setRovar(this);
        this.setTekton(hova);
        log("Sikeresen lepett a rovar");
        sporaElfogyasztas(hova);
    }

    /**
     * Spora elfogyasztasa.
     * @param tekton
     */
    public void sporaElfogyasztas(Tekton tekton) {
        hivasLog("sporaElfogyasztas(Tekton tekton)", List.of("tekton: Tekton - " + tekton.toString()), 0);
        if (tekton.getSporaSzam() > 0) {
            List<Spora> sporak = tekton.getSporak();
            Spora utolsoSpora = sporak.get(sporak.size() - 1);
            for (Spora spora : sporak) {
                rovarasz.setPont(rovarasz.getPont() + spora.getTapanyagErtek());
                tekton.sporaTorlese(spora);
            }
            utolsoSpora.hatasKifejtese(this);
            log("A rovar megette a sporat / sporakat");
        }
    }

    public void setLepesSzam(int lepes){ this.lepesSzam = lepes; }
    public int getLepesSzam(){ return lepesSzam; }

    public void setTekton(Tekton tekton){ this.honnan = tekton; }
    public Tekton getTekton() {
        hivasLog("getTekton()", List.of(), 0);
        log("Rovar tektonjanak lekerese");
        return honnan;
    }

    public void setRovarasz(Rovarasz rovarasz){ this.rovarasz = rovarasz; }
    public Rovarasz getRovarasz(){ return rovarasz; }

    /**
     * Nem engedi, hogy a rovar vagjon.
     */
    public void setVagoKepesseg(boolean ertek) {
        this.vagoKepesseg = ertek;
    }

    public void setBenulas(boolean ertek) {
        hivasLog("setBenulas(boolean ertek)", List.of(), 0);
        this.benulas = ertek;
        log("A rovar megbenult");
    }

    /**
     * Elvagja a fonalat a kijelolt tekton es az a tekton kozott,
     * ahol a rovar all.
     * @param tekton
     */
    public void fonalVagas(Tekton hova) {
        hivasLog("fonalVagas(Tekton tekton)", List.of("tekton: Tekton - " + hova.toString()), 0);
        if (!honnan.vanFonalKozottuk(hova)) {
            log("Nem letezik fonal a ket tekton kozott, nem lehet vagni.");
            return;
        }
        // A két tekton közti konkrét fonal megkeresése
        GombaFonal torlendoFonal = null;
        for (GombaFonal fonal : honnan.getGombafonalak()) {
            if ((fonal.getHonnan().equals(honnan) && fonal.getHova().equals(hova)) ||
                (fonal.getHova().equals(honnan) && fonal.getHonnan().equals(hova))) {
                torlendoFonal = fonal;
                break;
            }
        }
        if (torlendoFonal == null) {
            log("Nem talalhato konkret GombaFonal a ket tekton kozott.");
            return;
        }
        // Fonal törlése a tektonokból
        honnan.fonalTorlese(torlendoFonal);
        hova.fonalTorlese(torlendoFonal);
        torlendoFonal.getGombatest().fonalTorles(torlendoFonal);
        log("Fonalvagas vege: fonal(ak) sikeresen torolve.");
    }


}

