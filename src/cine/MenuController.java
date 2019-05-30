/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cine;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDecorator;
import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
/**
 * FXML Controller class
 *
 * @author axelf
 */
public class MenuController implements Initializable {
    
     @FXML
    void agregaEstreno(ActionEvent event) throws IOException {
        Stage actual = (Stage)((Node)event.getSource()).getScene().getWindow();
        actual.hide();

        Parent root = FXMLLoader.load(getClass().getResource("CrearPelicula.fxml"));
        Stage stage = new Stage();
        JFXDecorator decorator = new JFXDecorator(stage, root);
        decorator.setCustomMaximize(true);
        Scene scene = new Scene(decorator);
        String estilo = getClass().getResource("estilos.css").toExternalForm();
        scene.getStylesheets().add(estilo);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void estadoPeliculas(ActionEvent event) throws IOException {
        Stage actual = (Stage)((Node)event.getSource()).getScene().getWindow();
        actual.hide();

        Parent root = FXMLLoader.load(getClass().getResource("EstadoPeliculas.fxml"));
        Stage stage = new Stage();
        JFXDecorator decorator = new JFXDecorator(stage, root);
        decorator.setCustomMaximize(true);
        Scene scene = new Scene(decorator);
        String estilo = getClass().getResource("estilos.css").toExternalForm();
        scene.getStylesheets().add(estilo);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void anexaSala(ActionEvent event) throws IOException {
        Stage actual = (Stage)((Node)event.getSource()).getScene().getWindow();
        actual.hide();

        Parent root = FXMLLoader.load(getClass().getResource("Sala.fxml"));
        Stage stage = new Stage();
        JFXDecorator decorator = new JFXDecorator(stage, root);
        decorator.setCustomMaximize(true);
        Scene scene = new Scene(decorator);
        String estilo = getClass().getResource("estilos.css").toExternalForm();
        scene.getStylesheets().add(estilo);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void ordenesGeneradas(ActionEvent event) throws IOException {
        Stage actual = (Stage)((Node)event.getSource()).getScene().getWindow();
        actual.hide();

        Parent root = FXMLLoader.load(getClass().getResource("Ordenes.fxml"));
        Stage stage = new Stage();
        JFXDecorator decorator = new JFXDecorator(stage, root);
        decorator.setCustomMaximize(true);
        Scene scene = new Scene(decorator);
        String estilo = getClass().getResource("estilos.css").toExternalForm();
        scene.getStylesheets().add(estilo);
        stage.setScene(scene);
        stage.show();

    }

    @FXML
    void registroAdministrador(ActionEvent event) throws IOException {
        Stage actual = (Stage)((Node)event.getSource()).getScene().getWindow();
        actual.hide();

        Parent root = FXMLLoader.load(getClass().getResource("RegistroAdministrador.fxml"));
        Stage stage = new Stage();
        JFXDecorator decorator = new JFXDecorator(stage, root);
        decorator.setCustomMaximize(true);
        Scene scene = new Scene(decorator);
        String estilo = getClass().getResource("estilos.css").toExternalForm();
        scene.getStylesheets().add(estilo);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void controlUsuarios(ActionEvent event) throws IOException {
        Stage actual = (Stage)((Node)event.getSource()).getScene().getWindow();
        actual.hide();

        Parent root = FXMLLoader.load(getClass().getResource("ControlUsuario.fxml"));
        Stage stage = new Stage();
        JFXDecorator decorator = new JFXDecorator(stage, root);
        decorator.setCustomMaximize(true);
        Scene scene = new Scene(decorator);
        String estilo = getClass().getResource("estilos.css").toExternalForm();
        scene.getStylesheets().add(estilo);
        stage.setScene(scene);
        stage.show();

    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
