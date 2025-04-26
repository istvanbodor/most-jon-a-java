package lab.proj.mostjonajava.game;

import lab.proj.mostjonajava.model.Tekton;
import lab.proj.mostjonajava.model.TobbFonalasTekton;

import java.util.List;

public class PalyaEpito {



    public static Jatek palya1() {
        Jatek jatek = new Jatek(1, List.of());
        Tekton tekton = new TobbFonalasTekton();
        jatek.getTabla().add(tekton);
        return jatek;
    }

}
