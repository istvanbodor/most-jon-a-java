package lab.proj.mostjonajava;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import static lab.proj.mostjonajava.utils.Parancsok.*;

public class FungoriumApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(FungoriumApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        stage.setTitle("");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) throws IOException {
        String bemenet;
        String[] parameterek;
        String parancs;
        BufferedReader streamReader = new BufferedReader(new InputStreamReader(System.in));
        while (true) {
            System.out.println("Parancsolj: ");
            bemenet = streamReader.readLine();
            parameterek = bemenet.split(" ");
            parancs = parameterek[0];
            switch (parancs) {
                case JATEKINDITAS -> jatekInditasa(parameterek);
                case FONALNOVESZTES -> testFonalNovesztes(parameterek);
                case SPORATERMELES -> testSporaTermeles(parameterek);
                case SIMASPORASZORAS -> testSimaSporaSzoros(parameterek);
                case FEJLETTSPORASZORAS -> testFejlettSporaSzoros(parameterek);
                case GOMBATESTNOVESZTES -> testGombaTestNovesztes(parameterek);
                case GOMBATESTFEJLESZTES -> testGombaTestFejlesztes(parameterek);
                case GOMBATESTELPUSZTUL -> testGombaTestElpusztul(parameterek);
                case ROVARMOZGATAS -> testRovarMozgatas(parameterek);
                case ROVARMOZGATASBLOKKOLAS -> testRovarMozgatasBlokkolas(parameterek);
                case SPORAFOGYASZTAS -> testSporaFogyasztas(parameterek);
                case FONALVAGAS -> testFonalVagas(parameterek);
                case TEKTONTORES -> testTektonTores(parameterek);
                case BENITOSPORAHATASKIFEJTESE -> testBenitoSporaHatasKifejtese(parameterek);
                case LASSITOSPORAHATASKIFEJTESE -> testLassitoSporaHatasKifejtese(parameterek);
                case GYORSITOSPORAHATASKIFEJTESE -> testGyorsitoSporaHatasKifejtese(parameterek);
                case VAGASBENITOSPORAHATASKIFEJTESE -> testVagasBenitoSporaHatasKifejtese(parameterek);
                case FONALFELSZIVODAS -> testFonalFelszivodas(parameterek);
                case KILEPES -> System.exit(0);
                default -> System.out.println("Nem létező parancsot adtál meg!");
            }
        }
    }


    //itt kell megírni a teszteket
    //a parameterek[1] től kezdődnek a paraméterek, paraméter ellenőrzés először, ha rosszakat adott meg vagy nem eleget
    //akkor hibaüzenet, ha jók akkor továbbhívni ezekkel a paraméterekkel
    //kelleni fog egy játék osztály példány globálisan, ebben kell az állapotokat változtatni
    //todo a két játék init teszteset még hiányzik a parancsok közül - azokat megfogalmazni stb.
    //mvn clean javafx:run ezzel a paranccsal futtatható az app zöld gomb nélkül is


    private static void jatekInditasa(String[] parameterek) {
    }

    private static void testFonalNovesztes(String[] parameterek) {
        //todo teszteset
    }

    private static void testSporaTermeles(String[] parameterek) {
        //todo teszteset
    }

    private static void testSimaSporaSzoros(String[] parameterek) {
        //todo teszteset
    }

    private static void testFejlettSporaSzoros(String[] parameterek) {
        //todo teszteset
    }

    private static void testGombaTestNovesztes(String[] parameterek) {
        //todo teszteset
    }

    private static void testGombaTestFejlesztes(String[] parameterek) {
        //todo teszteset
    }

    private static void testGombaTestElpusztul(String[] parameterek) {
        //todo teszteset
    }

    private static void testRovarMozgatas(String[] parameterek) {
        //todo teszteset
    }

    private static void testRovarMozgatasBlokkolas(String[] parameterek) {
        //todo teszteset
    }

    private static void testSporaFogyasztas(String[] parameterek) {
        //todo teszteset
    }

    private static void testFonalVagas(String[] parameterek) {
        //todo teszteset
    }

    private static void testTektonTores(String[] parameterek) {
        //todo teszteset
    }

    private static void testBenitoSporaHatasKifejtese(String[] parameterek) {
        //todo teszteset
    }

    private static void testLassitoSporaHatasKifejtese(String[] parameterek) {
        //todo teszteset
    }

    private static void testGyorsitoSporaHatasKifejtese(String[] parameterek) {
        //todo teszteset
    }

    private static void testVagasBenitoSporaHatasKifejtese(String[] parameterek) {
        //todo teszteset
    }

    private static void testFonalFelszivodas(String[] parameterek) {
        //todo teszteset
    }

}