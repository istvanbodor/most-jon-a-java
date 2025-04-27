package lab.proj.mostjonajava;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import lab.proj.mostjonajava.game.JatekVezerlo;
import lab.proj.mostjonajava.game.PalyaEpito;
import lab.proj.mostjonajava.model.*;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import static lab.proj.mostjonajava.utils.Logger.hibaLog;
import static lab.proj.mostjonajava.utils.Logger.log;
import static lab.proj.mostjonajava.utils.Parancsok.*;

import lab.proj.mostjonajava.game.Jatek;

public class FungoriumApplication extends Application {

    @Override
    public void start(Stage stage){
    }

    public static void main(String[] args) throws IOException {
        JatekVezerlo jatekVezerlo = new JatekVezerlo();
    }

}