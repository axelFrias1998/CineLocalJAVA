/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cine;


import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDecorator;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import java.io.IOException;
import java.net.URL;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author axelf
 */
public class LoginController implements Initializable {

    @FXML
    private JFXButton btnInicia;

    @FXML
    private JFXTextField txtCorreo;

    @FXML
    private JFXPasswordField txtContrasenia;

    @FXML
    void IniciaSesion(ActionEvent event) throws IOException, ClassNotFoundException {
        
        try{
            Class.forName("com.mysql.jdbc.Driver");

            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/CineDB","root","");
            
            CallableStatement call = con.prepareCall("{call inicioSesion_sp(?)}");
            call.setString(1, txtCorreo.getText());
            
            boolean hash = call.execute();
            
            if(hash){
                  try(ResultSet rs = call.getResultSet()){
                      if(rs.next()){
                          String spPass = rs.getString(1);
                          System.out.println(spPass);
                          System.out.println(txtContrasenia.getText());
                          if(spPass.equalsIgnoreCase(txtContrasenia.getText()))
                              System.out.println("EXISTE Y JAJSDAJS");
                          else
                              System.out.println("Vale verga");
                      }
                  }catch(SQLException ex){}
//                Stage actual = (Stage)((Node)event.getSource()).getScene().getWindow();
//                actual.hide();
//
//                Parent root = FXMLLoader.load(getClass().getResource("Home.fxml"));
//                Stage nuevo = new Stage();
//                JFXDecorator decorator = new JFXDecorator(nuevo, root); 
//                Scene scene = new Scene(decorator);
//                nuevo.setScene(scene);
//                nuevo.show();
            }
            else
                System.out.println("Sin respuesta");

       
            con.close();

        }catch(SQLException e){ System.out.println(e);}
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }        
}
