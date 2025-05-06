package lab.proj.mostjonajava;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.event.ActionEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class JatekosokNeveNezetController {

    @FXML
    private VBox nevBemenetContainer;

    private final List<TextField> nameFields = new ArrayList<>();

    // Called from previous screen
    public void setPlayerCount(int count) {
        nevBemenetContainer.getChildren().clear();
        nameFields.clear();

        for (int i = 1; i <= count; i++) {
            TextField textField = new TextField();
            textField.setPromptText("Player " + i + " name");
            nevBemenetContainer.getChildren().add(textField);
            nameFields.add(textField);
        }
    }

    @FXML
    public void onJatekInditasClick(ActionEvent event) throws IOException {
        List<String> names = new ArrayList<>();

        // First validate all fields
        for (TextField field : nameFields) {
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
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        GrafikusJatekVezerlo jatekVezerlo = new GrafikusJatekVezerlo(names, stage);

        // If validation passed, load next screen
//        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("rovarasznezet.fxml"));
//        Parent root = fxmlLoader.load();
//
//        Scene scene = new Scene(root); // No fixed height
//        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
//        stage.setScene(scene);
//        stage.setTitle("Game");
//        stage.sizeToScene(); // Resize to fit the layout
//        stage.show();
//
//        System.out.println("Játékosnevek: " + names);
        // Pass names to the game controller here if needed
    }
}
