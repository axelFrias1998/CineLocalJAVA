/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cine;

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
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;


public class CrearPeliculaController implements Initializable {

    @FXML
    private Button btnAgregarPeli;
    @FXML
    private Button btnSeleccionar;
    @FXML
    private JFXTextField txtTitulo;
    @FXML
    private JFXTextField txtDirector;
    @FXML
    private JFXTextField txtEstado;
    @FXML
    private JFXTextArea txtDescripcion;
    @FXML
    private TextField txtFile;
    @FXML
    private Label lblValidacion;
    @FXML
    private ImageView Imagen;
    
    
    //URL is = getClass().getResource("/pelicula.png");
    File fileess = new File("src/cine/pelicula.png");   
    String fichero = fileess.getAbsolutePath();
    
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
    private void Action_AgregarPelicula(ActionEvent event)throws IOException, ClassNotFoundException, SQLException {
        FileInputStream files = null; 
        if(!txtTitulo.getText().isEmpty() && !"".equals(txtDirector.getText()) && !"".equals(txtDescripcion.getText()) && txtEstado.getText().length()!=0 && !fichero.equals("") || !fileess.exists()){    
        try{
                int estado = Integer.parseInt(txtEstado.getText());
                File file = new File(fichero);
                files = new FileInputStream(file);
                Class.forName("com.mysql.cj.jdbc.Driver");
                Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/CineDB?useTimezone=true&serverTimezone=UTC","root","Suripanta.98");
                
                //Objeto llamada procedimiento almacenado
                CallableStatement call = con.prepareCall("{call crearPelicula_sp(?, ?, ?, ?, ?)}");
                //Envío parámetro a procedimiento almacenado
                call.setString(1, txtTitulo.getText());
                call.setString(2, txtDirector.getText());
                call.setString(3, txtDescripcion.getText());
                call.setInt(4, estado);
                call.setBlob(5, files);
//                call.setBinaryStream(5,files,(int)file.length());
                call.executeUpdate();
                
                txtTitulo.setText("");
                txtDirector.setText("");
                txtDescripcion.setText("");
                txtEstado.setText("");
                lblValidacion.setVisible(false);
                System.out.println("Datos gurdados correctamente");
                
                con.close();
                
                //Pelicula peliculas = new Pelicula();
                
                    
            }catch(SQLException ex){ System.out.println(ex);};
            }else if(txtTitulo.getText().isEmpty() ){
            lblValidacion.setText("¡Ingresa todos los datos!");lblValidacion.setVisible(true);  
            }else if(txtEstado.getText().isEmpty()){lblValidacion.setText("¡Ingresa todos los datos!");lblValidacion.setVisible(true);
            }else if(txtDirector.getText().isEmpty()){lblValidacion.setText("¡Ingresa todos los datos!");lblValidacion.setVisible(true);
            }else if(txtDescripcion.getText().isEmpty()){lblValidacion.setText("¡Ingresa todos los datos!");lblValidacion.setVisible(true);}
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        RequiredFieldValidator  validarTitulo = new RequiredFieldValidator();
        txtTitulo.getValidators().add(validarTitulo);
        validarTitulo.setMessage("Ingresar titulo");       
        txtTitulo.focusedProperty().addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
            if(!newValue)
            {
                txtTitulo.validate();
            }
        });
        
        RequiredFieldValidator  validarDirector = new RequiredFieldValidator();
        txtDirector.getValidators().add(validarDirector);
        validarDirector.setMessage("Ingresar titulo");       
        txtDirector.focusedProperty().addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
            if(!newValue)
            {
                txtDirector.validate();
            }
        });
        RequiredFieldValidator  validarDescripcion = new RequiredFieldValidator();
        txtDescripcion.getValidators().add(validarDescripcion);
        validarDescripcion.setMessage("Ingresar titulo");       
        txtDescripcion.focusedProperty().addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
            if(!newValue)
            {
                txtDescripcion.validate();
            }
        });
        NumberValidator  validarEstado = new NumberValidator();
        txtEstado.getValidators().add(validarEstado);
        validarEstado.setMessage("Ingresar titulo");       
        txtEstado.focusedProperty().addListener(new ChangeListener<Boolean>(){
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if(!newValue)
                {
                    txtEstado.validate();
                }
            }
        });
    }   
    
}
