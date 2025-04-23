package lab.proj.mostjonajava.model;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static lab.proj.mostjonajava.utils.Logger.hivasLog;
import static lab.proj.mostjonajava.utils.Logger.log;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

//@Data
@Getter
@Setter
public abstract class Tekton {

    private static int nextId = 1;
    private int id;
    private int fonalakElettartama;

    private List<Tekton> szomszedosTektonok;
    private List<Rovar> rovarok;
    private List<GombaFonal> gombafonalak;
    private List<Spora> sporak;

    private Gombatest gombatest;

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Tekton[").append(id).append("]\n");

        // 1) Szomszédok
        sb.append("  Szomszedok: ");
        if (szomszedosTektonok.isEmpty()) {
            sb.append("nincs");
        } else {
            sb.append(szomszedosTektonok.stream()
                    .map(t -> String.valueOf(t.getId()))
                    .collect(Collectors.joining(", ")));
        }
        sb.append("\n");

        // 2) Gombafonal-összeköttetések
        sb.append("  Gombafonal-osszekottetesek: ");
        if (gombafonalak.isEmpty()) {
            sb.append("nincs");
        } else {
            // minden fonál másik végén lévő tekton ID-ja
            String kapcsolatok = gombafonalak.stream()
                    .map(f -> {
                        // ha this a honnan, akkor hova kell, egyébként honnan
                        Tekton masik = f.getHonnan().equals(this) ? f.getHova() : f.getHonnan();
                        return String.valueOf(masik.getId());
                    })
                    .distinct()
                    .collect(Collectors.joining(", "));
            sb.append(kapcsolatok);
        }
        sb.append("\n");

        // 2) Gombatest
        if (gombatest != null) {
            sb.append("  Gombatest: ID=").append(gombatest.getId())
                    .append(", kiloSpora=").append(gombatest.getKilohetoSporakSzama())
                    .append(", elSzortSpora=").append(gombatest.getElszortSporakSzama())
                    .append(", novelhetoFonalSzama=").append(gombatest.getNoveszthetoFonalakSzama())
                    .append("\n");
        } else {
            sb.append("  Gombatest: nincs\n");
        }

        // 3) Spórák a tektonon
        sb.append("  Sporak: ");
        if (sporak.isEmpty()) {
            sb.append("nincs");
        } else {
            sb.append(sporak.stream()
                    .map(sp -> sp.getClass().getSimpleName() + "(tap=" + sp.getTapanyag() + ")")
                    .collect(Collectors.joining(", ")));
        }
        sb.append("\n");

        // 4) Rovarok
        sb.append("  Rovarok: ");
        if (rovarok.isEmpty()) {
            sb.append("nincsenek");
        } else {
            sb.append(rovarok.stream()
                    .map(r -> String.format(
                            "ID=%d, lepSz=%d, benult=%b, vago=%b",
                            r.getId(), r.getLepesSzam(), r.getBenulas(), r.getVagoKepesseg()
                    ))
                    .collect(Collectors.joining(" | ")));
        }
        sb.append("\n");

