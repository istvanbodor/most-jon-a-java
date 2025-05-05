package lab.proj.mostjonajava;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import lab.proj.mostjonajava.game.JatekVezerlo;

import java.io.IOException;
import java.util.List;

public class FungoriumApplication extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("jatekinditas.fxml"));
        Parent root = fxmlLoader.load();

        Scene scene = new Scene(root, 250, 200);
        stage.setTitle("Menu");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) throws IOException {
        launch(args);
        //JatekVezerlo jatekVezerlo = new JatekVezerlo();
    }

}