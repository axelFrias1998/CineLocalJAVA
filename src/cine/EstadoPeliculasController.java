package cine;

import com.jfoenix.controls.JFXDecorator;
import java.io.IOException;
import java.net.URL;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
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
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class EstadoPeliculasController implements Initializable {

    @FXML private TableColumn<Pelicula, String> colDirector, colPais, colEstado, colNombre, colDuracion, colClasificacion;
    @FXML private TableColumn<Pelicula, Integer> colAnio, colID;
    @FXML private TableView<Pelicula> tablaPeliculas;
    
    ObservableList<Pelicula> listaPelicula = FXCollections.observableArrayList();

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

    @FXML
    void eliminarPelicula(ActionEvent event) {
        if(tablaPeliculas.getSelectionModel().getSelectedItem() != null){
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            for(Pelicula p : tablaPeliculas.getSelectionModel().getSelectedItems()){
                alert.setTitle("Confirmación");
                alert.setContentText("¿Seguro que deseas eliminar la película " + p.getTitulo() + "? Los datos no serán recuperables.");
                Optional<ButtonType> result =alert.showAndWait();
                if(result.get() == ButtonType.OK){
                    try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/CineDB?useTimezone=true&serverTimezone=UTC","root","Suripanta.98")) {
                        PreparedStatement stmt = con.prepareStatement(" Delete from pelicula where Id = " + p.getId()  +";");
                        stmt.executeUpdate();
                        con.close();
                    }catch(SQLException ex){ System.out.println(ex);}
                    listaPelicula.clear();
                    iniciaTabla();
                }
            }
        }else{
            Alert alertE = new Alert(Alert.AlertType.ERROR);
            alertE.setTitle("¡Error!");
            alertE.setContentText("Selecciona el registro a eliminar.");
            alertE.showAndWait();
        }
    }

    @FXML
    void cambiarDisponible(ActionEvent event) {
        if(tablaPeliculas.getSelectionModel().getSelectedItem() != null){
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            for(Pelicula p : tablaPeliculas.getSelectionModel().getSelectedItems()){
                if(p.getEstado().equals("Por estrenar")){
                    alert.setTitle("Confirmación");
                    alert.setContentText("¿Deseas cambiar el estado de la película a ESTRENO?");
                    Optional<ButtonType> result =alert.showAndWait();
                    if(result.get() == ButtonType.OK){
                        try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/CineDB?useTimezone=true&serverTimezone=UTC","root","Suripanta.98")) {
                            PreparedStatement stmt = con.prepareStatement("update pelicula set Estado_Id = 2 where Id = " + p.getId()  +";");
                            stmt.executeUpdate();
                            con.close();
                        }catch(SQLException ex){ System.out.println(ex);}
                        listaPelicula.clear();
                        iniciaTabla();
                    }
                }else if(p.getEstado().equals("No disponible")){
                    alert.setTitle("Confirmación");
                    alert.setContentText("Tu película ya no se encuentra disponible, ¿deseas cambiar su estado a DISPONIBLE?");
                    Optional<ButtonType> result =alert.showAndWait();
                    if(result.get() == ButtonType.OK){
                        try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/CineDB?useTimezone=true&serverTimezone=UTC","root","Suripanta.98")) {
                            PreparedStatement stmt = con.prepareStatement("update pelicula set Estado_Id = 2 where Id = " + p.getId()  +";");
                            stmt.executeUpdate();
                            con.close();
                        }catch(SQLException ex){ System.out.println(ex);}
                        listaPelicula.clear();
                        iniciaTabla();
                    }
                }
                else{
                    Alert alertE = new Alert(Alert.AlertType.ERROR);
                    alertE.setTitle("!Error!");
                    alertE.setContentText("Tu película ya es un ESTRENO.");
                    alertE.showAndWait();
                }
            }
        }else{
            Alert alertE = new Alert(Alert.AlertType.ERROR);
            alertE.setTitle("¡Error!");
            alertE.setContentText("Selecciona el registro a modificar.");
            alertE.showAndWait();
        }
    }

    @FXML
    void cambiarNoDisponible(ActionEvent event) {
        if(tablaPeliculas.getSelectionModel().getSelectedItem() != null){
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            for(Pelicula p : tablaPeliculas.getSelectionModel().getSelectedItems()){
                if(p.getEstado().equals("Estreno")){
                    alert.setTitle("Confirmación");
                    alert.setContentText("¿Deseas cambiar el estado de la película a NO DISPONIBLE?");
                    Optional<ButtonType> result =alert.showAndWait();
                    if(result.get() == ButtonType.OK){
                        try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/CineDB?useTimezone=true&serverTimezone=UTC","root","Suripanta.98")) {
                            PreparedStatement stmt = con.prepareStatement("update pelicula set Estado_Id = 3 where Id = " + p.getId()  +";");
                            stmt.executeUpdate();
                            con.close();
                        }catch(SQLException ex){ System.out.println(ex);}
                        listaPelicula.clear();
                        iniciaTabla();
                    }
                }else if(p.getEstado().equals("Por estrenar")){
                    alert.setTitle("Confirmación");
                    alert.setContentText("Tu película aún no se ha estrenado, ¿deseas cambiar su estado a NO DISPONIBLE?");
                    Optional<ButtonType> result =alert.showAndWait();
                    if(result.get() == ButtonType.OK){
                        try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/CineDB?useTimezone=true&serverTimezone=UTC","root","Suripanta.98")) {
                            PreparedStatement stmt = con.prepareStatement("update pelicula set Estado_Id = 3 where Id = " + p.getId()  +";");
                            stmt.executeUpdate();
                            con.close();
                        }catch(SQLException ex){ System.out.println(ex);}
                        listaPelicula.clear();
                        iniciaTabla();
                    }
                }
                else{
                    Alert alertE = new Alert(Alert.AlertType.ERROR);
                    alertE.setTitle("!Error!");
                    alertE.setContentText("Tu película ya no se encuentra disponible.");
                    alertE.showAndWait();
                }
            }
        }else{
            Alert alertE = new Alert(Alert.AlertType.ERROR);
            alertE.setTitle("¡Error!");
            alertE.setContentText("Selecciona el registro a modificar.");
            alertE.showAndWait();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        iniciaTabla();
    }  
    
    void iniciaTabla(){
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/CineDB?useTimezone=true&serverTimezone=UTC","root","Suripanta.98");
            //Objeto llamada procedimiento almacenado
            CallableStatement call = con.prepareCall("{call estadoPeliculas_sp()}");
            //Envío parámetro a procedimiento almacenado
            ResultSet rs = call.executeQuery();
            while(rs.next()){
                listaPelicula.add(new Pelicula(
                        rs.getInt(1),
                        rs.getInt(4),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(5),
                        rs.getString(6), 
                        rs.getString(7),
                        rs.getString(8)
                ));
            }
        }catch(SQLException ex){ System.out.println(ex);} catch (ClassNotFoundException ex) {
            Logger.getLogger(RegistroAdministradorController.class.getName()).log(Level.SEVERE, null, ex);
        }
        colID.setCellValueFactory(new PropertyValueFactory<>("Id"));
        colAnio.setCellValueFactory(new PropertyValueFactory<>("Anio"));
        colDirector.setCellValueFactory(new PropertyValueFactory<>("Director"));
        colNombre.setCellValueFactory(new PropertyValueFactory<>("Titulo"));
        colPais.setCellValueFactory(new PropertyValueFactory<>("Pais"));
        colEstado.setCellValueFactory(new PropertyValueFactory<>("Estado"));
        colClasificacion.setCellValueFactory(new PropertyValueFactory<>("Clasificacion"));
        colDuracion.setCellValueFactory(new PropertyValueFactory<>("Duracion"));
        colDirector.setCellValueFactory(new PropertyValueFactory<>("Director"));                                        
        tablaPeliculas.setItems(listaPelicula); 
    }

    
}
