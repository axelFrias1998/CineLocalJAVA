/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cine;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDecorator;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.validation.NumberValidator;
import com.jfoenix.validation.RequiredFieldValidator;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ResourceBundle;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;

import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;


public class CrearPeliculaController implements Initializable {

    @FXML
    private Label lblPais, lblAnio, lblTitulo, lblValidacion, lblDuracion, lblClasificacion, lblSinopsis, lblDirector, lblImagen;

    @FXML
    private JFXButton btnAgregarPeli, btnSeleccionar, btnCancelar;

    @FXML
    private JFXTextField txtDirector, txtPais, txtDuracion, txtAnio, txtTitulo;

    @FXML
    private ImageView Imagen;

    @FXML
    private JFXComboBox<String> cmbClasificacion;


    @FXML
    private JFXTextArea txtDescripcion;
    
    //URL is = getClass().getResource("/pelicula.png");
    File fileess;
    String fichero;
    
    @FXML
    private void Seleccionar_Imagen(ActionEvent event) {
        FileChooser imagen = new FileChooser();
        
        imagen.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("All Images", "*.*"),
                new FileChooser.ExtensionFilter("JPG", "*.jpg"),
                new FileChooser.ExtensionFilter("PNG", "*.png"));
        
        File img = imagen.showOpenDialog(null);
        if (img != null)
        {
            fileess = img;
            System.out.println(String.valueOf(img));
            fichero = String.valueOf(img);
            Image preview = new Image("file:"+img.getAbsolutePath(),500,700,false,false);
            Imagen.setImage(preview);
                
        }
    }
   
    @FXML
    @SuppressWarnings("empty-statement")
    private void Action_AgregarPelicula(ActionEvent event)throws IOException, ClassNotFoundException, SQLException, ParseException {
        FileInputStream files; 
        if(txtTitulo.validate() && txtDirector.validate() && txtAnio.validate() && txtDuracion.validate() && txtPais.validate() && txtDescripcion.validate() && cmbClasificacion.validate() && Imagen.getImage() != null){
            if(Validaciones.validaCadena(txtTitulo.getText()) && Validaciones.validaNombre(txtDirector.getText()) && Validaciones.validaAnio(txtAnio.getText()) && Validaciones.validaDuracion(txtDuracion.getText())
                    && Validaciones.validaNombre(txtPais.getText())){
                try{
                    File file = new File(fichero);
                    files = new FileInputStream(file);
                    Class.forName("com.mysql.cj.jdbc.Driver");
                    Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/CineDB?useTimezone=true&serverTimezone=UTC","root","Suripanta.98");

                    //Objeto llamada procedimiento almacenado
                    CallableStatement call = con.prepareCall("{call crearPelicula_sp(?, ?, ?, ?, ?, ?, ?, ?, ?)}");
                    //Envío parámetro a procedimiento almacenado
                    call.setString(1, txtTitulo.getText());
                    call.setString(2, txtDirector.getText());
                    call.setString(3, txtDescripcion.getText());
                    //1 significa Estreno para hacer join en BD
                    call.setInt(4, 1);
                    call.setBlob(5, files);
                    call.setInt(6, Integer.parseInt(txtAnio.getText()));
                    call.setString(7, txtAnio.getText());
                    call.setString(8, cmbClasificacion.getValue());
                    call.setInt(9, Integer.parseInt(txtDuracion.getText()));
                    call.executeUpdate();
                    con.close();
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("¡Datos agregadps!");
                    alert.setContentText("Los datos de la película se han agregado correctamente.");
                    alert.showAndWait();
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
                    
                }catch(SQLException ex){ System.out.println(ex);};
            }
            else{
                if(Validaciones.validaCadena(txtTitulo.getText()) && txtTitulo.getText().length() < 60)
                    lblTitulo.setVisible(false);                
                else{
                    lblTitulo.setStyle("visibility : true");
                    if(!Validaciones.validaCadena(txtTitulo.getText()))
                        lblTitulo.setText("El nombre de la película no es válido");
                    else if(txtTitulo.getText().length() >= 60)
                        lblTitulo.setText("El título no debe exceder 60 caracteres");
                }
                if(Validaciones.validaNombre(txtDirector.getText()))
                    lblDirector.setStyle("visibility : false");                
                else{
                    lblDirector.setStyle("visibility : true");
                    lblDirector.setText("El nombre del director no es válido");
                }
                if(Validaciones.validaAnio(txtAnio.getText()))
                    lblAnio.setStyle("visibility : false");                
                else{
                    lblAnio.setStyle("visibility : true");
                    lblAnio.setText("El año no es válido");
                }  
                if(Validaciones.validaDuracion(txtDuracion.getText()))
                    lblDuracion.setStyle("visibility : false");                
                else{
                    lblDuracion.setStyle("visibility : true");
                    lblDuracion.setText("La duración de la película no es válida");
                }  
                if(Validaciones.validaCadena(txtPais.getText()))
                    lblPais.setStyle("visibility : false");                
                else{
                    lblPais.setStyle("visibility : true");
                    lblPais.setText("El nombre del país no es válido");
                }
                if(txtDescripcion.getText().length() < 800)
                    lblSinopsis.setStyle("visibility : false");                
                else{
                    lblSinopsis.setStyle("visibility : true");
                    lblSinopsis.setText("La descrpción no es válida. Ingresa menos de 600 caracteres.");
                }  
            }
        }else{
            if(Imagen.getImage() == null){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("¡Agrega una imagen!");
                alert.setContentText("Ingresa el póster de tu película.");
                alert.showAndWait();
            }
            else{
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("¡Agrega los datos de la película!");
                alert.setContentText("Ingresa los datos necesarios.");
                alert.showAndWait();
            }
        }
    }
    
    @FXML
    void Cancelar(ActionEvent event) throws IOException {
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
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        RequiredFieldValidator  validarTitulo = new RequiredFieldValidator();
        txtTitulo.getValidators().add(validarTitulo);
        validarTitulo.setMessage("Ingresa un título");       
        txtTitulo.focusedProperty().addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
            if(!newValue)
                txtTitulo.validate();
        });
        RequiredFieldValidator  validarClasificacion = new RequiredFieldValidator();
        cmbClasificacion.getValidators().add(validarClasificacion);
        validarClasificacion.setMessage("Ingresa una clasificaciòn");       
        cmbClasificacion.focusedProperty().addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
            if(!newValue)
                cmbClasificacion.validate();
        });
        RequiredFieldValidator  validarDirector = new RequiredFieldValidator();
        txtDirector.getValidators().add(validarDirector);
        validarDirector.setMessage("Ingresa el nombre del director");       
        txtDirector.focusedProperty().addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
            if(!newValue)
                txtDirector.validate();
        });
        RequiredFieldValidator  validarDescripcion = new RequiredFieldValidator();
        txtDescripcion.getValidators().add(validarDescripcion);
        validarDescripcion.setMessage("Ingresa una sinopsis");       
        txtDescripcion.focusedProperty().addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
            if(!newValue)
                txtDescripcion.validate();
        });
        RequiredFieldValidator  validarPais = new RequiredFieldValidator();
        txtPais.getValidators().add(validarPais);
        validarPais.setMessage("Ingresa el país de procedencia");       
        txtPais.focusedProperty().addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
            if(!newValue)
                txtPais.validate();
        });
        RequiredFieldValidator  validarAnioT = new RequiredFieldValidator();
        txtAnio.getValidators().add(validarAnioT);
        validarAnioT.setMessage("Ingresa el año");       
        txtAnio.focusedProperty().addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
            if(!newValue)
                txtAnio.validate();
        });
        RequiredFieldValidator  validarDuracionT = new RequiredFieldValidator();
        txtDuracion.getValidators().add(validarDuracionT);
        validarDuracionT.setMessage("Ingresa la duración");       
        txtDuracion.focusedProperty().addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
            if(!newValue)
                txtDuracion.validate();
        });
        NumberValidator  validarAnio = new NumberValidator();
        txtAnio.getValidators().add(validarAnio);
        validarAnio.setMessage("Ingresa un año");       
        txtAnio.focusedProperty().addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
            if(!newValue)
                txtAnio.validate();
        });
        NumberValidator  validarDuracion = new NumberValidator();
        txtDuracion.getValidators().add(validarDuracion);
        validarDuracion.setMessage("Ingresar titulo");       
        txtDuracion.focusedProperty().addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
            if(!newValue)
                txtDuracion.validate();
        });
        cmbClasificacion.getItems().addAll("A", "AA", "B" , "B15", "C", "D");
    }
    
    
}
