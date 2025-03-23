package lab.proj.mostjonajava.game;

import lab.proj.mostjonajava.model.Gombasz;
import lab.proj.mostjonajava.model.Rovar;
import lab.proj.mostjonajava.model.Tekton;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Jatek {
    public static List<Tekton> tektonok;

    public static List<Rovar> rovaraszok;

    public static List<Gombasz> gombaszok;

    public Jatek() {
        tektonok = new ArrayList<>();
        rovaraszok = new ArrayList<>();
        gombaszok = new ArrayList<>();
    }

    //todo itt lesz a jatek logikaja - ez a "kontroller"
    //todo ki kell még egészíteni ezt az osztályt
}
