module org.example.priya_kashyap_assignment5 {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;
    requires com.almasb.fxgl.all;

    opens org.example.priya_kashyap_assignment5 to javafx.fxml;
    exports org.example.priya_kashyap_assignment5;
}