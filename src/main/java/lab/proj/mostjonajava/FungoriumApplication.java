package lab.proj.mostjonajava;

import javafx.application.Application;
import javafx.stage.Stage;
import lab.proj.mostjonajava.game.JatekVezerlo;

import java.io.IOException;

public class FungoriumApplication extends Application {

    @Override
    public void start(Stage stage){
    }

    public static void main(String[] args) throws IOException {
        JatekVezerlo jatekVezerlo = new JatekVezerlo();
    }

}