package lab.proj.mostjonajava;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import static lab.proj.mostjonajava.utils.Parancsok.*;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) throws IOException {
        String bemenet;
        String parancs;
        BufferedReader streamReader = new BufferedReader(new InputStreamReader(System.in));
        while (true) {
            System.out.println("Parancsolj: ");
            bemenet = streamReader.readLine();
            parancs = bemenet.split(" ")[0];
            switch (parancs) {
                case FONALNOVESZTES -> testFonalNovesztes(bemenet);
                case SPORATERMELES -> testSporaTermeles(bemenet);
                case SIMASPORASZORAS -> testSimaSporaSzoros(bemenet);
                case FEJLETTSPORASZORAS -> testFejlettSporaSzoros(bemenet);
                case GOMBATESTNOVESZTES -> testGombaTestNovesztes(bemenet);
                case GOMBATESTFEJLESZTES -> testGombaTestFejlesztes(bemenet);
                case GOMBATESTELPUSZTUL -> testGombaTestElpusztul(bemenet);
                case ROVARMOZGATAS -> testRovarMozgatas(bemenet);
                case ROVARMOZGATASBLOKKOLAS -> testRovarMozgatasBlokkolas(bemenet);
                case SPORAFOGYASZTAS -> testSporaFogyasztas(bemenet);
                case FONALVAGAS -> testFonalVagas(bemenet);
                case TEKTONTORES -> testTektonTores(bemenet);
                case BENITOSPORAHATASKIFEJTESE -> testBenitoSporaHatasKifejtese(bemenet);
                case LASSITOSPORAHATASKIFEJTESE -> testLassitoSporaHatasKifejtese(bemenet);
                case GYORSITOSPORAHATASKIFEJTESE -> testGyorsitoSporaHatasKifejtese(bemenet);
                case VAGASBENITOSPORAHATASKIFEJTESE -> testVagasBenitoSporaHatasKifejtese(bemenet);
                case FONALFELSZIVODAS -> testFonalFelszivodas(bemenet);
                case KILEPES -> System.exit(0);
                default -> System.out.println("Nem létező parancsot adtál meg!");
            }
        }
    }

    //itt kell megírni a teszteket
    //a bemenet.split(" ")[1] től kezdődnek a paraméterek, paraméter ellenőrzés először, ha rosszakat adott meg vagy nem eleget
    //akkor hibaüzenet, ha jók akkor továbbhívni ezekkel a paraméterekkel
    //kelleni fog egy játék osztály példány globálisan, ebben kell az állapotokat változtatni
    //todo a két játék init teszteset még hiányzik a parancsok közül - azokat megfogalmazni stb.
    //mvn clean javafx:run ezzel a paranccsal futtatható az app zöld gomb nélkül is


    private static void testFonalNovesztes(String bemenet) {
        //todo teszteset
    }

    private static void testSporaTermeles(String bemenet) {
        //todo teszteset
    }

    private static void testSimaSporaSzoros(String bemenet) {
        //todo teszteset
    }

    private static void testFejlettSporaSzoros(String bemenet) {
        //todo teszteset
    }

    private static void testGombaTestNovesztes(String bemenet) {
        //todo teszteset
    }

    private static void testGombaTestFejlesztes(String bemenet) {
        //todo teszteset
    }

    private static void testGombaTestElpusztul(String bemenet) {
        //todo teszteset
    }

    private static void testRovarMozgatas(String bemenet) {
        //todo teszteset
    }

    private static void testRovarMozgatasBlokkolas(String bemenet) {
        //todo teszteset
    }

    private static void testSporaFogyasztas(String bemenet) {
        //todo teszteset
    }

    private static void testFonalVagas(String bemenet) {
        //todo teszteset
    }

    private static void testTektonTores(String bemenet) {
        //todo teszteset
    }

    private static void testBenitoSporaHatasKifejtese(String bemenet) {
        //todo teszteset
    }

    private static void testLassitoSporaHatasKifejtese(String bemenet) {
        //todo teszteset
    }

    private static void testGyorsitoSporaHatasKifejtese(String bemenet) {
        //todo teszteset
    }

    private static void testVagasBenitoSporaHatasKifejtese(String bemenet) {
        //todo teszteset
    }

    private static void testFonalFelszivodas(String bemenet) {
        //todo teszteset
    }

}