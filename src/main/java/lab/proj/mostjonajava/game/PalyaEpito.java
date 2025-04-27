package lab.proj.mostjonajava.game;

import lab.proj.mostjonajava.model.*;
import lab.proj.mostjonajava.utils.Logger;

import java.util.ArrayList;
import java.util.List;

import static lab.proj.mostjonajava.utils.Logger.log;

public class PalyaEpito {

    /**
     * Létrehoz egy 3 tektonból álló lánc-topológiát:
     * - t1–t2 és t2–t3 között fonalak
     * - t1-en egy Gombatest (2 spóra) és egy Rovar
     * - t2-n 3 darab SimaSpora
     */
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

    /**
     * PÁLYA3 – 3 Tektonból áll, az elsőn egy FejlettGombatest van, sehol nincs fonal,
     * de a szomszédságok be vannak állítva. A gombatest 2 spórát tud kilőni.
     */
    public static Jatek palya3()
    {
        Jatek jatek = new Jatek(2, List.of("tesztgombasz", "tesztrovarasz"));

        Tekton t1 = new TobbFonalasTekton();
        Tekton t2 = new TobbFonalasTekton();
        Tekton t3 = new TobbFonalasTekton();

        jatek.getTabla().addAll(List.of(t1,t2,t3));
        Gombatest eredeti = new Gombatest(t1, jatek.getGombaszok().get(0));
        jatek.getGombaszok().get(0).getGombatestek().add(eredeti);
        jatek.getTabla().get(0).setGombatest(jatek.getGombaszok().get(0).getGombatestek().get(0));


        // Szomszédságok
        jatek.getTabla().get(0).getSzomszedosTektonok().add(t2);
        jatek.getTabla().get(1).getSzomszedosTektonok().add(t1);
        jatek.getTabla().get(1).getSzomszedosTektonok().add(t3);
        jatek.getTabla().get(2).getSzomszedosTektonok().add(t2);

        // Gombatest létrehozása
        jatek.getGombaszok().get(0).getGombatestek().get(0).setElszortSporakSzama(0);
        jatek.getGombaszok().get(0).getGombatestek().get(0).setKilohetoSporakSzama(2);
        jatek.getGombaszok().get(0).getGombatestek().get(0).setKilohetoSporakSzama(1);

        // Fejlett példány és beállítás
        FejlettGombatest fejlett = new FejlettGombatest(jatek.getGombaszok().get(0).getGombatestek().get(0));
        jatek.getTabla().get(0).setGombatest(fejlett);

        log(String.valueOf(jatek));
        return jatek;
    }

    /**
     * 2 tektonból álló pálya:
     * - t1–t2 között fonal
     * - t1-en sima gombatest
     * - t2-n 1 sima spóra
     * - t1-en egy rovar, a vágóképessége nem aktív
     */
    public static Jatek palya5()
    {
        Jatek jatek = new Jatek(2, List.of("tesztgombasz", "tesztrovarasz"));

        // Tektonok
        Tekton t1 = new EgyFonalasTekton();
        Tekton t2 = new TobbFonalasTekton();

        jatek.getTabla().addAll(List.of(t1,t2));

        // Szomszédságok: t1–t2
        jatek.getTabla().get(0).getSzomszedosTektonok().add(t2);
        jatek.getTabla().get(1).getSzomszedosTektonok().add(t1);

        // Gombatest t1-en
        Gombatest Gt1 = new Gombatest(jatek.getTabla().get(0), jatek.getGombaszok().get(0));
        jatek.getGombaszok().get(0).getGombatestek().add(Gt1);
        jatek.getTabla().get(0).setGombatest(jatek.getGombaszok().get(0).getGombatestek().get(0));

        // Gombafonal t1–t2
        GombaFonal f12 = new GombaFonal(jatek.getTabla().get(0), jatek.getTabla().get(1), jatek.getGombaszok().get(0).getGombatestek().get(0));
        jatek.getTabla().get(0).getGombafonalak().add(f12);
        jatek.getTabla().get(1).getGombafonalak().add(f12);

        // t2-re 1 sima spóra
        SimaSpora s = new SimaSpora();
        jatek.getTabla().get(1).getSporak().add(s);

        // t1-re egy rovar
        Rovar R1 = new Rovar(jatek.getTabla().get(0), jatek.getRovaraszok().get(0));
        jatek.getTabla().get(0).getRovarok().add(R1);

        //Rovar vágóképessége nem aktív
        jatek.getRovaraszok().get(0).getRovarok().get(0).setVagoKepesseg(false);

        // Pálya visszaadása
        log(String.valueOf(jatek));
        return jatek;
    }

