/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cine;

import com.jfoenix.controls.JFXDecorator;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author axelf
 */
public class LoginController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }    
    
    public void IniciaSesion() throws IOException{
        Parent root = FXMLLoader.load(getClass().getResource("Home.fxml"));
        Stage stage = new Stage();
        
        JFXDecorator decorator = new JFXDecorator(stage, root); 
        Scene scene = new Scene(decorator);
        
        stage.setScene(scene);
        stage.show();
        
        Stage window = null;
        window.setScene(scene);
    }
    
}
