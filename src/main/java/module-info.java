module QuizMaster {
    requires javafx.fxml;
    requires javafx.controls;
    requires javafx.graphics;
    requires java.sql;
    requires mysql.connector.java;
    requires lightcouch;
    requires gson;
    requires org.junit.jupiter.api;

    opens database.nosql to gson, lightcouch, java.sql;
    opens model to gson;
    opens view to javafx.graphics, javafx.fxml;
    opens controller to javafx.fxml;
}