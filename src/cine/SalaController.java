package cine;


import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTreeTableColumn;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import com.jfoenix.validation.RequiredFieldValidator;
import java.io.FileNotFoundException;
import java.net.URL;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.layout.Pane;

public class SalaController implements Initializable {

    @FXML
    private JFXTreeTableView<Salas> tvSalas;
    
    @FXML
    private JFXComboBox<Integer> cmbFilas, cmbColumnas;

    @FXML
    private Tab anexar, control, asignar;
    
    @FXML
    private Pane paneRes;

    @FXML
    private Label lblTotalAsientos;
//    public String RutaFormato = "C:/Users/axelf/Desktop/Proyecto Valdepeña/Cine/src/cine/Salas/Formatos/";

    @FXML
    void crearSala(ActionEvent event) throws ClassNotFoundException {
        int fila = 64;
        Class.forName("com.mysql.cj.jdbc.Driver");
        //Objeto llamada procedimiento almacenado
        
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
        }catch(SQLException ex){ System.out.println(ex);};
    }

    @FXML
    void cancelar(ActionEvent event) {
        int l;
    }

    @FXML
    @SuppressWarnings("empty-statement")
    void crearDisenio(ActionEvent event) throws FileNotFoundException, ClassNotFoundException {
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
                    System.out.println(ultimaSala);
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
        
        
//        PdfWriter writer = new PdfWriter(RutaFormato + "prueba.pdf");
//        PdfDocument pdf = new PdfDocument(writer);
//        Document doc = new Document(pdf);
//        doc.add(new Paragraph("Prueba"));
//        doc.close();
    }
    @Override
    @SuppressWarnings("empty-statement")
    public void initialize(URL url, ResourceBundle rb) {
        
        cmbFilas.getItems().addAll(6, 7, 8, 9, 10, 11, 12);
        cmbColumnas.getItems().addAll(12, 13, 14, 15, 16);
        
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
        
        //TreeTable de Salas
        JFXTreeTableColumn<Salas, String> idSala = new JFXTreeTableColumn<>("ID");
        idSala.setPrefWidth(50);
        idSala.setCellValueFactory((TreeTableColumn.CellDataFeatures<Salas, String> param) -> param.getValue().getValue().Id);
        
        JFXTreeTableColumn<Salas, String> asientosSala = new JFXTreeTableColumn<>("# Asientos");
        asientosSala.setPrefWidth(60);
        asientosSala.setCellValueFactory((TreeTableColumn.CellDataFeatures<Salas, String> param) -> param.getValue().getValue().NumAsientos);
        
        JFXTreeTableColumn<Salas, String> disponible = new JFXTreeTableColumn<>("Disponibilidad");
        disponible.setPrefWidth(50);
        disponible.setCellValueFactory((TreeTableColumn.CellDataFeatures<Salas, String> param) -> param.getValue().getValue().Disponible);
        
        ObservableList<Salas> salas = FXCollections.observableArrayList();
        
        //Objeto llamada procedimiento almacenado
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/CineDB?useTimezone=true&serverTimezone=UTC","root","Suripanta.98");
            //Objeto llamada procedimiento almacenado
            CallableStatement call = con.prepareCall("{call tablaSalas_sp()}");
            //Envío parámetro a procedimiento almacenado

            ResultSet rs = call.executeQuery();
            while(rs.next()){
                System.out.println(Integer.toString(rs.getInt(1)));
                System.out.println(Integer.toString(rs.getInt(2) * rs.getInt(3)));
                System.out.println(Boolean.toString(rs.getBoolean(4)));
                salas.add(new Salas(new SimpleStringProperty(Integer.toString(rs.getInt(1))), new SimpleStringProperty(Integer.toString(rs.getInt(2) * rs.getInt(3))), 
                        new SimpleStringProperty(Boolean.toString(rs.getBoolean(4)))));
            }
        }catch(SQLException ex){ System.out.println(ex);} catch (ClassNotFoundException ex) {
            Logger.getLogger(RegistroAdministradorController.class.getName()).log(Level.SEVERE, null, ex);
        }
        final TreeItem<Salas> root = new RecursiveTreeItem<>(salas, RecursiveTreeObject::getChildren);
//        tvSalas.getColumns().addAll(idSala, asientosSala, disponible);
//        tvSalas.setRoot(root);
//        tvSalas.setShowRoot(false);
    }    

    
}
