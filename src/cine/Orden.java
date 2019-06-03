package cine;

import java.sql.Date;

public class Orden {
    private int Id_orden;
    private float Monto;
    private Date  Fecha;
    private String Usuario;
    private String Pelicula;

    public Orden(int Id_orden, float Monto, Date Fecha, String Usuario, String Pelicula) {
        this.Id_orden = Id_orden;
        this.Monto = Monto;
        this.Fecha = Fecha;
        this.Usuario = Usuario;
        this.Pelicula = Pelicula;
    }

    public String getUsuario() {
        return Usuario;
    }

    public void setUsuario(String Usuario) {
        this.Usuario = Usuario;
    }

    
    public String getPelicula() {
        return Pelicula;
    }

    public void setPelicula(String Pelicula) {
        this.Pelicula = Pelicula;
    }

    public int getId_orden() {
        return Id_orden;
    }

    public void setId_orden(int Id_orden) {
        this.Id_orden = Id_orden;
    }

    public float getMonto() {
        return Monto;
    }

    public void setMonto(float Monto) {
        this.Monto = Monto;
    }

  
    public Date getFecha() {
        return Fecha;
    }

    public void setFecha(Date Fecha) {
        this.Fecha = Fecha;
    }
}