    /**
     * 2 tektonból álló pálya:
     * - t1-en sima gombatest
     * - t2-n 1 sima spóra
     * - t1-en egy rovar
     */
    public static Jatek palya7(){
        Jatek jatek = new Jatek(2, List.of("tesztgombasz", "tesztrovarasz"));

        // Tektonok
        Tekton t1 = new TobbFonalasTekton();
        Tekton t2 = new TobbFonalasTekton();

        jatek.getTabla().addAll(List.of(t1,t2));

        // Szomszédságok: t1–t2
        jatek.getTabla().get(0).getSzomszedosTektonok().add(jatek.getTabla().get(1));
        jatek.getTabla().get(1).getSzomszedosTektonok().add(jatek.getTabla().get(0));

        // Gombatest t1-en
        Gombatest Gt1 = new Gombatest(jatek.getTabla().get(0), jatek.getGombaszok().get(0));
        jatek.getTabla().get(0).setGombatest(Gt1);

        // t2-re 1 sima spóra
        SimaSpora s = new SimaSpora();
        jatek.getTabla().get(1).getSporak().add(s);

        // t1-re egy rovar
        Rovar R1 = new Rovar(jatek.getTabla().get(0), jatek.getRovaraszok().get(0));
        jatek.getTabla().get(0).getRovarok().add(R1);

        // Pálya visszaadása
        log(String.valueOf(jatek));
        return jatek;
    }

    /**
     * 2 tektonból álló pálya:
     * - t1: EltunoFonalasTekton
     * - t2: TobbFonalasTekton
     * - t1–t2 között gombafonal
     * - t1-en sima gombatest (nincs fejlettség, nincs spóra)
     */
    public static Jatek palya8() {
        Jatek jatek = new Jatek(2, List.of("tesztgombasz", "tesztrovarasz"));

        // Tektonok
        Tekton t1 = new EltunoFonalasTekton();
        Tekton t2 = new TobbFonalasTekton();

        jatek.getTabla().addAll(List.of(t1,t2));

        // Szomszédságok: t1–t2
        jatek.getTabla().get(0).getSzomszedosTektonok().add(jatek.getTabla().get(1));
        jatek.getTabla().get(1).getSzomszedosTektonok().add(jatek.getTabla().get(0));

        // Gombatest t1-en
        Gombatest Gt1 = new Gombatest(jatek.getTabla().get(0), jatek.getGombaszok().get(0));
        jatek.getTabla().get(0).setGombatest(Gt1);

        // Gombafonal t1–t2
        GombaFonal f12 = new GombaFonal(jatek.getTabla().get(0), jatek.getTabla().get(1), jatek.getGombaszok().get(0).getGombatestek().get(0));
        jatek.getTabla().get(0).getGombafonalak().add(f12);
        jatek.getTabla().get(0).getGombafonalak().add(f12);

        // Pálya visszaadása
        log(String.valueOf(jatek));
        return jatek;
    }

    /**
     * 1 tektonból álló pálya:
     * - t1: EltunoFonalasTekton
     * - 6 különböző spóra van rajta:
     *   - SimaSpora, BenitoSpora, GyorsitoSpora, LassitoSpora, OsztodoSpora, VagasTiltoSpora
     * - t1-en egy rovar található ("R1")
     * - nincs gombafonal vagy szomszédos tekton
     * - nincs gombatest
     */
    public static Jatek palya9(){
        Jatek jatek = new Jatek(2, List.of("tesztgombasz", "tesztrovarasz"));

        // Tektonok
        Tekton t1 = new EltunoFonalasTekton();

        jatek.getTabla().add(t1);

        // t1-re spórák
        jatek.getTabla().get(0).getSporak().add(new SimaSpora());
        jatek.getTabla().get(0).getSporak().add(new BenitoSpora());
        jatek.getTabla().get(0).getSporak().add(new GyorsitoSpora());
        jatek.getTabla().get(0).getSporak().add(new LassitoSpora());
        jatek.getTabla().get(0).getSporak().add(new OsztodoSpora());
        jatek.getTabla().get(0).getSporak().add(new VagasTiltoSpora());

        // t1-re egy rovar
        Rovar R1 = new Rovar(jatek.getTabla().get(0), jatek.getRovaraszok().get(0));
        jatek.getTabla().get(0).getRovarok().add(R1);

        // Pálya visszaadása
        log(String.valueOf(jatek));
        return jatek;
    }

}
