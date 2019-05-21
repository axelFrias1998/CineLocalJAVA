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
    private JFXButton btnAnexaSala;

    @FXML
    private JFXButton btnControlDeUsuarios;

    @FXML
    private JFXButton btnAgregaEstreno;

    @FXML
    private JFXButton btnEstadoPeliculas;

    @FXML
    private JFXButton btnCreaFuncion;

    @FXML
    private JFXButton btnOrdenesGeneradas;

    @FXML
    private JFXButton btnRegistro;
    
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
    void estadoPeliculas(ActionEvent event) {

    }

    @FXML
    void creaFuncion(ActionEvent event) {

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
    void ordenesGeneradas(ActionEvent event) {

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
    void controlUsuarios(ActionEvent event) {

    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
