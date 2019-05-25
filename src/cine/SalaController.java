package cine;


import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDecorator;
import com.jfoenix.validation.RequiredFieldValidator;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
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
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.converter.LocalDateStringConverter;
import sun.plugin2.jvm.RemoteJVMLauncher.CallBack;


public class SalaController implements Initializable {
    ObservableList<Salas> listaSala = FXCollections.observableArrayList();
    //Anexar Sala
    @FXML private JFXComboBox<Integer> cmbFilas, cmbColumnas;    
    @FXML private Pane paneRes;
    @FXML private Label lblTotalAsientos;
    //Control de salas
    @FXML private TableView<Salas> tableSalas;
    @FXML private TableColumn<Salas, Integer> colCapacidad;    
    @FXML private TableColumn<Salas, Integer> colSala;
    @FXML private TableColumn<Salas, String> colDisponible;
    
    //Asignar funciones
    @FXML private DatePicker dtmFechaFuncion;
    @FXML private TableColumn<Funcion, Integer> colIdFuncion;
    @FXML private TableColumn<Funcion, String> colFin, colInicio, colSalaFuncion;
    @FXML private Label lblDuracionAprox, lblProyectandose;
    @FXML  private TableView<Funcion> tablaFuncionesSala;
    @FXML private ComboBox<String> cmbTipoProyeccion, cmbPelicula, cmbHoraInicio;
    @FXML private ComboBox<Integer> cmbSala;
    
    @FXML
    void agregarPelicula(ActionEvent event) {
        
    }
    
