package cine;

import java.net.URL;
import javafx.fxml.Initializable;
import com.jfoenix.controls.JFXDecorator;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.validation.RequiredFieldValidator;
import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class RegistroAdministradorController implements Initializable {
    
    @FXML
    private AnchorPane contenido;
    @FXML
    private JFXPasswordField txtPass, txtConfirma;

    @FXML
    private JFXTextField txtNombre, txtEmail;

    @FXML
    private Label lblNombre, lblPass, lblConfirma, lblEmail;

    @FXML
    private void Action_AgregarRegistro(ActionEvent event)throws IOException, ClassNotFoundException, SQLException {
        
        if(txtNombre.validate() && txtEmail.validate() && txtPass.validate()){
            if(Validaciones.validaEmail(txtEmail.getText()) && Validaciones.validaNombre(txtNombre.getText()) && txtPass.getText().length() >= 6  && txtConfirma.getText().equals(txtPass.getText())){
                System.out.println("AQUI TAMBIEN");
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("Confirmación");
                    alert.setContentText("¿Los datos del administrador son los correctos?");
                    Optional<ButtonType> result =alert.showAndWait();
                    if(result.get() == ButtonType.OK){
                        boolean existe = false;
                        
                        Class.forName("com.mysql.cj.jdbc.Driver");
                        //Objeto llamada procedimiento almacenado
                        try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/CineDB?useTimezone=true&serverTimezone=UTC","root","Suripanta.98")) {
                            //Objeto llamada procedimiento almacenado
                            CallableStatement call = con.prepareCall("{call registroUsuario_sp(?, ?, ?, ?, ?, ?)}");
                            //Envío parámetro a procedimiento almacenado
                            call.setString(1, txtNombre.getText());
                            call.setString(2, txtEmail.getText());
                            call.setString(3, txtPass.getText());
                            call.setFloat(4, 0);
                            call.setInt(5, 1);
                            call.registerOutParameter(6, Types.BOOLEAN);
                            call.executeQuery();
                            existe = call.getBoolean(6);
                        }catch(SQLException ex){ System.out.println(ex);};
                        
                        if(!existe){
                            Alert correcto = new Alert(Alert.AlertType.INFORMATION);
                            correcto.setTitle("¡Éxito!");
                            correcto.setContentText("Los datos del administrador se han agregado correctamente");
                            correcto.showAndWait();
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
                        }else{
                            Alert correcto = new Alert(Alert.AlertType.ERROR);
                            correcto.setTitle("¡Error!");
                            correcto.setContentText("El correo de administrador se ha registrado anteriormente");
                            correcto.showAndWait();
                            for(Node node: contenido.getChildren()){
                                if(node instanceof JFXTextField)
                                    ((JFXTextField)node).setText("");
                                else if(node instanceof JFXPasswordField)
                                    ((JFXPasswordField)node).setText("");
                            }
                        }
                    }
            }
            else{
                if(Validaciones.validaEmail(txtEmail.getText()))
                    lblEmail.setStyle("visibility : false");
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
                if(txtPass.getText().length() >= 6)
                    lblPass.setStyle("visibility : false");
                else{
                    lblPass.setStyle("visibility : true");
                    lblPass.setText("La contraseña debe tener 6 caracteres o más");
                }
                if(txtConfirma.getText().equals(txtPass.getText()))
                    lblConfirma.setStyle("visibility: false");
                else{
                    lblPass.setStyle("visibility : true");
                    lblPass.setText("Las contraseñas deben coincidir");
                }
            }
        }else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Sin datos");
            alert.setContentText("Ingresa los datos del administrador");
            alert.showAndWait();
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
        RequiredFieldValidator  validarNombre = new RequiredFieldValidator();
        txtNombre.getValidators().add(validarNombre);
        validarNombre.setMessage("Ingresa el nombre del nuevo administrador");       
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
        txtPass.getValidators().add(validarPass);
        validarPass.setMessage("Ingresa su contraseña");       
        txtPass.focusedProperty().addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
            if(!newValue)
                txtPass.validate();
        });
        RequiredFieldValidator  validarConfirma = new RequiredFieldValidator();
        txtConfirma.getValidators().add(validarConfirma);
        validarConfirma.setMessage("Ingresa su contraseña");       
        txtConfirma.focusedProperty().addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
            if(!newValue)
                txtConfirma.validate();
        });
    }     
}
