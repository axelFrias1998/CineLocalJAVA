/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cine;

import com.jfoenix.controls.JFXDecorator;
import java.io.IOException;
import java.net.URL;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class OrdenesController implements Initializable {

    @FXML private TableColumn<Orden, String> columNombre;
    @FXML private TableColumn<Orden, Date> columFecha;
    @FXML private TableColumn<Orden, Float> columMonto;
    @FXML private TableView<Orden> tblOrdenes;
    @FXML private TableColumn<Orden, Integer> columId;
    @FXML private TableColumn<Orden, String> columFuncion;
    @FXML private TableColumn<Orden, String> columEstado;
    @FXML private TableColumn<Orden, String> columTipProy;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        iniciaTablas();
    }   
    
    ObservableList<Orden> listaOrdenes = FXCollections.observableArrayList();
    public void iniciaTablas(){
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3308/CineDB?useTimezone=true&serverTimezone=UTC","root","");
           //Objeto llamada procedimiento almacenado
            CallableStatement call = con.prepareCall("{call mostrarOrdenes_sp()}");
            //Envío parámetro a procedimiento almacenado
            ResultSet rs = call.executeQuery();
            while(rs.next()){
                listaOrdenes.add(new Orden(rs.getInt(1),rs.getFloat(2),(rs.getBoolean(3) == true) ? "Disponible" : "No disponible",rs.getDate(4),rs.getString(5),rs.getString(6),rs.getString(7)));
            }
        }catch(SQLException ex){ System.out.println(ex);} catch (ClassNotFoundException ex) {
            Logger.getLogger(RegistroAdministradorController.class.getName()).log(Level.SEVERE, null, ex);
        }
        columId.setCellValueFactory(new PropertyValueFactory<>("Id_orden"));
        columNombre.setCellValueFactory(new PropertyValueFactory<>("Id_Usuario"));
        columMonto.setCellValueFactory(new PropertyValueFactory<>("Monto"));
        columEstado.setCellValueFactory(new PropertyValueFactory<>("Estado"));
        columFecha.setCellValueFactory(new PropertyValueFactory<>("Fecha"));
        columFuncion.setCellValueFactory(new PropertyValueFactory<>("Pelicula"));
        columTipProy.setCellValueFactory(new PropertyValueFactory<>("TipoProyect"));
        tblOrdenes.setItems(listaOrdenes); 
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
