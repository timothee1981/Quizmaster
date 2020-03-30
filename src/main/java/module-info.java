module QuizMaster {
    requires javafx.fxml;
    requires javafx.controls;
    requires javafx.graphics;
    requires java.sql;
    requires mysql.connector.java;
    requires lightcouch;
    requires gson;
    requires org.junit.jupiter.api;

    opens view to javafx.graphics, javafx.fxml;
    opens controller to javafx.fxml;
}