package lab.proj.mostjonajava.game;

import java.util.ArrayList;
import java.util.List;

import lab.proj.mostjonajava.model.*;

import static lab.proj.mostjonajava.utils.Logger.hivasLog;
import lombok.Data;

@Data
public class Jatek {
    public static List<Tekton> tektonok;

    public static List<Rovarasz> rovaraszok;

    public static List<Gombasz> gombaszok;

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
                Rovarasz rovarasz = new Rovarasz(nev);
            } else {
               Gombasz gombasz = new Gombasz(nev);
            }
        }
    }

    public Gombatest keresGombatestById(int id){
        Gombatest test = new Gombatest();
        return test;
    }

    public Tekton keresTektonById(int id){
        Tekton t = new FonalEltetoTekton();
        return t;
    }

}
