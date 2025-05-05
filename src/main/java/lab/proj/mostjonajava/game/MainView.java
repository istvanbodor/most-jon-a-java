package lab.proj.mostjonajava.game;

import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class MainView {
    @FXML
    private Button startButton;

    @FXML
    public void onStartButtonClick() {
        System.out.println("Játék indítása...");
    }
}