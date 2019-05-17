/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cine;


import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.transitions.hamburger.HamburgerBackArrowBasicTransition;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

/**
 * FXML Controller class
 *
 * @author axelf
 */
public class InicioController implements Initializable {

    @FXML
    private Label prueba;
    
     @FXML
    private JFXHamburger hamburger;

    @FXML
    private JFXButton crearPelicula;

    @FXML
    private JFXDrawer drawer;

    @FXML
    private Pane panelTrabajo;
    
    @FXML
    void crearPelicula(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("CrearPelicula.fxml"));
        
        Scene scene = new Scene(root);
        javafx.stage.Stage stage = (javafx.stage.Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.toFront();
        stage.show();
                        
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try{
            VBox box = FXMLLoader.load(getClass().getResource("Menu.fxml"));
            drawer.setSidePane(box);
            
            for(Node node: box.getChildren()){
                if(node.getAccessibleText() != null){
                    node.addEventHandler(MouseEvent.MOUSE_CLICKED, (e) ->{
                        switch case:
                    });
                }
            }
            HamburgerBackArrowBasicTransition transition = new HamburgerBackArrowBasicTransition(hamburger);
            transition.setRate(-1);
            hamburger.addEventHandler(MouseEvent.MOUSE_PRESSED, (e) -> {
                transition.setRate(transition.getRate() * -1);
                transition.play();

                if (drawer.isOpened()) {
                    drawer.close();
                    drawer.setStyle("visibility:false");
                } else {
                    drawer.open();
                    drawer.setStyle("visibility:true");
                }
            });
        } catch (IOException ex) {
            System.out.println(ex);
        }
        
    }    
    
}
