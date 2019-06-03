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
import java.io.IOException;
import java.net.URL;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;

import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author axelf
 */
public class ControlUsuarioController implements Initializable {
    
    @FXML private TableView<Usuarios> tblUsuarios;
    @FXML private TableColumn<Usuarios, String> clmnnombre, clmncorreo, clmncontrasenia;
    @FXML private TableColumn<Usuarios, Number> clmnSaldo, clmnid;
    @FXML private JFXTextField txtEmail, txtNombre, txtSaldo;
    @FXML private JFXPasswordField txtContraseña;
    @FXML private JFXButton btnEditar, btnEliminar, btnActualizar;
    @FXML private Label lblNombre, lblEmail, lblPass, lblSaldo;

    private int id;
    private int tipo_id;
     
    ObservableList<Usuarios> listaUsuario = FXCollections.observableArrayList();
    
    @FXML
    void Action_Editar(ActionEvent event) {
        editartrue();
        btnActualizar.setDisable(false);
    }

    @FXML
    void Action_ActualizarUsuario(ActionEvent event) throws ClassNotFoundException {
        if(txtNombre.validate() && txtEmail.validate() && txtContraseña.validate() && txtSaldo.validate())
        {
            if(Validaciones.validaEmail(txtEmail.getText()) && Validaciones.validaNombre(txtNombre.getText()) && txtContraseña.getText().length() >= 6){
                
                try{
            
                    String nombre = txtNombre.getText(), email = txtEmail.getText(),contra = txtContraseña.getText();
                    int Id = id, Tipo_id=tipo_id;
                    float saldo = Float.parseFloat(txtSaldo.getText());
                    Class.forName("com.mysql.cj.jdbc.Driver");
                    Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/CineDB?useTimezone=true&serverTimezone=UTC","root","Suripanta.98");

                    //Objeto llamada procedimiento almacenado
                    CallableStatement call = con.prepareCall("{call ActualizarUsuario_sp(?, ?, ?, ?, ?, ?)}");
                    call.setInt(1, Id);
                    call.setString(2, nombre);
                    call.setString(3, email);
                    call.setString(4, contra);
                    call.setFloat(5, saldo);
                    call.setInt(6, Tipo_id);
                    
                    Usuarios us = new Usuarios(Id,nombre,email,contra,saldo,Tipo_id);
                    
                    
                    call.executeUpdate();
                    con.close();
                    
                    listaUsuario.set(tblUsuarios.getSelectionModel().getSelectedIndex(),us);
			
                    Alert mensaje = new Alert(Alert.AlertType.INFORMATION);
                    mensaje.setTitle("Registro actualizado");
                    mensaje.setContentText("El registro ha sido actualizado exitosamente");
                    mensaje.setHeaderText("Resultado:");
                    mensaje.show();
                        
                    editar();
                    limpiar();
                    btnActualizar.setDisable(true);
                        
                }catch(SQLException ex){ System.out.println(ex);};
                
            }else{
                if(Validaciones.validaEmail(txtEmail.getText())){
                    lblEmail.setStyle("visibility : false");
                }
                else{
                    
                    lblEmail.setStyle("visibility : true");
                    lblEmail.setText("Ingresa un correo válido");
                }
                if(Validaciones.validaNombre(txtNombre.getText()))
                    lblNombre.setStyle("visibility : false");
                else{
                    lblNombre.setStyle("visibility : true");
                    lblNombre.setText("Ingresa un nombre válido");
                }
                if(txtContraseña.getText().length() >= 6)
                    lblPass.setStyle("visibility : false");
                else{
                    lblPass.setStyle("visibility : true");
                    lblPass.setText("La contraseña debe tener 6 caracteres o más");
                }
                
            }
        }else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Sin datos");
            alert.setContentText("Ingresa los datos");
            alert.showAndWait();
        }
    }

    @FXML
    void eliminarRegistro(ActionEvent event) throws ClassNotFoundException {
        if(tblUsuarios.getSelectionModel().getSelectedItem().getId() == SesionUsuario.getInstance().getId()){
            Alert mensaje = new Alert(Alert.AlertType.INFORMATION);
            mensaje.setTitle("ERROR");
            mensaje.setContentText("No puedes eliminar tu cuenta.");
            mensaje.setHeaderText("Resultado:");
            mensaje.show();
        }else{
            try{
                    Class.forName("com.mysql.cj.jdbc.Driver");
                    Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/CineDB?useTimezone=true&serverTimezone=UTC","root","Suripanta.98");
                    //Objeto llamada procedimiento almacenado
                    CallableStatement call = con.prepareCall("{call eliminarUsuario_sp(?)}");
                    //call.setInt(1, Integer.parseInt(lblId.getText()));
                    call.setInt(1, id);
                    call.executeUpdate();
                    con.close();
                    listaUsuario.remove(tblUsuarios.getSelectionModel().getSelectedIndex());
                    Alert mensaje = new Alert(Alert.AlertType.INFORMATION);
                    mensaje.setTitle("Registro eliminado");
                    mensaje.setContentText("El registro ha sido eliminado exitosamente");
                    mensaje.setHeaderText("Resultado:");
                    mensaje.show();
                    editar();
                    limpiar();
            }catch(SQLException ex){ System.out.println(ex);}
        }
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        btnActualizar.setDisable(true);
        id=0;
        tipo_id=0;
        iniciaTablas();
        GestionarDatos();
        editar();
        //Nombre
        RequiredFieldValidator  validarNombre = new RequiredFieldValidator();
        txtNombre.getValidators().add(validarNombre);
        validarNombre.setMessage("Ingresa el nombre");       
        txtNombre.focusedProperty().addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
            if(!newValue)
                txtNombre.validate();
        });
        
        //Email
        RequiredFieldValidator  validarEmail = new RequiredFieldValidator();
        txtEmail.getValidators().add(validarEmail);
        validarEmail.setMessage("Ingresa un Email");       
        txtEmail.focusedProperty().addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
            if(!newValue)
                txtEmail.validate();
        });
        
        //pass
        RequiredFieldValidator  validarPass = new RequiredFieldValidator();
        txtContraseña.getValidators().add(validarPass);
        validarPass.setMessage("Ingresa su contraseña");       
        txtContraseña.focusedProperty().addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
            if(!newValue)
                txtContraseña.validate();
        });
    }    

    private void editartrue() {
        txtNombre.setDisable(false);
        txtContraseña.setDisable(false);
        txtSaldo.setDisable(false);
        txtEmail.setDisable(false);    
    }

    private void editar() {
        txtNombre.setDisable(true);
        txtContraseña.setDisable(true);
        txtSaldo.setDisable(true);
        txtEmail.setDisable(true);
    }

    private void limpiar() {
        txtNombre.setText("");
        txtContraseña.setText("");
        txtSaldo.setText("");
        txtEmail.setText("");
        id=0;
            tipo_id=0;    
    }

    private void iniciaTablas() {
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/CineDB?useTimezone=true&serverTimezone=UTC","root","Suripanta.98");
           
            Statement stm=con.createStatement();
            ResultSet rs = stm.executeQuery("select * from usuario");

            while(rs.next()){
                listaUsuario.add(new Usuarios(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4),rs.getFloat(5), rs.getInt(6)));
            }
        }catch(SQLException ex){ System.out.println(ex);} catch (ClassNotFoundException ex) {
            Logger.getLogger(RegistroAdministradorController.class.getName()).log(Level.SEVERE, null, ex);
        }
        clmnid.setCellValueFactory(new PropertyValueFactory<>("id"));
        clmnnombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        clmncorreo.setCellValueFactory(new PropertyValueFactory<>("correo"));
        clmnSaldo.setCellValueFactory(new PropertyValueFactory<>("saldo"));
        tblUsuarios.setItems(listaUsuario);      
    }

    private void GestionarDatos() {
        tblUsuarios.getSelectionModel().selectedItemProperty().addListener(
            new ChangeListener<Usuarios>(){
                @Override
                public void changed(ObservableValue<? extends Usuarios> observable, Usuarios valorAnt, Usuarios valorSelect) {
                    if(valorSelect!=null)
                    {
                        id = valorSelect.getId();
                        txtNombre.setText(valorSelect.getNombre());
                        txtEmail.setText(valorSelect.getCorreo());
                        txtContraseña.setText(valorSelect.getContraseña());
                        txtSaldo.setText(Float.toString(valorSelect.getSaldo()));
                        tipo_id =valorSelect.getTipoUsuario();
                    }                   
                }                    
            }
        );
    }
    
    @FXML
    void regresar(ActionEvent event) throws IOException {
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
    }
}
