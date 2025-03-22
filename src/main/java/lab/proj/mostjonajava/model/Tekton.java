package lab.proj.mostjonajava.model;

import java.util.List;

abstract class Tekton {

    private List<Tekton> szomszedosTektonok;

    private Rovar rovar;

    private Gombasz gombasz;

    private List<GombaFonal> gombafonalak;

    private Gombatest gombatest;

    private List<Spora> sporak;
    private int sporaSzam;
    private int fonalakElettartama;

    public abstract Tekton ujTektonLetrehozasa();
    public abstract void ketteTores();
    public boolean vanFonalKozottuk(Tekton tekton) { return false; }
    public void fonalHozzaadasa(GombaFonal fonal) {}
    public void fonalTorlese(GombaFonal fonal) {}
    public void mindenFonalElszakadasa() {}
    public void sporaHozzaadasa(Spora spora) {}
    public void sporaTorlese(Spora spora) {}
    public void eletIdoCsokkentes() {}
    public boolean gombafonalNoveszthetoE() { return false; }
    public boolean gombatestNoveszthetoE() { return false; }
    public void rovarMozgasa(Tekton tekton) {}
    public boolean egyenlo(Tekton tekton) { return false; }
    public int getSporaSzam() { return sporaSzam; }
    public int getFonalElettartama() { return fonalakElettartama; }
}
