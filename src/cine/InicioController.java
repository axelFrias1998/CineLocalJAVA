/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cine;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.transitions.hamburger.HamburgerBackArrowBasicTransition;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

public class InicioController implements Initializable {

    @FXML
    private JFXDrawer drawer;

    @FXML
    private JFXHamburger hamburger;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try{
            VBox box = FXMLLoader.load(getClass().getResource("Menu.fxml"));
            drawer.setSidePane(box);
            HamburgerBackArrowBasicTransition burgerTask = new HamburgerBackArrowBasicTransition(hamburger);
            burgerTask.setRate(-1);
            hamburger.addEventHandler(MouseEvent.MOUSE_CLICKED, (e) ->{
                burgerTask.setRate(burgerTask.getRate() * -1);
                burgerTask.play();
                if (drawer.isOpened()) {
                    drawer.close();
                    drawer.setStyle("visibility: false;");
                } else {
                    drawer.open();
                    drawer.setStyle("visibility: true;");
            }
            });
            
        } catch (IOException ex) {
            Logger.getLogger(InicioController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }    
    
}
