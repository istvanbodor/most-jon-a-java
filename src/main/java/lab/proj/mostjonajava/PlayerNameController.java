package lab.proj.mostjonajava;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.List;

public class PlayerNameController {

    @FXML
    private VBox namesContainer;

    private final List<TextField> nameFields = new ArrayList<>();

    // Hívjuk meg előző képernyőről
    public void setPlayerCount(int count) {
        namesContainer.getChildren().clear(); // előző mezők törlése

        for (int i = 1; i <= 10; i++) {
            Label label = new Label("Játékos " + i + ":");
            TextField textField = new TextField();
            textField.setPromptText("Név");
            textField.setDisable(i > count); // csak az első `count` mező legyen aktív

            HBox row = new HBox(10, label, textField);
            namesContainer.getChildren().add(row);
            nameFields.add(textField);
        }
    }

    @FXML
    public void onStartGameClicked() {
        List<String> names = new ArrayList<>();

        for (TextField field : nameFields) {
            if (!field.isDisabled()) { // csak az aktív mezők számítanak
                String name = field.getText().trim();
                if (name.isEmpty()) {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setHeaderText(null);
                    alert.setContentText("Kérjük, adjon meg nevet minden játékosnak!");
                    alert.showAndWait();
                    return;
                }
                names.add(name);
            }
        }

        System.out.println("Játékosnevek: " + names);
        // Itt jöhet a játék elindítása vagy átadás a modellnek
    }

}
