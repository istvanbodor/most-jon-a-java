package lab.proj.mostjonajava.model;

import lab.proj.mostjonajava.utils.Logger;
import lombok.Data;

import java.util.List;

@Data
public class Rovar extends Jatekos {
    private Tekton tekton;
    private int lepesSzam;
    private boolean vagoKepesseg;
    private boolean benulas;

    public Rovar(String nev) {
        super(nev);
        Logger.hivasLog("Rovar(String nev)", List.of("nev: String - " + nev));
        tekton = null;
        lepesSzam = 2;
        vagoKepesseg = true;
        benulas = false;
    }

    public Tekton tektonKivalasztasa() {
        return null;
    }

    public void pontNovelese(int ertek) {
    }

    public void lepes(Tekton tekton) {
    }

    public void sporaElfogyasztas(Tekton tekton) {
    }

    public void lepesSzamNoveles() {
    }

    public void lepesSzamCsokkentes() {
    }

    public void vagoKepessegTiltas() {
    }

    public void benulasEllenorzes() {
    }

    public void vagasEllenorzes() {
    }

    public void fonalVagas(Tekton tekton) {
    }
}
