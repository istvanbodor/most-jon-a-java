package lab.proj.mostjonajava.game;

import java.util.ArrayList;
import java.util.List;

import lab.proj.mostjonajava.model.*;

import static lab.proj.mostjonajava.utils.Logger.hivasLog;
import lombok.Data;

@Data
public class Jatek {

    private List<Tekton> tabla = new ArrayList<>();
    private List<Gombasz> gombaszok = new ArrayList<>();
    private List<Rovarasz> rovaraszok = new ArrayList<>();

    public Jatek(int jatekosokSzama, List<String> nevek) {
        // inicializáld a tabla-t egy tetszőleges topológiával,
        // hozd létre a gombászokat és rovarászokat nevek alapján,
        // és add hozzá őket a listákhoz:
        for (String nev : nevek) {
            gombaszok.add(new Gombasz(nev));
            rovaraszok.add(new Rovarasz(nev));
        }
        // Példa: indítsd el a tabla-t egy fix kezdőgrafban
        // tabla = createChain3();  // vagy bármilyen factory
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
