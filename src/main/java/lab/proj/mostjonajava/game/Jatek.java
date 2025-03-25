package lab.proj.mostjonajava.game;

import lab.proj.mostjonajava.model.Gombasz;
import lab.proj.mostjonajava.model.Rovar;
import lab.proj.mostjonajava.model.Tekton;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

import static lab.proj.mostjonajava.utils.Logger.hivasLog;

@Data
public class Jatek {
    public static List<Tekton> tektonok;

    public static List<Rovar> rovaraszok;

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
              Rovar rovar = new Rovar(nev);
            } else {
               Gombasz gombasz = new Gombasz(nev);
            }
        }
    }

}
