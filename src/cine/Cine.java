package cine;

import com.jfoenix.controls.JFXDecorator;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Cine extends Application {
    
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("Login.fxml"));
        JFXDecorator decorator = new JFXDecorator(stage, root);
        decorator.setCustomMaximize(true);
        Scene scene = new Scene(decorator);
        String estilo = getClass().getResource("estilos.css").toExternalForm();
        scene.getStylesheets().add(estilo);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
    
}
