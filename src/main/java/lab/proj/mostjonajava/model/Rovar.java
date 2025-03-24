package lab.proj.mostjonajava.model;

import lab.proj.mostjonajava.utils.Logger;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

import static lab.proj.mostjonajava.utils.Logger.hivasLog;
import static lab.proj.mostjonajava.utils.Logger.log;

@EqualsAndHashCode(callSuper = true)
@Data
public class Rovar extends Jatekos {
    private static int nextId = 1;
    private int id;
    private Tekton tekton;
    private int lepesSzam;
    private boolean vagoKepesseg;
    private boolean benulas;

    /**
     * Rovar konstruktora.
     * @param nev
     */
    public Rovar(String nev) {
        super(nev);
        hivasLog("Rovar(String nev)", List.of("nev: String - " + nev), 1);
//        tekton = null;
//        lepesSzam = 2;
//        vagoKepesseg = true;
//        benulas = false;
//        id = nextId++;
        log("Rovar letrejott");
    }

    /**
     * Kivalaszt egy tektont.
     * @return
     */
    public Tekton tektonKivalasztasa() {
        return null;
    }

    /**
     * Noveli a jatekos pontszamat a megadott ertekkel.
     * @param ertek
     */
    public void pontNovelese(int ertek) {
        log("Novekedett a rovar pontszama.");
    }

    /**
     * A rovar lepeseert felelos egyik tektonrol a masikra.
     * @param tekton
     */
    public void lepes(Tekton tekton) {
    }

    /**
     * Spora elfogyasztasa.
     * @param tekton
     */
    public void sporaElfogyasztas(Tekton tekton) {
        log("A rovar megette a sporat.");
    }

    /**
     * A rovar lepes szamat csokkenti, azaz hanyat léphet.
     */
    public void lepesSzamNoveles() {
        log("A rovar gyorsult.");
    }

    /**
     * A rovar lepes szamat noveli, azaz hanyat léphet.
     */
    public void lepesSzamCsokkentes() {
        log("A rovar lassult.");
    }

    /**
     * Nem engedi, hogy a rovar vagjon.
     */
    public void vagoKepessegTiltas() {
        log("A rovar nem tud vagni.");
    }

    /**
     * Ellenorzi, hogy a rovar benult allapotban van-e.
     */
    public void benulasEllenorzes() {
    }

    /**
     * Ellenorzi, hogy a rovar tud-e vagni.
     */
    public void vagasEllenorzes() {
    }

    /**
     * Elvagja a fonalat a kijelolt tekton es az a tekton kozott,
     * ahol a rovar all.
     * @param tekton
     */
    public void fonalVagas(Tekton tekton) {
    }

    /**
     * Lebenitja a rovart.
     */
    public void benulas(){
        log("A rovar megbenult.");
    }
}
