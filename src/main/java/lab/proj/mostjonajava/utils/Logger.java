package lab.proj.mostjonajava.utils;

import java.util.List;

public class Logger {

    /**
     * A tesztelést segítő logger függvény - kiírja a hívott függvényt és annak paramétereit
     *
     * @param hivottFuggveny a hívott függvény
     * @param parameterek    a hívott függvény paraméterei
     */
    public static void hivasLog(String hivottFuggveny, List<String> parameterek, int szint) {
        System.out.println("\t".repeat(Math.max(0, szint)) + "-> " + hivottFuggveny + " fut a kovetkezo parameterekkel: " + parameterek);
    }

    /**
     * Ez egy hiba logolás céljából létrehozott segédföggvény
     * @param hiba a hibaüzenet
     */
    public static void hibaLog(String hiba) {
        System.out.println("Hiba: " + hiba);
    }

    /**
     *  Sima logger függvény
     * @param uzenet a logolandó üzenet
     */
    public static void log(String uzenet) {
        System.out.println(uzenet);
    }
}
