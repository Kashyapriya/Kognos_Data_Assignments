module org.example.priya_kashyap_assignment_3 {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;
    requires com.almasb.fxgl.all;

    opens org.example.priya_kashyap_assignment_3 to javafx.fxml;
    exports org.example.priya_kashyap_assignment_3;
}