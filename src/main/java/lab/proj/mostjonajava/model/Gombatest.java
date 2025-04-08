package lab.proj.mostjonajava.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static lab.proj.mostjonajava.utils.Logger.hivasLog;
import static lab.proj.mostjonajava.utils.Logger.log;
import lombok.Data;

@Data
public class Gombatest {

    private static int nextId = 1;
    private int id;
    private Tekton tekton;
    private Gombasz gombasz;
    private List<GombaFonal> gombaFonalak;
    private int kilohetoSporakSzama;
    private int elszortSporakSzama;
    private int noveszthetoFonalakSzama = 1;

    /**
     * A gombatest konstruktora
     */
    public Gombatest() {
        this.id = nextId++;
        this.gombaFonalak = new ArrayList<>();
        log("Gombatest letrejott.");
    }

    /**
     * Egy új fonalat növeszt.
     *
     * @param honnan
     * @param hova
     */
    public void fonalNovesztes(Tekton honnan, Tekton hova) {
        hivasLog("fonalNovesztes(Tekton honnan, Tekton hova)", List.of("honnan: Tekton - " + honnan.toString(), "hova: Tekton - " + hova.toString()), 0);
        if(!honnan.vanFonalKozottuk(hova) && hova.gombatestNoveszthetoE() && honnan.szomszedossagEllenorzese(hova)) {
            GombaFonal gombaFonal = new GombaFonal(this.getTekton(), hova, this);
            honnan.setGombafonal(gombaFonal);
            hova.setGombafonal(gombaFonal);
            gombaFonalak.add(gombaFonal);
            noveszthetoFonalakSzama--;
            log("Teljesulnek a feltetelek");
        }
        else {
            log("Nem teljesulnek a fonalnovesztes feltetelei");
        }
    }

    //ezt atirni ez bonyolultabb lesz
    public void fonalTorles(GombaFonal fonal) {
        hivasLog("fonalTorles(GombaFonal fonal)", List.of("fonal: Gombafonal"), 2);
        gombaFonalak.remove(fonal);
        log("Fonal torlese gombatestbol sikeres volt.");
    }


    /**
     * Megsemmisíti a gombatestet, a fonalakkal együtt.
     */
    public void elpusztulas(boolean fonalakTorlese) { // az uj tekton tipus miatt nem igy fog teljesen kinezni
        hivasLog("elpusztulas()", List.of(), 0);
        tekton.setGombatest(null);
        tekton = null;
        gombasz = null;
        //if (toroljeAFonalakat) {//a fonalTorlest kell meg meghivni de az még nincs kidolgozva}
        log("A gombatest elpusztult");
    }

    /**
     * Spórákat lő ki.
     *
     * @param tekton
     */
    public void sporaKiloves(Tekton celTekton, int mennyiseg) {
        hivasLog("sporaKiloves(Tekton tekton, int mennyiseg)", List.of("tekton: Tekton", "mennyiseg: int"), 0);

        if (!tekton.szomszedossagEllenorzese(celTekton)) {
            log("A kiválasztott tekton nem szomszédos a gombatesttel.");
            return;
        }

        if (kilohetoSporakSzama < mennyiseg) {
            log("Nincs eleg kiloheto spora. Maradek: " + kilohetoSporakSzama);
            return;
        }

        kilohetoSporakSzama -= mennyiseg;
        elszortSporakSzama += mennyiseg;

        Random rand = new Random();
        for (int i = 0; i < mennyiseg; i++) {
            int valasztas = rand.nextInt(6);
            Spora spora;
            switch (valasztas) {
                case 0 -> spora = new BenitoSpora();
                case 1 -> spora = new VagasTiltoSpora();
                case 2 -> spora = new OsztodoSpora();
                case 3 -> spora = new LassitoSpora();
                case 4 -> spora = new GyorsitoSpora();
                default -> spora = new SimaSpora();
            }
            celTekton.setSpora(spora);
        }
        log("Spora(k) kilovese sikeres volt: " + mennyiseg + " db");
    }

    public Tekton getTekton() {
        return tekton;
    }

    public void setTekton(Tekton tekton) {
        this.tekton = tekton;
    }

    public Gombasz getGombasz() {
        return gombasz;
    }

    public void setGombasz(Gombasz gombasz) {
        this.gombasz = gombasz;
    }

    public int getKilohetoSporakSzama() {
        return kilohetoSporakSzama;
    }

    public void setKilohetoSporakSzama(int kilohetoSporakSzama) {
        this.kilohetoSporakSzama = kilohetoSporakSzama;
    }

    public int getElszortSporakSzama() {
        return elszortSporakSzama;
    }

    public void setElszortSporakSzama(int elszortSporakSzama) {
        this.elszortSporakSzama = elszortSporakSzama;
    }

    public int getNoveszthetoFonalakSzama() {
        return noveszthetoFonalakSzama;
    }

    public void setNoveszthetoFonalakSzama(int noveszthetoFonalakSzama) {
        this.noveszthetoFonalakSzama = noveszthetoFonalakSzama;
    }

    public List<GombaFonal> getGombaFonalak() {
        return gombaFonalak;
    }

    public void setGombaFonalak(GombaFonal gombaFonal) {
        gombaFonalak.add(gombaFonal);
    }

    public void setGombaFonalak(List<GombaFonal> fonalak) {
        this.gombaFonalak.addAll(fonalak);
    }
}