    @FXML
    void cambiarDisponibilidad(ActionEvent event) throws IOException, ClassNotFoundException, SQLException {
        if(tableSalas.getSelectionModel().getSelectedItem() != null){
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            for(Salas s : tableSalas.getSelectionModel().getSelectedItems()){
                if(s.isDisponible().equals("Disponible")){
                    alert.setTitle("Confirmación");
                    alert.setContentText("¿Deseas cambiar el estado de la sala a NO DISPONIBLE?");
                    Optional<ButtonType> result =alert.showAndWait();
                    if(result.get() == ButtonType.OK){
                        try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/CineDB?useTimezone=true&serverTimezone=UTC","root","Suripanta.98")) {
                            PreparedStatement stmt = con.prepareStatement("update sala set Disponible = false where Id = " + s.getId()  +";");
                            stmt.executeUpdate();
                            con.close();
                        }catch(SQLException ex){ System.out.println(ex);}
                        listaSala.clear();
                        iniciaTablas();
                        iniciaCMB();
                    }
                }else{
                    alert.setTitle("Confirmación");
                    alert.setContentText("¿Deseas cambiar el estado de la sala a DISPONIBLE?");
                    Optional<ButtonType> result =alert.showAndWait();
                    if(result.get() == ButtonType.OK){
                        try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/CineDB?useTimezone=true&serverTimezone=UTC","root","Suripanta.98")) {
                            Statement stmt = con.createStatement();
                            ResultSet rsLleno = stmt.executeQuery("Select * from asiento where Sala_Id = " + s.getId() +" limit 1;");
                            if(rsLleno.next()){
                                PreparedStatement stmtUpdateExiste = con.prepareStatement("update sala set Disponible = true where Id = " + s.getId()  +";");
                                stmtUpdateExiste.executeUpdate();
                            }
                            else{
                                CallableStatement call = con.prepareCall("{call nuevosAsientos_sp(?,?)}");
                                ResultSet rsSala = stmt.executeQuery("Select Id, FilasAsientos, ColumnasAsientos from sala where Id = " + s.getId());
                                if(rsSala.next())
                                    SesionSala.setInstance(rsSala.getInt(3), rsSala.getInt(2), s.getId(), rsSala.getInt(3) * rsSala.getInt(2));
                                int fila = 64;
                                for(int i = 1; i <= SesionSala.getInstance().getFilas(); i++){
                                    for(int j = 1; j <= SesionSala.getInstance().getColumnas(); j++){
                                        call.setString(1, (char)(fila + i) + Integer.toString(j));
                                        call.setInt(2, SesionSala.getInstance().getIdSala());
                                        call.executeQuery();
                                    }
                                }
                            }
                            con.close();
                        }catch(SQLException ex){ System.out.println(ex);}
                        listaSala.clear();
                        iniciaTablas();
                        iniciaCMB();
                    }
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
    void mostrarFormato(ActionEvent event) {

    }
    
    void agregarAsientos(int col, int fil) throws ClassNotFoundException{
        int fila = 64;
        Class.forName("com.mysql.cj.jdbc.Driver");
        try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/CineDB?useTimezone=true&serverTimezone=UTC","root","Suripanta.98")) {
            //Objeto llamada procedimiento almacenado
            CallableStatement call = con.prepareCall("{call nuevosAsientos_sp(?,?)}");
            for(int i = 1; i <= SesionSala.getInstance().getFilas(); i++){
                for(int j = 1; j <= SesionSala.getInstance().getColumnas(); j++){
                    call.setString(1, (char)(fila + i) + Integer.toString(j));
                    call.setInt(2, SesionSala.getInstance().getIdSala());
                    call.executeQuery();
                }
            }
            con.close();
            SesionSala.liberaSala();
        }catch(SQLException ex){ System.out.println(ex);}
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("¡Èxito!");
        alert.setContentText("Tu sala se ha creado con sus asientos correspondientes");
        alert.showAndWait();
        iniciaTablas();
        
    }
    
    @FXML
    void crearSala(ActionEvent event) throws ClassNotFoundException, IOException {
        Stage actual = (Stage)((Node)event.getSource()).getScene().getWindow();
        actual.hide();

        Parent root = FXMLLoader.load(getClass().getResource("Sala.fxml"));
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
    @SuppressWarnings("empty-statement")
    void crearDisenio(ActionEvent event) throws FileNotFoundException, ClassNotFoundException, SQLException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmación");
        alert.setContentText("¿Los datos la sala son los correctos? Su sala NO ESTARÁ DISPONIBLE hasta confirmar el número de asientos.");
        Optional<ButtonType> result =alert.showAndWait();
        if(cmbColumnas.validate() && cmbFilas.validate()){
            if(result.get() == ButtonType.OK){
                Class.forName("com.mysql.cj.jdbc.Driver");
                //Objeto llamada procedimiento almacenado
                try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/CineDB?useTimezone=true&serverTimezone=UTC","root","Suripanta.98")) {
                    //Objeto llamada procedimiento almacenado
                    CallableStatement call = con.prepareCall("{call nuevaSala_sp(?, ?)}");
                    //Envío parámetro a procedimiento almacenado
                    call.setInt(1, cmbFilas.getValue());
                    call.setInt(2, cmbColumnas.getValue());
                    call.executeQuery();

                    Statement stmt = con.createStatement();

                    ResultSet rs = stmt.executeQuery("select MAX(Id) from sala;");
                    int ultimaSala = 0;
                    if(rs.next())
                        ultimaSala = rs.getInt(1);
                    SesionSala.setInstance(cmbColumnas.getValue(), cmbFilas.getValue(), ultimaSala ,cmbColumnas.getValue() * cmbFilas.getValue());
                    con.close();
                }catch(SQLException ex){ System.out.println(ex);};
                paneRes.setVisible(true);
                paneRes.setManaged(true);
                lblTotalAsientos.setText("Tu sala tendrá " + cmbColumnas.getValue() * cmbFilas.getValue() +" asientos");
            }
        }else{
            Alert alertE = new Alert(Alert.AlertType.ERROR);
            alertE.setTitle("¡Datos necesarios!");
            alertE.setContentText("Ingresa el número de filas y columnas");
            alertE.showAndWait();
        }
        listaSala.clear();
        iniciaTablas();
        iniciaCMB();
    }
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            iniciaRequiredField();
            iniciaCMB();
            iniciaTablas();
            configDTM();
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(SalaController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    public void configDTM(){
        dtmFechaFuncion.setEditable(false);
        dtmFechaFuncion.setConverter(new LocalDateStringConverter(DateTimeFormatter.ofPattern("dd/MM/yyyy"), null));
    }
 
    public void iniciaRequiredField(){
        
        RequiredFieldValidator  validarFilas = new RequiredFieldValidator();
        cmbFilas.getValidators().add(validarFilas);
        validarFilas.setMessage("Selecciona un número de filas");       
        cmbFilas.focusedProperty().addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
            if(!newValue)
                cmbFilas.validate();
        });
        
