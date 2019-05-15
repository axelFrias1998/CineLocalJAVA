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
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author axelf
 */
public class LoginController implements Initializable {

     @FXML
    private Label lblContrasenia;

    @FXML
    private Label lblCorreo;

    @FXML
    private JFXButton btnInicia;

    @FXML
    private JFXTextField txtCorreo;

    @FXML
    private JFXPasswordField txtContrasenia;


    @FXML
    void IniciaSesion(ActionEvent event) throws IOException, ClassNotFoundException, SQLException {
        if(!"".equals(txtCorreo.getText()) && !"".equals(txtContrasenia.getText())){
            try{
                Class.forName("com.mysql.jdbc.Driver");
                Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/CineDB","root","");
                
                //Objeto llamada procedimiento almacenado
                CallableStatement call = con.prepareCall("{call inicioSesion_sp(?)}");
                //Envío parámetro a procedimiento almacenado
                call.setString(1, txtCorreo.getText());
                
                //Si existe respuesta
                boolean hash = call.execute();
                if(hash){
                    Integer Id = null, Rol = null;
                    String spNombre = null, spPass = null, spEmail= null;
                    ResultSet rs = call.getResultSet();
//                    id nombre pass email rol
                    while(rs.next()){
                        Id = rs.getInt(1);
                        Rol = rs.getInt(5);
                        spNombre = rs.getString(2);
                        spPass = rs.getString(3);
                        spEmail = rs.getString(4);
                    }
                    if(spPass.equals(txtContrasenia.getText()) && Rol == 1){
                        SesionUsuario.getInstance(spNombre, spEmail, Rol, Id);
//                        Stage actual = (Stage)((Node)event.getSource()).getScene().getWindow();
                        Parent root = FXMLLoader.load(getClass().getResource("Home.fxml"));
                        Stage nuevo = new Stage();
                        JFXDecorator decorator = new JFXDecorator(nuevo, root); 
                        Scene scene = new Scene(decorator);
                        nuevo.setScene(scene);
                        nuevo.show();
//                        actual.hide();
                    }
                    else if(Rol != 1){
                        Alert alert = new Alert(AlertType.ERROR);
                        alert.setTitle("Error de acceso");
                        alert.setContentText("No tienes permiso para acceder al sistema. Entra con una cuenta válida.");
                        alert.showAndWait();
                    }
                    else if(!spPass.equals(txtContrasenia.getText())){
                        Alert alert = new Alert(AlertType.ERROR);
                        alert.setTitle("Error de credenciales");
                        alert.setContentText("Usuario y/o contraseña incorrectos.");
                        alert.showAndWait();
                    }
                    con.close();
                }    
            }catch(SQLException ex){ System.out.println(ex);};
        }
        else if("".equals(txtCorreo.getText())){
            lblCorreo.setText("¡Ingresa un correo válido!");
            lblCorreo.setVisible(true);
        }
        else if("".equals(txtContrasenia.getText())){
            lblContrasenia.setText("¡Ingresa una contraseña válida!");
            lblContrasenia.setVisible(true);
        }
        else{
            lblContrasenia.setVisible(false);
            lblCorreo.setVisible(false);
        }
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }        
}
