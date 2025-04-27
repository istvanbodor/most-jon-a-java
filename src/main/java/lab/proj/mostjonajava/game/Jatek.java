package lab.proj.mostjonajava.game;

import java.util.ArrayList;
import java.util.List;

import lab.proj.mostjonajava.model.*;

import static lab.proj.mostjonajava.utils.Logger.hivasLog;
import lombok.Data;

@Data
public class Jatek {
    public static int KOROK_SZAMA = 10;
    private List<Tekton> tabla = new ArrayList<>();
    private List<Gombasz> gombaszok = new ArrayList<>();
    private List<Rovarasz> rovaraszok = new ArrayList<>();

    /**
     * A jatek konstruktora
     * @param jatekosokSzama A jatekosok szama
     * @param jatekosNevek A jatekosok neveinek a listaja
     */
    public Jatek(int jatekosokSzama, List<String> jatekosNevek) {

        List<String> parameterek = new ArrayList<>();
        parameterek.add("jatekosokSzama: int - " + jatekosokSzama);
        parameterek.add("jatekosNevek: List<String> - " + jatekosNevek);
        hivasLog("Jatek(int jatekosokSzama, List<String> jatekosNevek)", parameterek, 0);
//        tektonok = new ArrayList<>();
//        rovaraszok = new ArrayList<>();
//        gombaszok = new ArrayList<>();

        for (int i = 0; i < jatekosokSzama; i++) {
            String nev = jatekosNevek.get(i);
            if (i % 2 == 1) {
               rovaraszok.add(new Rovarasz(nev));
            } else {
                gombaszok.add(new Gombasz(nev));
            }
        }
    }

    public List<Tekton> getTabla() { return tabla; }
    public List<Gombasz> getGombaszok() { return gombaszok; }
    public List<Rovarasz> getRovaraszok() { return rovaraszok; }

    /** Keres egy tektont azonosito alapjan. */
    public Tekton keresTektonById(int id) {
        return tabla.stream()
                .filter(t -> t.getId() == id)
                .findFirst()
                .orElse(null);
    }

    /** Keres egy gombatestet azonosito alapjan. */
    // game/Jatek.java
    public Gombatest keresGombatestById(int id) {
        return gombaszok.stream()
                .flatMap(g -> g.getGombatestek().stream())
                .filter(gt -> gt.getId() == id)
                .findFirst()
                .orElse(null);
    }

    /** Keres egy rovart azonosito alapjan. */
    public Rovar keresRovarById(int id) {
        return rovaraszok.stream()
                .flatMap(rz -> rz.getRovarok().stream())
                .filter(r -> r.getId() == id)
                .findFirst()
                .orElse(null);
    }

    // ide jöhetnek még a játékvezérlő metódusok (korindítás, állapotfrissítés, mentés/ betöltés, stb.)

}