        RequiredFieldValidator  validarColumna = new RequiredFieldValidator();
        cmbColumnas.getValidators().add(validarColumna);
        validarColumna.setMessage("Selecciona un número de columnas");       
        cmbColumnas.focusedProperty().addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
            if(!newValue)
                cmbColumnas.validate();
        });
    }
    
    public void iniciaCMB() throws ClassNotFoundException, SQLException{
        cmbFilas.getItems().clear();
        cmbColumnas.getItems().clear();
        cmbPelicula.getItems().clear();
        cmbTipoProyeccion.getItems().clear();
        cmbSala.getItems().clear();
        cmbHoraInicio.getItems();
        
        cmbFilas.getItems().addAll(6, 7, 8, 9, 10, 11, 12);
        cmbColumnas.getItems().addAll(12, 13, 14, 15, 16);
        try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/CineDB?useTimezone=true&serverTimezone=UTC","root","Suripanta.98")) {
            Statement stmt = con.createStatement();
            ResultSet rsPelicula = stmt.executeQuery("Select Titulo from pelicula;");
            while(rsPelicula.next())
                cmbPelicula.getItems().add(rsPelicula.getString(1));
            ResultSet rsTProyeccion = stmt.executeQuery("Select Nombre from tipoproyeccion;");
            while(rsTProyeccion.next())
                cmbTipoProyeccion.getItems().add(rsTProyeccion.getString(1));
            ResultSet rsSala = stmt.executeQuery("Select Id from sala where Disponible = true;");
            while(rsSala.next())
                cmbSala.getItems().add(rsSala.getInt(1));
            con.close();
        }catch(SQLException ex){ System.out.println(ex);}
        for(int i = 9; i <= 22; i++)
            cmbHoraInicio.getItems().add(i +":00");
        
        cmbSala.valueProperty().addListener(new ChangeListener<Integer>() {
        @Override public void changed(ObservableValue ov, Integer t, Integer t1) {
            lblProyectandose.setVisible(true);
            lblProyectandose.setManaged(true);
            tablaFuncionesSala.setVisible(true);
            tablaFuncionesSala.setManaged(true);
//            try{
//                Class.forName("com.mysql.cj.jdbc.Driver");
//                Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/CineDB?useTimezone=true&serverTimezone=UTC","root","Suripanta.98");
//                //Objeto llamada procedimiento almacenado
//                CallableStatement call = con.prepareCall("{call tablaSalas_sp()}");
//                //Envío parámetro a procedimiento almacenado
//                List<Salas> lista = new ArrayList();
//                ResultSet rs = call.executeQuery();
//
//                while(rs.next()){
//                    listaSala.add(new Salas(rs.getInt(1),rs.getInt(2) * rs.getInt(3), (rs.getBoolean(4) == true) ? "Disponible" : "No disponible"));
//                }
//            }catch(SQLException ex){ System.out.println(ex);} catch (ClassNotFoundException ex) {
//                Logger.getLogger(RegistroAdministradorController.class.getName()).log(Level.SEVERE, null, ex);
//            }
//            colSala.setCellValueFactory(new PropertyValueFactory<>("id"));
//            colCapacidad.setCellValueFactory(new PropertyValueFactory<>("asientos"));
//            colDisponible.setCellValueFactory(new PropertyValueFactory<>("disponible"));
//            tableSalas.setItems(listaSala);   
            System.out.println(t1);
        }    
    });
    }
    
    
    public void iniciaTablas(){
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/CineDB?useTimezone=true&serverTimezone=UTC","root","Suripanta.98");
            //Objeto llamada procedimiento almacenado
            CallableStatement call = con.prepareCall("{call tablaSalas_sp()}");
            //Envío parámetro a procedimiento almacenado
            List<Salas> lista = new ArrayList();
            ResultSet rs = call.executeQuery();

            while(rs.next()){
                listaSala.add(new Salas(rs.getInt(1),rs.getInt(2) * rs.getInt(3), (rs.getBoolean(4) == true) ? "Disponible" : "No disponible"));
            }
        }catch(SQLException ex){ System.out.println(ex);} catch (ClassNotFoundException ex) {
            Logger.getLogger(RegistroAdministradorController.class.getName()).log(Level.SEVERE, null, ex);
        }
        colSala.setCellValueFactory(new PropertyValueFactory<>("id"));
        colCapacidad.setCellValueFactory(new PropertyValueFactory<>("asientos"));
        colDisponible.setCellValueFactory(new PropertyValueFactory<>("disponible"));
        tableSalas.setItems(listaSala);      
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
