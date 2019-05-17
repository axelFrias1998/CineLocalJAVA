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
import com.jfoenix.validation.RequiredFieldValidator;
import java.awt.Paint;
import java.io.IOException;
import java.net.URL;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;

/**
 * FXML Controller class
 *
 * @author axelf
 */
public class LoginController implements Initializable {

    @FXML
    private Label lblPrueba;
    
    @FXML
    private JFXButton btnInicia;

    @FXML
    private JFXTextField txtCorreo;

    @FXML
    private JFXPasswordField txtContrasenia;


    @FXML
    void IniciaSesion(ActionEvent event) throws IOException, ClassNotFoundException, SQLException {
        if(!"".equals(txtCorreo.getText()) && !"".equals(txtContrasenia.getText())){
            if(Validaciones.validaEmail(txtCorreo.getText())){
                try{
                    Class.forName("com.mysql.cj.jdbc.Driver");
                    Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/CineDB?useTimezone=true&serverTimezone=UTC","root","Suripanta.98");
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
                        if(rs.next()){
                            Id = rs.getInt(1);
                            Rol = rs.getInt(5);
                            spNombre = rs.getString(2);
                            spPass = rs.getString(3);
                            spEmail = rs.getString(4);
                            if(spPass.equals(txtContrasenia.getText()) && Rol == 1){                                
                                
                                Stage actual = (Stage)((Node)event.getSource()).getScene().getWindow();
                                actual.hide();
                                
                                Parent root = FXMLLoader.load(getClass().getResource("Inicio.fxml"));
                                Stage stage = new Stage();
                                JFXDecorator decorator = new JFXDecorator(stage, root);
                                decorator.setCustomMaximize(true);
                                Scene scene = new Scene(decorator);
                                String estilo = getClass().getResource("estilos.css").toExternalForm();
                                scene.getStylesheets().add(estilo);
                                stage.setScene(scene);
                                stage.show();


                                lblPrueba.setText(SesionUsuario.getInstance().getNombre());
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
                        }
                        else{
                            Alert alert = new Alert(AlertType.ERROR);
                                alert.setTitle("Error de credenciales");
                                alert.setContentText("No existe dicho usuario");
                                alert.showAndWait();
                        }
                        
                        con.close();
                    }    
                }catch(SQLException ex){ System.out.println(ex);};
            }
            else{
                Alert alert = new Alert(AlertType.ERROR);
                            alert.setTitle("Correo inválido");
                            alert.setContentText("Ingresa un correo válido");
                            alert.showAndWait();
            }    
        }
        else{
            Alert alert = new Alert(AlertType.ERROR);
            
            alert.setTitle("Formulario incompleto");
            alert.setContentText("No puedes dejar campos vacíos");
            alert.showAndWait();
        }
    }
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        RequiredFieldValidator  validaCorreo = new RequiredFieldValidator();
        txtCorreo.getValidators().add(validaCorreo);
        validaCorreo.setMessage("Ingresa un correo"); 
        txtCorreo.focusedProperty().addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
            if(!newValue)
            {
                txtCorreo.validate();
            }
        });
        
        RequiredFieldValidator  validaPass = new RequiredFieldValidator();
        txtContrasenia.getValidators().add(validaPass);
        validaPass.setMessage("Ingresa una contraseña"); 
        txtContrasenia.focusedProperty().addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
            if(!newValue)
            {
                txtContrasenia.validate();
            }
        });
    }        
}
