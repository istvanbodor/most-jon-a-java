package lab.proj.mostjonajava;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import lab.proj.mostjonajava.game.JatekVezerlo;

import java.io.IOException;
import java.util.List;

/**
 * A Fungorium játék fő alkalmazás osztálya, amely kezeli a JavaFX alkalmazás indítását
 * és a kezdő felület megjelenítését.
 * Ez az osztály a {@link Application} osztályból származik, és implementálja annak
 * absztrakt metódusait a JavaFX alkalmazás működéséhez.
 */
public class FungoriumApplication extends Application {
    /**
     * A JavaFX alkalmazás fő metódusa, amely inicializálja és megjeleníti
     * a kezdő felületet.
     *
     * @param stage Az elsődleges színpad (stage), amelyre a felület kerül.
     * @throws Exception Ha bármilyen hiba történik a felület betöltése közben.
     */
    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("jatekmenunezet.fxml"));
        Parent root = fxmlLoader.load();

        Scene scene = new Scene(root, 250, 200);
        stage.setTitle("Menu");
        stage.setScene(scene);
        stage.show();
    }
    /**
     * A program belépési pontja. Elindítja a JavaFX alkalmazást.
     *
     * @param args parancssori argumentumok
     * @throws IOException Ha I/O hiba történik az alkalmazás indítása közben.
     */
    public static void main(String[] args) throws IOException {
        launch(args);
        //JatekVezerlo jatekVezerlo = new JatekVezerlo();
    }

}