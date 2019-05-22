package cine;


import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.validation.RequiredFieldValidator;
import java.io.FileNotFoundException;
import java.net.URL;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ResourceBundle;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.layout.Pane;

public class SalaController implements Initializable {

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
//                    System.out.println((char)(fila + i) + Integer.toString(j));
                    call.setInt(1, SesionSala.getInstance().getIdSala());
                    call.executeQuery();
                }
            }
            con.close();
            SesionSala.liberaSala();
            System.out.println(SesionSala.getInstance().getIdSala());
        }catch(SQLException ex){ System.out.println(ex);};
    }

    @FXML
    void cancelar(ActionEvent event) {
        int l;
    }

    @FXML
    void crearDisenio(ActionEvent event) throws FileNotFoundException, ClassNotFoundException {
        paneRes.setVisible(true);
        paneRes.setManaged(true);
        lblTotalAsientos.setText("Tu sala tendrá " + cmbColumnas.getValue() * cmbFilas.getValue() +" asientos");
        
        Class.forName("com.mysql.cj.jdbc.Driver");
        //Objeto llamada procedimiento almacenado
        try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/CineDB?useTimezone=true&serverTimezone=UTC","root","Suripanta.98")) {
            //Objeto llamada procedimiento almacenado
            CallableStatement call = con.prepareCall("{call nuevaSala_sp(?, ?, ?)}");
            //Envío parámetro a procedimiento almacenado
            call.setInt(1, cmbFilas.getValue());
            System.out.println(cmbFilas.getValue());
            call.setInt(2, cmbColumnas.getValue());
            System.out.println(cmbColumnas.getValue());
            call.registerOutParameter(3, Types.INTEGER);
            call.executeQuery();
            SesionSala.setInstance(cmbColumnas.getValue(), cmbFilas.getValue(), call.getInt(3),cmbColumnas.getValue() * cmbFilas.getValue());
            System.out.println(call.getInt(3));
            con.close();
        }catch(SQLException ex){ System.out.println(ex);};
//        PdfWriter writer = new PdfWriter(RutaFormato + "prueba.pdf");
//        PdfDocument pdf = new PdfDocument(writer);
//        Document doc = new Document(pdf);
//        doc.add(new Paragraph("Prueba"));
//        doc.close();
    }
    @Override
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
    }    

    
}
