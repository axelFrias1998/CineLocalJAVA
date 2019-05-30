package cine;

import java.sql.Date;

public class Orden {
    private int Id_orden;
    private float Monto;
    private String Estado;
    private Date  Fecha;
    private String Id_Usuario;
    private int Id_funcion;
    private String Pelicula;
    private String TipoProyect;

    public String getTipoProyect() {
        return TipoProyect;
    }

    public void setTipoProyect(String TipoProyect) {
        this.TipoProyect = TipoProyect;
    }

    public String getPelicula() {
        return Pelicula;
    }

    public void setPelicula(String Pelicula) {
        this.Pelicula = Pelicula;
    }

    public String getId_Usuario() {
        return Id_Usuario;
    }

    public void setId_Usuario(String Id_Usuario) {
        this.Id_Usuario = Id_Usuario;
    }

    public String getEstado() {
        return Estado;
    }

    public void setEstado(String Estado) {
        this.Estado = Estado;
    }

    public Orden(int Id_orden, float Monto, String Estado, Date Fecha, String Id_Usuario, String Pelicula, String TipoProyect) {
        this.Id_orden = Id_orden;
        this.Monto = Monto;
        this.Estado = Estado;
        this.Fecha = Fecha;
        this.Id_Usuario = Id_Usuario;
        this.Pelicula = Pelicula;
        this.TipoProyect = TipoProyect;
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



    public int getId_funcion() {
        return Id_funcion;
    }

    public void setId_funcion(int Id_funcion) {
        this.Id_funcion = Id_funcion;
    }

    @Override
    public String toString() {
        return "Ordenes{" + "Id_orden=" + Id_orden + ", Monto=" + Monto + ", Estado=" + Estado + ", Fecha=" + Fecha + ", Id_Usuario=" + Id_Usuario + ", Id_funcion=" + Id_funcion + ", Pelicula=" + Pelicula + ", TipoProyect=" + TipoProyect + '}';
    }
}
