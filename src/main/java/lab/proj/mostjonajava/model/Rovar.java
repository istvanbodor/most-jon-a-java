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

    public Rovar(String nev) {
        super(nev);
        hivasLog("Rovar(String nev)", List.of("nev: String - " + nev), 1);
        tekton = null;
        lepesSzam = 2;
        vagoKepesseg = true;
        benulas = false;
        id = nextId++;
        log("Rovar letrejott - ROVAR_ID: " + id);
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
