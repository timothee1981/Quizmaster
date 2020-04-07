module Quizmaster {
    requires javafx.fxml;
    requires javafx.controls;
    requires javafx.graphics;
    requires java.sql;
    requires mysql.connector.java;
    requires lightcouch;
    requires gson;

    opens database.nosql to gson, lightcouch, java.sql;
    opens model to gson;


    opens view to javafx.graphics, javafx.fxml;
    opens controller to javafx.fxml;

}