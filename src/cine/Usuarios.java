package cine;

public class Usuarios {
    private int Id;
    private String Nombre;
    private String Correo;;
    private String Contraseña;;
    private float Saldo;
    private int TipoUsuario;

    public Usuarios(int Id, String Nombre, String Correo, String Contraseña, float Saldo, int TipoUsuario) {
        this.Id = Id;
        this.Nombre = Nombre;
        this.Correo = Correo;
        this.Contraseña = Contraseña;
        this.Saldo = Saldo;
        this.TipoUsuario = TipoUsuario;
    }

    public int getId() {
        return Id;
    }

    public void setId(int Id) {
        this.Id = Id;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String Nombre) {
        this.Nombre = Nombre;
    }

    public String getCorreo() {
        return Correo;
    }

    public void setCorreo(String Correo) {
        this.Correo = Correo;
    }

    public String getContraseña() {
        return Contraseña;
    }

    public void setContraseña(String Contraseña) {
        this.Contraseña = Contraseña;
    }

    public float getSaldo() {
        return Saldo;
    }

    public void setSaldo(float Saldo) {
        this.Saldo = Saldo;
    }

    public int getTipoUsuario() {
        return TipoUsuario;
    }

    public void setTipoUsuario(int TipoUsuario) {
        this.TipoUsuario = TipoUsuario;
    }

    @Override
    public String toString() {
        return "Usuarios{" + "Id=" + Id + ", Nombre=" + Nombre + ", Correo=" + Correo + ", Contrase\u00f1a=" + Contraseña + ", Saldo=" + Saldo + ", TipoUsuario=" + TipoUsuario + '}';
    }
}
