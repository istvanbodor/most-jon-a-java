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
    private int kilohetoSporakSzama;
    private int elszortSporakSzama;
    private int noveszthetoFonalakSzama = 1;

    private List<GombaFonal> gombaFonalak;

    /**
     * A gombatest konstruktora paraméterek nélkül (fejlesztésnél jön elő)
     */
    public Gombatest() {
        this.id = nextId++;
        log("Gombatest letrejott.");
    }

    /**
     * A gombatest konstruktora
     * @param tekton
     * @param gombasz
     */
    public Gombatest(Tekton tekton, Gombasz gombasz) {
        this.id = nextId++;
        this.gombasz = gombasz;
        this.tekton = tekton;
        this.gombaFonalak = new ArrayList<>();
        log("Gombatest letrejott.");
    }

    /**
     * Egy új fonalat növeszt.
     * Feltétel: szomszédos a két tekton
     * @param honnan
     * @param hova
     */
    public void fonalNovesztes(Tekton honnan, Tekton hova) {
        hivasLog("fonalNovesztes(Tekton honnan, Tekton hova)", List.of("honnan: Tekton - " + honnan.toString(), "hova: Tekton - " + hova.toString()), 0);
        if(honnan.szomszedossagEllenorzese(hova)) {
            GombaFonal gombaFonal = new GombaFonal(honnan, hova, this);
            honnan.setGombafonal(gombaFonal);
            hova.setGombafonal(gombaFonal);
            gombaFonalak.add(gombaFonal);
            noveszthetoFonalakSzama--;
            log("Fonalnovesztes sikeres");
        }
        else {
            log("Nem novesztheto gombafonal");
        }
    }

    /**
     * Ha egy fonalat törlünk, akkor más fonalak már nem biztos, hogy összeköttetésben maradnak a gombatestükkel.
     * Azokat a fonalakat töröljük, amik már nem kapcsolódnak hozzájuk, ezeket dfs bejárással keressük meg.
     * @param fonal
     */
    public void fonalTorles(GombaFonal fonal) {
        hivasLog("fonalTorles(GombaFonal fonal)", List.of("fonal: Gombafonal"), 2);
    
        // eltávolítás a gombatestből
        gombaFonalak.remove(fonal);
    
        // eltávolítás a tektonokból, azt még meg kell oldani, hogy marad meg ha az egyik tekton FonalElteto
        Tekton honnan = fonal.getHonnan();
        Tekton hova = fonal.getHova();
        honnan.fonalTorlese(fonal);
        hova.fonalTorlese(fonal);
    
        // megmaradt fonalak bejárása: melyik tekton éri még el a gombatestet?
        List<Tekton> elerhetoTektonok = new ArrayList<>();

        dfsTektonBejaras(tekton, elerhetoTektonok);
    
        // minden olyan fonalat törlünk, ami olyan tektonban van, amely már nem kapcsolódik a gombatesthez
        for (Tekton tek : List.of(honnan, hova)) {
            List<GombaFonal> torlendoFonalak = new ArrayList<>();
            for (GombaFonal fon : tek.getGombafonal()) {    
                if (!elerhetoTektonok.contains(fon.getHonnan()) || !elerhetoTektonok.contains(fon.getHova()))
                    torlendoFonalak.add(fon);
            }
            for (GombaFonal torolheto : torlendoFonalak) {
                tek.fonalTorlese(torolheto);
            }
        }
        log("Fonal torlese gombatestbol es elszakadt fonalak eltavolitasa is sikeres volt.");
    }

    /**
     * Dfs bejárás, hogy megtaláljuk az "elérhetetlen" tektonokat
     * @param aktualis
     * @param bejart
     */
    private void dfsTektonBejaras(Tekton aktualis, List<Tekton> bejart) {
        if (bejart.contains(aktualis)) return;
        bejart.add(aktualis);
    
        for (GombaFonal fonal : aktualis.getGombafonalak()) {
            Tekton kovetkezo = fonal.getHonnan().equals(aktualis) ? fonal.getHova() : fonal.getHonnan();
            dfsTektonBejaras(kovetkezo, bejart);
        }
    }
    
    /**
     * Megsemmisíti a gombatestet, az összes fonalával együtt.
     */
    public void elpusztulas() { 
        hivasLog("elpusztulas()", List.of(), 0);
        
        List<GombaFonal> torlendoFonalak = new ArrayList<>(gombaFonalak);
        for (GombaFonal fonal : torlendoFonalak) {
            fonal.getHonnan().fonalTorlese(fonal);
            fonal.getHova().fonalTorlese(fonal);
        }
        gombaFonalak.clear();
        tekton.setGombatest(null);
        gombasz.getGombatestek().remove(this);
        
        log("A gombatest elpusztult");
    }

    /**
     * Spórákat lő ki szomszédos tektonra, random generátor segítségével.
     * @param celTekton
     * @param mennyiseg
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
            celTekton.getSporak().add(spora);
        }
        log("Spora(k) kilovese sikeres volt: " + mennyiseg + " db");
    }

    /**
     * Visszatér a gombatest tektonjával.
     * @return 
     */
    public Tekton getTekton() { return tekton; }

    /**
     * Beállítja a tektont.
     * @param ertek
     */
    public void setTekton(Tekton ertek) { tekton = ertek; }

    /**
     * Visszatér a gombatest gombászával.
     * @return 
     */
    public Gombasz getGombasz() { return gombasz; }

    /**
     * Beállítja a gombászt. 
     * @param ertek
     */
    public void setGombasz(Gombasz ertek) { gombasz = ertek; }

    /**
     * Visszatér a kilőhető spórák számával.
     * @return 
     */
    public int getKilohetoSporakSzama() { return kilohetoSporakSzama; }

    /**
     * Visszatér az elszórt spórák számával.
     * @return 
     */
    public int getElszortSporakSzama() { return elszortSporakSzama; }

    /**
     * Visszatér a növeszthető fonalak számával.
     * @return 
     */
    public int getNoveszthetoFonalakSzama() { return noveszthetoFonalakSzama; }

    /**
     * Visszatér a gombatest gombafonalaival.
     * @return 
     */
    public List<GombaFonal> getGombaFonalak() { return gombaFonalak; }
}
