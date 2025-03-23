package lab.proj.mostjonajava.utils;

import java.util.ArrayList;

public class HivasLogger {

    /**
     * A tesztelést segítő logger függvény - kiírja a hívott függvényt és annak paramétereit
     * @param hivottFuggveny a hívott függvény
     * @param parameterek a hívott függvény paraméterei
     */
    public static void hivasLogolasa(String hivottFuggveny, ArrayList<String> parameterek) {
        System.out.println(hivottFuggveny + "fut a következő paraméterekkel: " + parameterek);
    }
}
