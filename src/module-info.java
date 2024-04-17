module connect4 {
    requires javafx.controls;
    requires javafx.fxml;
    requires junit;

    exports Test to junit;

    opens ui;
    opens core;
}