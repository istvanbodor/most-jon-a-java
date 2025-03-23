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

    public void fonalNovesztes(Tekton honnan, Tekton hova) {}
    public void elpusztulas() {}
    public void sporaKiloves(Tekton tekton) {}
    public void sporaTermeles() {}
    public void sporaTorleseGombatestbol(Spora spora) {}
    public void sporaTarolasa() {}
    public Gombatest ujGombatestLetrehozas(Tekton tekton) { return null; }
    public int getKilohetoSporakSzama() { return kilohetoSporakSzama; }
    public int getElszortSporakSzama() { return elszortSporakSzama; }
    public int getNoveszthetoFonalakSzama() { return noveszthetoFonalakSzama; }
}
