/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cine;

import com.jfoenix.controls.JFXButton;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

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
    void muestraAgregaEstreno(ActionEvent event) {

    }

    @FXML
    void muestraEstadoPeliculas(ActionEvent event) {

    }

    @FXML
    void muestraCrearFuncion(ActionEvent event) {

    }

    @FXML
    void muestraAnexaSala(ActionEvent event) {

    }

    @FXML
    void muestraOrdenes(ActionEvent event) {

    }
    @FXML
    void muestraRegistroAdministrador(ActionEvent event) {

    }

    @FXML
    void mostrarControlUsuarios(ActionEvent event) {

    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        btnAnexaSala.setAccessibleText("AnexaSala");
    }    
    
}
