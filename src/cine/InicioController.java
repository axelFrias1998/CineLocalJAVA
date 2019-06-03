/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cine;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.transitions.hamburger.HamburgerBackArrowBasicTransition;
import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

public class InicioController implements Initializable {

    @FXML private Label lblBienvenido;
    @FXML private PieChart piePeliculas, piePeliculasDatos; 
    @FXML private JFXDrawer drawer;
    @FXML private JFXHamburger hamburger;
    int administradores = 0, usuarios = 0, peliculasP = 0, peliculasSD = 0, peliculasSP = 0;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        datosChart();
        lblBienvenido.setText("Bienvenido " + SesionUsuario.getInstance().getNombre());
        piePeliculas.setData(creaChart());
        piePeliculasDatos.setData(creaChartP());
        piePeliculas.setStartAngle(0);
        try{
            VBox box = FXMLLoader.load(getClass().getResource("Menu.fxml"));
            drawer.setSidePane(box);
            HamburgerBackArrowBasicTransition burgerTask = new HamburgerBackArrowBasicTransition(hamburger);
            burgerTask.setRate(-1);
            hamburger.addEventHandler(MouseEvent.MOUSE_CLICKED, (e) ->{
                burgerTask.setRate(burgerTask.getRate() * -1);
                burgerTask.play();
                if (drawer.isOpened()) {
                    drawer.close();
                    drawer.setStyle("visibility: false;");
                } else {
                    drawer.open();
                    drawer.setStyle("visibility: true;");
            }
            });
            
        } catch (IOException ex) {
            Logger.getLogger(InicioController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    ObservableList<PieChart.Data> creaChart(){
        ObservableList<PieChart.Data> pieData = FXCollections.observableArrayList();
        pieData.add(new PieChart.Data("Usuarios", usuarios));
        pieData.add(new PieChart.Data("Administradores", administradores));
        return pieData;
    }
    
    ObservableList<PieChart.Data> creaChartP(){
        ObservableList<PieChart.Data> pieData = FXCollections.observableArrayList();
        pieData.add(new PieChart.Data("Proyectando", peliculasP));
        pieData.add(new PieChart.Data("Sin estrenar", peliculasSD));
        pieData.add(new PieChart.Data("Sin proyectar", peliculasSP));
        return pieData;
    }
    
    public void datosChart(){
        try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/CineDB?useTimezone=true&serverTimezone=UTC","root","Suripanta.98")) {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("select count(*) from usuario where Rol_Id != 1;");
            if(rs.next())
                usuarios = rs.getInt(1);
            ResultSet rsADMON = stmt.executeQuery("select count(*) from usuario where Rol_Id != 1");
            if(rsADMON.next())
                administradores = rsADMON.getInt(1);
            ResultSet rsProyectando = stmt.executeQuery("select count(*) from pelicula where Estado_Id = 2");
            if(rsProyectando.next())
                peliculasP = rsProyectando.getInt(1);
            ResultSet rsSinEstrenar = stmt.executeQuery("select count(*) from pelicula where Estado_Id = 1");
            if(rsSinEstrenar.next())
                peliculasSD = rsSinEstrenar.getInt(1);
            ResultSet rsSinProyectar = stmt.executeQuery("select count(*) from pelicula where Estado_Id = 3");
            if(rsSinProyectar.next())
                peliculasSP = rsSinProyectar.getInt(1);
        con.close();
        }catch(SQLException ex){ System.out.println(ex);};
    }

           
    
}
