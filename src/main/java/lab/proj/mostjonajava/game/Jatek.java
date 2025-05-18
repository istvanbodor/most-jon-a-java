package lab.proj.mostjonajava.game;

import javafx.scene.paint.Color;
import lab.proj.mostjonajava.model.*;
import lombok.Data;

import java.util.*;

import static lab.proj.mostjonajava.utils.Cleaner.cleanUp;

@Data
public class Jatek {
    public static int KOROK_SZAMA = 10;
    private List<Tekton> tabla = new ArrayList<>();
    private List<Gombasz> gombaszok = new ArrayList<>();
    private List<Rovarasz> rovaraszok = new ArrayList<>();

    private Map<Integer, String> gombatestIkonok = new HashMap<>();

    private Map<Tekton, Color> tektonSzinek = new HashMap<>();


    /**
     * A jatek konstruktora
     * @param jatekosokSzama A jatekosok szama
     * @param jatekosNevek A jatekosok neveinek a listaja
     */
    public Jatek(int jatekosokSzama, List<String> jatekosNevek) {
        cleanUp();
        Random rnd = new Random();
        int rovaraszIdx = 0;
        int gombaszIdx = 0;
        for (int i = 0; i < jatekosokSzama; i++) {
            String nev = jatekosNevek.get(i);
            Tekton tekton;
            int randomSzam = rnd.nextInt(1,6);
            switch (randomSzam) {
                case 1 -> tekton = new TobbFonalasTekton();
                case 2 -> tekton = new EgyFonalasTekton();
                case 3 -> tekton = new EltunoFonalasTekton();
                case 4 -> tekton = new TestNelkuliTekton();
                default -> tekton = new FonalEltetoTekton();
            }
            switch (randomSzam) {
                case 1 -> tektonSzinek.put(tekton, Color.AQUA);
                case 2 -> tektonSzinek.put(tekton, Color.GREEN);
                case 3 -> tektonSzinek.put(tekton, Color.YELLOW);
                case 4 -> tektonSzinek.put(tekton, Color.RED);
                default -> tektonSzinek.put(tekton, Color.PURPLE);
            }
            if (i % 2 == 1) {
               rovaraszok.add(new Rovarasz(nev));
               tabla.add(tekton);
               rovaraszok.get(rovaraszIdx).getRovarok().add(new Rovar(tabla.get(i), rovaraszok.get(rovaraszIdx)));
               tabla.get(i).getRovarok().add(rovaraszok.get(rovaraszIdx).getRovarok().get(0));
               tabla.get(i).getSzomszedosTektonok().add(tabla.get(i-1));
               tabla.get(i-1).getSzomszedosTektonok().add(tabla.get(i));
               rovaraszIdx++;
            } else {
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


}
