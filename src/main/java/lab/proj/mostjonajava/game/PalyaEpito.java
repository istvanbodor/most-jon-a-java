package lab.proj.mostjonajava.game;

import lab.proj.mostjonajava.model.*;
import lab.proj.mostjonajava.utils.Logger;

import java.util.ArrayList;
import java.util.List;

import static lab.proj.mostjonajava.utils.Logger.log;

public class PalyaEpito {



    public static Jatek palya1() {
        Jatek jatek = new Jatek(2, List.of("tesztgombasz", "tesztrovarasz"));
        Tekton t1 = new TobbFonalasTekton();
        Tekton t2 = new TobbFonalasTekton();
        Tekton t3 = new TobbFonalasTekton();
        jatek.getTabla().addAll(List.of(t1,t2,t3));
        Gombatest gt = new Gombatest(t1, jatek.getGombaszok().get(0));
        jatek.getGombaszok().get(0).getGombatestek().add(gt);
        GombaFonal f12 = new GombaFonal(t1, t2, jatek.getGombaszok().get(0).getGombatestek().get(0));
        GombaFonal f23 = new GombaFonal(t2, t3, jatek.getGombaszok().get(0).getGombatestek().get(0));
        jatek.getTabla().get(0).setGombafonal(f12);
        jatek.getTabla().get(1).setGombafonal(f12);
        jatek.getTabla().get(1).setGombafonal(f23);
        jatek.getTabla().get(2).setGombafonal(f23);

        jatek.getTabla().get(0).setGombatest(jatek.getGombaszok().get(0).getGombatestek().get(0));
        jatek.getGombaszok().get(0).getGombatestek().get(0).setElszortSporakSzama(3);
        jatek.getGombaszok().get(0).getGombatestek().get(0).setKilohetoSporakSzama(2);

        jatek.getTabla().get(0).getSzomszedosTektonok().add(t2);
        jatek.getTabla().get(1).getSzomszedosTektonok().add(t1);
        jatek.getTabla().get(1).getSzomszedosTektonok().add(t3);
        jatek.getTabla().get(2).getSzomszedosTektonok().add(t2);

        Rovar rovar = new Rovar(t1, jatek.getRovaraszok().get(0));
        jatek.getTabla().get(0).getRovarok().add(rovar);
        jatek.getRovaraszok().get(0).getRovarok().add(rovar);

        jatek.getTabla().get(1).getSporak().add(new SimaSpora());
        jatek.getTabla().get(1).getSporak().add(new SimaSpora());
        jatek.getTabla().get(1).getSporak().add(new SimaSpora());
        log(String.valueOf(jatek));
        return jatek;
    }

}
