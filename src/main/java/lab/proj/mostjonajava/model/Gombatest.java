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
     * Feltétel: szomszédos a két tekton és az egyiken van már a gombatesttől fonál
     * @param honnan
     * @param hova
     */
    public void fonalNovesztes(Tekton honnan, Tekton hova) {
        hivasLog("fonalNovesztes(Tekton honnan, Tekton hova)", 
                List.of("honnan: Tekton - " + honnan.toString(), 
                    "hova: Tekton - " + hova.toString()), 0);
        
        boolean kapcsolodik = gombaFonalak.stream().anyMatch(f -> f.getHonnan().equals(honnan) || f.getHova().equals(honnan) || f.getHonnan().equals(hova) || f.getHova().equals(hova));
        
        if(!honnan.szomszedossagEllenorzese(hova) || noveszthetoFonalakSzama <= 0 || !kapcsolodik) {
            log("Nem novesztheto gombafonal");
            return;
        }

        GombaFonal gombaFonal = new GombaFonal(honnan, hova, this);
        
        honnan.setGombafonal(gombaFonal);
        
        //egyfonalas miatt ellenőrzés ->ha az egyik tektonhoz nem adja hozzá akkor a másikhoz se
        if(honnan.getGombafonalak().contains(gombaFonal)) {
            hova.setGombafonal(gombaFonal);
            
            if(hova.getGombafonalak().contains(gombaFonal)) {
                this.gombaFonalak.add(gombaFonal);
                noveszthetoFonalakSzama--;
                log("Fonalnovesztes sikeres");
            } else {
                honnan.getGombafonalak().remove(gombaFonal);
                log("Fonalnovesztes sikertelen");
            }
        } else {
            log("Fonalnovesztes sikertelen");
        }
    }

    //TODO Elolvasni:
    //néha gombafonal törlésnél a tekton.getGombafonalak().remove(fonal) függvény hívódik meg,
    //ez abban az esetben ha a fonal tekton típustól függetlenül kell hogy törlődjön, pl. ha azt a fonalat vágjuk, azon a tektonon van,
    //ami széttörik vagy mikor a hozzá tartozó gombatest elpusztul
    //
    //néha viszont a tekton.gombafonalTorlese(fonal) hívódik meg, ez tektonfüggő hívás, tehát pl. ha elszakad a gombatesttől akkor 
    //két fonal éltető között megmarad a fonál

    /**
     * Ha egy fonalat törlünk, akkor más fonalak már nem biztos, hogy összeköttetésben maradnak a gombatestükkel.
     * Azokat a fonalakat töröljük, amik már nem kapcsolódnak hozzájuk, ezeket dfs bejárással keressük meg.
     * @param fonal
     */
    public void fonalTorles(GombaFonal fonal) {
        hivasLog("fonalTorles(GombaFonal fonal)", List.of("fonal: Gombafonal"), 2);
        
        // 1. A fő fonal mindenképp törlődik mindenhonnan
        fonal.getHonnan().getGombafonalak().remove(fonal);
        fonal.getHova().getGombafonalak().remove(fonal);
        gombaFonalak.remove(fonal);
        
        // 2. DFS bejárás a levágott ágak keresésére
        List<Tekton> elerhetoTektonok = new ArrayList<>();
        dfsTektonBejaras(tekton, elerhetoTektonok);
        
        // 3. Levágott ágak törlése (kivéve ha mindkét végük FonalElteto)
        List<GombaFonal> osszesFonal = new ArrayList<>(gombaFonalak);
        for (GombaFonal fon : osszesFonal) {
            if (!elerhetoTektonok.contains(fon.getHonnan()) || 
                !elerhetoTektonok.contains(fon.getHova())) {
                
                // Törlés próbálkozás mindkét oldalról (ha mindkettő fonal éltető akkor meg kell hagyni, ha csak az egyikből akkor oda visszarakjuk)
                boolean honnanTorolve = fon.getHonnan().getGombafonalak().remove(fon);
                boolean hovaTorolve = fon.getHova().getGombafonalak().remove(fon);
                
                // Ha mindkettőből töröltük, akkor gombatestből is
                if (honnanTorolve && hovaTorolve) {
                    gombaFonalak.remove(fon);
                } 
                // Ha csak egyik oldalról sikerült, visszarakjuk
                else {
                    // Ha egyik oldal sem tudott törölni, nem csinálunk semmit
                    if (!honnanTorolve && !hovaTorolve) {
                        return;
                    }
                    // Visszarakás csak akkor, ha egyik oldalról töröltünk
                    if (honnanTorolve) {
                        fon.getHova().getGombafonalak().add(fon);
                    }
                    if (hovaTorolve) {
                        fon.getHonnan().getGombafonalak().add(fon);
                    }
                }
            }
        }
        log("Fonal torlese sikeres.");
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
            fonal.getHonnan().getGombafonalak().remove(fonal);
            fonal.getHova().getGombafonalak().remove(fonal);
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

        if (!tekton.szomszedossagEllenorzese(celTekton) || kilohetoSporakSzama < mennyiseg) {
            log("Spora kilovese sikertelen.");
            return;
        }

        if(this.getKilohetoSporakSzama() > mennyiseg){
            log("Spora kilovese sikertelen.");
            return;
        }

        kilohetoSporakSzama -= mennyiseg;
        elszortSporakSzama += mennyiseg;

        Random rand = new Random();
        for (int i = 0; i < mennyiseg; i++) {
            Spora spora = switch (rand.nextInt(6)) {
                case 0 -> new BenitoSpora();
                case 1 -> new VagasTiltoSpora();
                case 2 -> new OsztodoSpora();
                case 3 -> new LassitoSpora();
                case 4 -> new GyorsitoSpora();
                default -> new SimaSpora();
            };
            celTekton.getSporak().add(spora);
        }
        log("Sporak kilovese sikeres volt.");
    }

    /**
     * Visszatér a gombatest id-jával.
     * @return 
     */
    public int getId(){return id; }

    /**
     * Visszatér a gombatest tektonjával.
     * @return 
     */
    public Tekton getTekton() {
        hivasLog("getTekton()", List.of(), 0);
        return tekton;
    }

    /**
     * Beállítja a tektont.
     * @param ertek
     */
    public void setTekton(Tekton tekton) {
        hivasLog("setTekton(Tekton tekton)", List.of("tekton: Tekton"), 0);
        this.tekton = tekton;
    }

    /**
     * Visszatér a gombatest gombászával.
     * @return 
     */
    public Gombasz getGombasz() {
        hivasLog("getGombasz()", List.of(), 0);
        return gombasz;
    }

    /**
     * Beállítja a gombászt. 
     * @param ertek
     */
    public void setGombasz(Gombasz gombasz) {
        hivasLog("setGombasz(Gombasz gombasz)", List.of("gombasz: Gombasz"), 0);
        this.gombasz = gombasz;
    }

    /**
     * Visszatér a kilőhető spórák számával.
     * @return 
     */
    public int getKilohetoSporakSzama() {
        hivasLog("getKilohetoSporakSzama()", List.of(), 0);
        return kilohetoSporakSzama;
    }

    /**
     * Visszatér az elszórt spórák számával.
     * @return 
     */
    public int getElszortSporakSzama() {
        hivasLog("getElszortSporakSzama()", List.of(), 0);
        return elszortSporakSzama;
    }

    /**
     * Visszatér a növeszthető fonalak számával.
     * @return 
     */
    public int getNoveszthetoFonalakSzama() {
        hivasLog("getNoveszthetoFonalakSzama()", List.of(), 0);
        return noveszthetoFonalakSzama;
    }

    /**
     * Visszatér a gombatest gombafonalaival.
     * @return 
     */
    public List<GombaFonal> getGombaFonalak() {
        hivasLog("getGombaFonalak()", List.of(), 0);
        return gombaFonalak;
    }

    /**
     * Minden kör végén resetelődik a növeszthető fonalak száma és termelődik egy spóra
     */
    public void korFrissites() {
        hivasLog("korFrissites()", List.of(), 0);
        
        if (kilohetoSporakSzama < 10) { 
            kilohetoSporakSzama++;
        }
        
        noveszthetoFonalakSzama = 1;
        
        if (elszortSporakSzama >= 10) {
            log("A gombatest elpusztul");
            elpusztulas();
        }
    }
}
