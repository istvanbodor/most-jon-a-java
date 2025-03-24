package lab.proj.mostjonajava.model;

import lombok.Data;

import java.util.List;

import static lab.proj.mostjonajava.utils.Logger.log;

@Data
public abstract class Tekton {

    private static int nextId = 1;

    private int id;
    private List<Tekton> szomszedosTektonok;

    private Rovar rovar;

    private Gombasz gombasz;

    private List<GombaFonal> gombafonalak;

    private Gombatest gombatest;

    private List<Spora> sporak;
    private int sporaSzam;
    private int fonalakElettartama;

    public Tekton() {
     //   id = nextId++;
        log("Tekton létrejött");
    }

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
