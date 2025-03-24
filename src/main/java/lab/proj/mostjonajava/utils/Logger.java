package lab.proj.mostjonajava.utils;

import java.util.ArrayList;
import java.util.List;

public class Logger {

    /**
     * A tesztelést segítő logger függvény - kiírja a hívott függvényt és annak paramétereit
     *
     * @param hivottFuggveny a hívott függvény
     * @param parameterek    a hívott függvény paraméterei
     */
    public static void hivasLog(String hivottFuggveny, List<String> parameterek) {
        System.out.println("-> " + hivottFuggveny + " fut a kovetkezo parameterekkel: " + parameterek);
    }

    public static void hibaLog(String hiba) {
        System.out.println("Hiba: " + hiba);
    }

    public static void log(String uzenet) {
        System.out.println(uzenet);
    }
}
