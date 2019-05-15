package cine;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class HomeController implements Initializable {
    
    @FXML
    private Label lblRespuesta;
    SesionUsuario usuario;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        lblRespuesta.setText(usuario.toString());
    }    
    
}
