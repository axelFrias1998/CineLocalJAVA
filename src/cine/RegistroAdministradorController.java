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
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.validation.NumberValidator;
import com.jfoenix.validation.RequiredFieldValidator;
import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class RegistroAdministradorController implements Initializable {

     @FXML
    private JFXTextField txtPass;

    @FXML
    private JFXTextField txtNombre;

    @FXML
    private Label lblValidacion;

    @FXML
    private JFXTextField txtEmail;

    @FXML
    private JFXTextField txtSaldo;

    @FXML
    private JFXTextField txtRol;

    @FXML
    private JFXButton btnAgregar;

    @FXML
    private void Action_AgregarRegistro(ActionEvent event)throws IOException, ClassNotFoundException, SQLException {
        
        if(!txtNombre.getText().isEmpty() && !"".equals(txtEmail.getText()) && !"".equals(txtPass.getText()) && txtSaldo.getText().length()!=0 && txtRol.getText().length()!=0){    
            if(Validaciones.validaEmail(txtEmail.getText()) && Validaciones.validaNombre(txtNombre.getText())){
                try{
                    float saldo = Float.parseFloat(txtSaldo.getText());
                    int rol = Integer.parseInt(txtRol.getText());
                
                    Class.forName("com.mysql.cj.jdbc.Driver");
                    Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3308/CineDB?useTimezone=true&serverTimezone=UTC","root","");
                
                    //Objeto llamada procedimiento almacenado
                    CallableStatement call = con.prepareCall("{call registroUsuario_sp(?, ?, ?, ?, ?)}");
                    //Envío parámetro a procedimiento almacenado
                    call.setString(1, txtNombre.getText());
                    call.setString(2, txtEmail.getText());
                    call.setString(3, txtPass.getText());
                    call.setFloat(4, saldo);
                    call.setInt(5, rol);
                    call.executeUpdate();
                
                    txtNombre.setText("");
                    txtEmail.setText("");
                    txtPass.setText("");
                    txtSaldo.setText("");
                    
                    lblValidacion.setVisible(false);
                    System.out.println("Datos gurdados correctamente");
                
                    con.close();
                    
                }catch(SQLException ex){ System.out.println(ex);};
            }
            else if (!Validaciones.validaEmail(txtEmail.getText())){
                    lblValidacion.setText("Correo invalido, introduzca un correo valido");
                    lblValidacion.setVisible(true);
                }
            else if (!Validaciones.validaNombre(txtNombre.getText())){
                    lblValidacion.setText("Nombre invalido, introduzca un nombre valido");
                    lblValidacion.setVisible(true);
                }
            
        }else {
            lblValidacion.setText("¡Ingresa todos los datos!");lblValidacion.setVisible(true);  }
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        RequiredFieldValidator  validarNombre = new RequiredFieldValidator();
        txtNombre.getValidators().add(validarNombre);
        validarNombre.setMessage("Ingresar Nombre");       
        txtNombre.focusedProperty().addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
            if(!newValue)
            {
                txtNombre.validate();
            }
        });
        
        //Email
        RequiredFieldValidator  validarEmail = new RequiredFieldValidator();
        txtEmail.getValidators().add(validarEmail);
        validarEmail.setMessage("Ingresar Email");       
        txtEmail.focusedProperty().addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
            if(!newValue)
            {
                txtEmail.validate();
            }
        });
        
        //pass
        RequiredFieldValidator  validarPass = new RequiredFieldValidator();
        txtPass.getValidators().add(validarPass);
        validarPass.setMessage("Ingresar Password");       
        txtPass.focusedProperty().addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
            if(!newValue)
            {
                txtPass.validate();
            }
        });
        
        //saldo
        RequiredFieldValidator  validarSaldo = new RequiredFieldValidator();
        txtSaldo.getValidators().add(validarSaldo);
        validarSaldo.setMessage("Ingresar Saldo");       
        txtSaldo.focusedProperty().addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
            if(!newValue)
            {
                txtSaldo.validate();
            }
        });
        
        NumberValidator  validarSaldo_float = new NumberValidator();
        txtSaldo.getValidators().add(validarSaldo_float);
        validarSaldo_float.setMessage("No se puede ingresar letras");       
        txtSaldo.focusedProperty().addListener(new ChangeListener<Boolean>(){
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if(!newValue)
                {
                    txtSaldo.validate();
                }
            }
        });
        
        //rol
        RequiredFieldValidator  validarRol = new RequiredFieldValidator();
        txtRol.getValidators().add(validarRol);
        validarRol.setMessage("Ingresar Rol");       
        txtRol.focusedProperty().addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
            if(!newValue)
            {
                txtRol.validate();
            }
        });
    }     
}
