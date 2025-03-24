package lab.proj.mostjonajava.model;

import lab.proj.mostjonajava.utils.Logger;
import lombok.Data;

import java.util.List;

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

    public Gombatest() {
        id = nextId++;
        log("Gombatest letrejott - GOMBATEST_ID: " + id);
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
    public void elpusztulas() {}

    /**
     * Spórákat lő ki.
     * @param tekton
     */
    public void sporaKiloves(Tekton tekton) {}

    /**
     * Minden körben spoórát termel.
     */
    public void sporaTermeles() {}

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
