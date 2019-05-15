package cine;

import com.jfoenix.controls.JFXDecorator;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Cine extends Application {
    
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("Login.fxml"));
        
        Scene scene = new Scene(root);
        stage.setScene(scene);
//        stage.res
//        stage.resizableProperty().setValue(Boolean.FALSE);
//        stage.initStyle(StageStyle.UTILITY);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
    
}
