package cine;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.jfoenix.controls.JFXComboBox;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Tab;

public class SalaController implements Initializable {

    @FXML
    private JFXComboBox<Integer> cmbFilas, cmbColumnas;

    @FXML
    private Tab anexar, control, asignar;
    public String RutaFormato = "C:\\Users\\axelf\\Desktop\\Proyecto Valdepeña\\Cine\\src\\cine\\Salas\\Formatos\\";

    @FXML
    void crearSala(ActionEvent event) {
        int l;
    }

    @FXML
    void cancelar(ActionEvent event) {
        int l;
    }

    @FXML
    void crearDisenio(ActionEvent event) throws FileNotFoundException {
        PdfWriter writer = new PdfWriter(RutaFormato + "miPDF.pdf");
        PdfDocument pdf = new PdfDocument(writer);
        Document doc = new Document(pdf);
        doc.add(new Paragraph("Prueba de pdg"));
        doc.close();
        
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cmbFilas.getItems().addAll(6, 7, 8, 9, 10, 11, 12);
        cmbColumnas.getItems().addAll(12, 13, 14, 15, 16);
    }    

    
}
