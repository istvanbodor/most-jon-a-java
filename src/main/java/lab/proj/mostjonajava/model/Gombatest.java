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

    public Gombatest() {
     //   id = nextId++;
        log("Gombatest letrejott");
    }

    public void fonalNovesztes(Tekton honnan, Tekton hova) {
    }

    public void elpusztulas() {
    }

    public void sporaKiloves(Tekton tekton) {
    }

    public void sporaTermeles() {
        hivasLog("sporaTermeles()", List.of(), 0);
        log("Spora termeles megtortent");
    }

    public void sporaTorleseGombatestbol(Spora spora) {
    }

    public void sporaTarolasa() {
    }

    public Gombatest ujGombatestLetrehozas(Tekton tekton) {
        return null;
    }

    public int getKilohetoSporakSzama() {
        return kilohetoSporakSzama;
    }

    public int getElszortSporakSzama() {
        return elszortSporakSzama;
    }

    public int getNoveszthetoFonalakSzama() {
        return noveszthetoFonalakSzama;
    }
}
