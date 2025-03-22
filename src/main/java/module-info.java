module lab.proj.mostjonajava {
    requires javafx.controls;
    requires javafx.fxml;


    opens lab.proj.mostjonajava to javafx.fxml;
    exports lab.proj.mostjonajava;
}