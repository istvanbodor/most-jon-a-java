package lab.proj.mostjonajava.game;

import javafx.scene.paint.Color;
import lab.proj.mostjonajava.model.*;
import lombok.Data;

import java.util.*;

import static lab.proj.mostjonajava.utils.Cleaner.cleanUp;

/**
 * A játék fő osztálya, amely tartalmazza a játék állapotát és logikáját.
 * Kezeli a játékosokat, a pályaelemeket és az azok közötti interakciókat.
 *
 * @Data Lombok annotáció, ami generál getter/setter metódusokat, toString(), equals() és hashCode() metódusokat
 */
@Data
public class Jatek {
    /** A játékban szereplő körök száma */
    public static int KOROK_SZAMA = 10;
    /** A játék tábláját alkotó Tektönök listája */
    private List<Tekton> tabla = new ArrayList<>();
    /** A játékban részt vevő Gombászok listája */
    private List<Gombasz> gombaszok = new ArrayList<>();
    /** A játékban részt vevő Rovarászok listája */
    private List<Rovarasz> rovaraszok = new ArrayList<>();
    /** Gombatestek ikonjait tároló map (ID -> ikon elérési út) */
    private Map<Integer, String> gombatestIkonok = new HashMap<>();
    /** Tektönök színeit tároló map (Tekton -> Color) */
    private Map<Tekton, Color> tektonSzinek = new HashMap<>();
    /**
     * A játék konstruktora, amely inicializálja a játékot a megadott játékosokkal.
     *
     * @param jatekosokSzama A játékosok száma
     * @param jatekosNevek A játékosok neveinek listája
     */
    public Jatek(int jatekosokSzama, List<String> jatekosNevek) {
        cleanUp();
        tabla = new ArrayList<>();
        gombaszok = new ArrayList<>();
        rovaraszok = new ArrayList<>();
        gombatestIkonok = new HashMap<>();
        tektonSzinek = new HashMap<>();
        Random rnd = new Random();
        int rovaraszIdx = 0;
        int gombaszIdx = 0;
        for (int i = 0; i < jatekosokSzama; i++) {
            String nev = jatekosNevek.get(i);
            Tekton tekton;
            int randomSzam = rnd.nextInt(1,6);
            // Véletlenszerűen választunk Tektön típust
            switch (randomSzam) {
                case 1 -> tekton = new TobbFonalasTekton();
                case 2 -> tekton = new EgyFonalasTekton();
                case 3 -> tekton = new EltunoFonalasTekton();
                case 4 -> tekton = new TestNelkuliTekton();
                default -> tekton = new FonalEltetoTekton();
            }
            // Tektönhöz szín hozzárendelése
            switch (randomSzam) {
                case 1 -> tektonSzinek.put(tekton, Color.AQUA);
                case 2 -> tektonSzinek.put(tekton, Color.GREEN);
                case 3 -> tektonSzinek.put(tekton, Color.YELLOW);
                case 4 -> tektonSzinek.put(tekton, Color.RED);
                default -> tektonSzinek.put(tekton, Color.PURPLE);
            }
            if (i % 2 == 1) {
                // Páratlan indexű játékos: Rovarász
                rovaraszok.add(new Rovarasz(nev));
               tabla.add(tekton);
               rovaraszok.get(rovaraszIdx).getRovarok().add(new Rovar(tabla.get(i), rovaraszok.get(rovaraszIdx)));
               tabla.get(i).getRovarok().add(rovaraszok.get(rovaraszIdx).getRovarok().get(0));
               tabla.get(i).getSzomszedosTektonok().add(tabla.get(i-1));
               tabla.get(i-1).getSzomszedosTektonok().add(tabla.get(i));
               rovaraszIdx++;
            } else {
                // Páros indexű játékos: Gombász
                gombaszok.add(new Gombasz(nev));
                if (randomSzam == 4) {
                    tektonSzinek.remove(tekton);
                    tekton = new EgyFonalasTekton();
                    tektonSzinek.put(tekton, Color.GREEN);
                }
                tabla.add(tekton);
                Gombatest gombatest = new Gombatest(tabla.get(i), gombaszok.get(gombaszIdx));
                gombaszok.get(gombaszIdx).getGombatestek().add(gombatest);
                gombatestIkonok.put(gombatest.getId(), "/ikonok/SimaGombaTest.png");
                tabla.get(i).setGombatest(gombaszok.get(gombaszIdx).getGombatestek().get(0));
                if (i > 0) {
                    tabla.get(i).getSzomszedosTektonok().add(tabla.get(i-1));
                    tabla.get(i-1).getSzomszedosTektonok().add(tabla.get(i));
                }
                gombaszIdx++;
            }
        }
    }
    /**
     * Jatek osztály konstruktora, amely a megadott játékosnevek alapján
     * létrehozza a megfelelő típusú játékosokat (Gombász vagy Rovarász).
     *
     * @param jatekosokSzama     A játékosok száma.
     * @param jatekosokNevei     A játékosok nevei vesszővel elválasztva.
     *
     * A páros indexű játékosok (0, 2, ...) Gombászok lesznek,
     * míg a páratlan indexűek (1, 3, ...) Rovarászok.
     */
    public Jatek(int jatekosokSzama, String jatekosokNevei) {
        cleanUp();
        String[] jatekosTomb = jatekosokNevei.split(",");
        for (int i = 0; i < jatekosokSzama; i ++) {
            if (i % 2 == 1) {
                rovaraszok.add(new Rovarasz(jatekosTomb[i]));
            }
            else {
                gombaszok.add(new Gombasz(jatekosTomb[i]));
            }
        }
    }
    /**
     * Visszaadja a játék tábláját alkotó Tektönök listáját.
     * @return A Tektönök listája
     */
    public List<Tekton> getTabla() { return tabla; }
    /**
     * Visszaadja a játékban részt vevő Gombászok listáját.
     * @return A Gombászok listája
     */
    public List<Gombasz> getGombaszok() { return gombaszok; }
    /**
     * Visszaadja a játékban részt vevő Rovarászok listáját.
     * @return A Rovarászok listája
     */
    public List<Rovarasz> getRovaraszok() { return rovaraszok; }
    /**
     * Tektön keresése ID alapján.
     * @param id A keresett Tektön ID-ja
     * @return A megtalált Tektön, vagy null ha nem található
     */
    public Tekton keresTektonById(int id) {
        return tabla.stream()
                .filter(t -> t.getId() == id)
                .findFirst()
                .orElse(null);
    }
    /**
     * Gombatest keresése ID alapján.
     * @param id A keresett Gombatest ID-ja
     * @return A megtalált Gombatest, vagy null ha nem található
     */
    public Gombatest keresGombatestById(int id) {
        return gombaszok.stream()
                .flatMap(g -> g.getGombatestek().stream())
                .filter(gt -> gt.getId() == id)
                .findFirst()
                .orElse(null);
    }
    /**
     * Rovar keresése ID alapján.
     * @param id A keresett Rovar ID-ja
     * @return A megtalált Rovar, vagy null ha nem található
     */
    public Rovar keresRovarById(int id) {
        return rovaraszok.stream()
                .flatMap(rz -> rz.getRovarok().stream())
                .filter(r -> r.getId() == id)
                .findFirst()
                .orElse(null);
    }
}