        return sb.toString();
    }

    /**
     * Tekton konstruktora, amely paraméterként átveszi az élettartamot.
     * @param eletTartam
     */
    Tekton(int eletTartam) {
        hivasLog("Tekton(int eletTartam)", List.of("eletTartam: int"), 1);
        fonalakElettartama = eletTartam;
        this.id = nextId++;
        this.szomszedosTektonok = new ArrayList<>();
        this.rovarok = new ArrayList<>();
        this.sporak = new ArrayList<>();
        this.gombafonalak = new ArrayList<>();
        log("Tekton letrejott.");
    }

    /**
     * Egy tekton ketté törik. Minden származtatott osztályban külön van implementálva.
     */
    public abstract void ketteTores();

     /**
     * Ellenőrzi, hogy növeszthető-e gombatest az adott tekton.
     * Feltételek: legyen legalább 3 spóra a tektonon és legyen összekötve fonallal.
     * @return
     */
    public boolean gombatestNoveszthetoE() {
        hivasLog("gombatestNoveszthetoE()", List.of(), 0);
        for (GombaFonal fonal : gombafonalak) {
            if ((fonal.getHonnan() == this || fonal.getHova() == this) && sporak.size() >= 3){
                log("true");
                return true;
            }
        }
        log("false");
        return false;
    }

    /**
     * Ellenőrzi, hogy fejleszthető e a tektonon található gombatest
     * Feltételek: legyen legalább 3 spóra a tektonon és gombatest, amit fejleszthetsz.
     * @return
     */
    public boolean gombatestFejleszthetoE() {
        hivasLog("gombatestFejleszthetoE()", List.of(), 0);
        if(sporak.size() >= 3 && gombatest != null){
            log("true");
            return true;}
        log("false");
        return false;

    }

    /**
     * Ellenőrzi, hogy a paraméterben kapott tekton szomszédja-e az adott tektonnak.
     * @param tekton
     * @return
     */
    public boolean szomszedossagEllenorzese(Tekton tekton) {
        hivasLog("szomszedossagEllenorzese(Tekton tekton)", List.of("tekton: Tekton"), 1);
        if(szomszedosTektonok.contains(tekton)){
            log("true");
            return true;}
        log("false");
        return false;
    }

    /**
     * Ellenőrzi, hogy a paraméterben kapott tekton szomszédja vagy a szomszédainak szomszédja-e az adott tektonnak.
     * @param tekton
     * @return
     */
    public boolean szomszedSzomszedEllenorzese(Tekton tekton) {
        hivasLog("szomszedSzomszedEllenorzese(Tekton tekton)", List.of("tekton: Tekton"), 1);
        for (Tekton szomszed : szomszedosTektonok) {
            if (szomszed.getSzomszedosTektonok().contains(tekton)){
                log("true");
                return true;
            }
        }
        log("false");
        return false;
    }

    /**
     * Ellenőrzi, hogy van-e fonal a két tekton között (amin áll és ami a paraméter).
     * @param tekton
     * @return
     */
    public boolean vanFonalKozottuk(Tekton tekton) {
        hivasLog("vanFonalKozottuk(Tekton tekton)", List.of("tekton: Tekton"), 1);
        if (this.equals(tekton)) return false;
        for (GombaFonal fonal : gombafonalak) {
            if ((fonal.getHonnan().equals(this) && fonal.getHova().equals(tekton)) || (fonal.getHova().equals(this) && fonal.getHonnan().equals(tekton))) {
                log("true");
                return true;
            }
        }
        log("false");
        return false;
    }

    /**
     * Visszatér a tekton id-jával.
     * @return 
     */
    public int getId(){ return id; }

    /**
     * Visszaadja a szomszédos tektonokat.
     * @return
     */
    public List<Tekton> getSzomszedosTektonok() {
        hivasLog("getSzomszedosTektonok()", List.of(), 1);
        return szomszedosTektonok;
    }

    /**
     * Visszaadja a tektonon lévő rovarokat.
     * @return
     */
    public List<Rovar> getRovarok() {
        hivasLog("getRovarok()", List.of(), 1);
        return rovarok;
    }

    /**
     * Visszaadja a tektonont összekötő gombafonalakat.
     * @return
     */
    public List<GombaFonal> getGombafonalak() {
        hivasLog("getGombafonal()", List.of(), 1);
        return gombafonalak;
    }

    /**
     * Törlődik a megadott fonal - a FonalElteto (ebben fix) és lehet az EltunoFonalas tektonba override-ol
     * @param fonal
     */
    public void fonalTorlese(GombaFonal fonal) {
        hivasLog("fonalTorlese(GombaFonal fonal)", List.of("fonal: Gombafonal"), 1);
        getGombafonalak().remove(fonal);
        log("Fonal torlese sikeres.");
    }

    /**
     * Hozzáad egy gombafonalat a tektonhoz. - az Egyfonalas tektonban override-ol
     * @param fonal
     */
    public void setGombafonal(GombaFonal fonal) { 
        hivasLog("setGombafonal(GombaFonal fonal)", List.of("fonal: Gombafonal"), 1);
        gombafonalak.add(fonal);
        log("Fonal hozzaadasa sikeres volt.");
    }

    /**
     * Visszaadja a tektonon lévő spórákat.
     * @return
     */
    public List<Spora> getSporak() {
        hivasLog("getSporak()", List.of(), 1);
        return sporak;
    }

    /**
     * Visszaadja a tektonon lévő gombatestet.
     * @return
     */
    public Gombatest getGombatest() {
        hivasLog("getGombatest()", List.of(), 1);
        return gombatest;
    }

    /**
     * Hozzáad egy gombatestet a tektonhoz. - a TestNelkuli tektonban override-ol
     * @param gombatest
     */
    public void setGombatest(Gombatest gombatest) {
        hivasLog("setGombatest(Gombatest gombatest)", List.of("gombatest: Gombatest"), 0);
        this.gombatest = gombatest;
        gombatest.setTekton(this);
        gombatest.getGombasz().getGombatestek().add(gombatest);

        log("Gombatest beallitasra kerult");
    }

    /**
     * Bizonyos tektonoknál (pl. Eltűnő fonalas) számolni kell az eltelt időt, ezt valósítja meg ez a fügvény.
     */
    public void korFrissites() {
        hivasLog("korFrissites()", List.of(), 0);
        if (fonalakElettartama == -1) return; // ez nem tűnik el idővel

        fonalakElettartama--;
    
        if (fonalakElettartama == 0) {
            List<GombaFonal> torlendoFonalak = new ArrayList<>(getGombafonalak());
            for (GombaFonal fonal : torlendoFonalak) {
                fonal.getHonnan().getGombafonalak().remove(fonal);
                fonal.getHova().getGombafonalak().remove(fonal);
                fonal.getGombatest().fonalTorles(fonal);
            }
    
            fonalakElettartama = 3;
            log("Fonalak torlese megtortent az EltunoFonalasTektonon.");
        }
    }
}
