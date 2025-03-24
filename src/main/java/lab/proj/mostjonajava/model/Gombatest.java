package lab.proj.mostjonajava.model;

import lombok.Data;

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
        log("Gombatest letrejott");
    }

    /**
     * Egy új fonalat növeszt.
     * @param honnan
     * @param hova
     */
    public void fonalNovesztes(Tekton honnan, Tekton hova) {}

    /**
     * Megsemmisíti a gombatestet, a fonalakkal együtt.
     */
    public void elpusztulas() {
        log("Elpusztulas sikeres.");
    }

    /**
     * Spórákat lő ki.
     * @param tekton
     */
    public void sporaKiloves(Tekton tekton) {}

    /**
     * Minden körben spoórát termel.
     */
    public void sporaTermeles() {
        //hivasLog("sporaTermeles()", List.of(), 0);
        log("Spora termeles megtortent");
    }

    /**
     * A kilőtt spórákat törli a gombatestből.
     * @param spora
     */
    public void sporaTorleseGombatestbol(Spora spora) {}

    /**
     * Gombatest konstruktora
     * @param tekton
     * @return
     */
    public Gombatest ujGombatestLetrehozas(Tekton tekton) { return null; }
}
