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
        boolean feltetelekTeljesulnek = !honnan.vanFonalKozottuk(hova) && hova.gombafonalNoveszthetoE() && honnan.szomszedossagEllenorzese(hova);
        if(feltetelekTeljesulnek) {
            GombaFonal gombaFonal = new GombaFonal(honnan, hova, this);
            fonalHozzaadasa(gombaFonal);
            log("Teljesulnek a feltetelek");
        }
        else {
            log("Nem teljesulnek a fonalnovesztes feltetelei");
        }
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
        this.tekton = new EgyFonalasTekton();
        if (this.tekton.szomszedossagEllenorzese(tekton)) {
            tekton.sporaHozzaadasa(new SimaSpora());
        }
        else {
            log("A kivalasztott tekton nem szomszedja a gombatestet tartalmazo tektonnak");
        }
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

    /**
     * Fonal törlése
     * @param fonal
     */
    public void fonalTorles(GombaFonal fonal) {
        hivasLog("fonalTorles(GombaFonal fonal)", List.of("fonal: Gombafonal"), 2);
        log("Fonal torles gombatestbol sikeres volt");
    }

    public void fonalHozzaadasa(GombaFonal fonal) {
        hivasLog("fonalHozzaadasa(GombaFonal fonal)", List.of("fonal: Gombafonal"), 1);
        log("Fonal hozzaadava a gombatesthez");
    }
}
