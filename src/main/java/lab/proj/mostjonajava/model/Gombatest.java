package lab.proj.mostjonajava.model;

import lombok.Data;

import java.util.List;

@Data
public class Gombatest {
    private Tekton tekton;
    private List<GombaFonal> gombaFonalak;

    private int kilohetoSporakSzama;
    private int elszortSporakSzama;
    private int noveszthetoFonalakSzama;

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
