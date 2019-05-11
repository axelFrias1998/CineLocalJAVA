/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cine;

import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

/**
 *
 * @author axelf
 */
public class FXMLDocumentController implements Initializable {
    
    @FXML
    private Label label;
    
    @FXML
    private void handleButtonAction(ActionEvent event) {
        try{
            Class.forName("com.mysql.jdbc.Driver");

            Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/CineDB","root","");
            //here sonoo is the database name, root is the username and root is the password
            Statement stmt=con.createStatement();
            ResultSet rs=stmt.executeQuery("select * from CineDB.TipoProyeccion");
            
            while(rs.next())
            System.out.println(rs.getInt(1) + " " + rs.getString(2) + " $" + rs.getFloat(3));

            con.close();

        }catch(Exception e){ System.out.println(e);}
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
