package lab.proj.mostjonajava.model;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

import static lab.proj.mostjonajava.utils.Logger.hivasLog;
import static lab.proj.mostjonajava.utils.Logger.log;

@Data
public class Gombatest {
    private static int nextId = 1;
    private int id;
    private Tekton tekton;
    private List<GombaFonal> gombaFonalak;
    private int kilohetoSporakSzama;
    private int elszortSporakSzama;
    private int noveszthetoFonalakSzama;

    /**
     * A gombatest konstruktora
     */
    public Gombatest() {
        //   id = nextId++;
    }

    public int getKilohetoSporakSzama() {
        hivasLog("getKilohetoSporakSzama()", List.of(), 0);
        log("Kiloheto sporak szamanak ellenorzese");
        return (int) Math.floor(Math.random() * 3);
    }

    /**
     * Egy új fonalat növeszt.
     *
     * @param honnan
     * @param hova
     */
    public void fonalNovesztes(Tekton honnan, Tekton hova) {
        hivasLog("fonalNovesztes(Tekton honnan, Tekton hova)", List.of("honnan: Tekton - " + honnan.toString(), "hova: Tekton - " + hova.toString()), 0);
    }

    /**
     * Megsemmisíti a gombatestet, a fonalakkal együtt.
     */
    public void elpusztulas() {
        hivasLog("elpusztulas()", List.of(), 0);
        log("A gombatest elpusztult");
    }

    /**
     * Spórákat lő ki.
     *
     * @param tekton
     */
    public void sporaKiloves(Tekton tekton) {
        hivasLog("sporaKiloves(Tekton tekton)", List.of("tekton: Tekton - " + tekton.toString()), 0);
        log("Gombatest spora kilovese megtortent");
        tekton.sporaHozzaadasa(new SimaSpora());
    }

    /**
     * Minden körben spoórát termel.
     */
    public void sporaTermeles() {
        hivasLog("sporaTermeles()", List.of(), 0);
        log("Spora termeles megtortent");
    }

    /**
     * A kilőtt spórákat törli a gombatestből.
     *
     * @param spora
     */
    public void sporaTorleseGombatestbol(Spora spora) {
    }

    /**
     * Gombatest konstruktora
     *
     * @param tekton
     * @return
     */
    public Gombatest ujGombatestLetrehozas(Tekton tekton) {
        return null;
    }

    public void fonalTorles(GombaFonal fonal) {
        hivasLog("fonalTorles(GombaFonal fonal)", List.of("fonal: Gombafonal"), 2);
        log("Fonal torles gombatestbol sikeres volt");
    }
}
