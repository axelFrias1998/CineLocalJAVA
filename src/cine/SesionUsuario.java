package cine;

public class SesionUsuario {
    
        private static SesionUsuario instance;
                
        private String Nombre;
        private String Correo;
        private Integer Rol;
        private Integer Id;

    private SesionUsuario(String Nombre, String Correo, Integer Rol, Integer Id) {
        this.Nombre = Nombre;
        this.Correo = Correo;
        this.Rol = Rol;
        this.Id = Id;
    }
    
    public static SesionUsuario getInstance(String Nombre, String Correo, Integer Rol, Integer Id){
        if(instance == null)
                instance = new SesionUsuario(Nombre, Correo, Rol, Id);
        return instance;
    }
    
    public static SesionUsuario getInstance(){
        
                
            return instance;
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

    public int getRol() {
        return Rol;
    }

    public void setRol(Integer Rol) {
        this.Rol = Rol;
    }

    public int getId() {
        return Id;
    }

    public void setId(Integer Id) {
        this.Id = Id;
    }

    @Override
    public  String toString() {
        return "Bienvenido SesionUsuario{" + "Nombre=" + Nombre + ", Correo=" + Correo + ", Rol=" + Rol + ", Id=" + Id + '}';
    }
    
    
}
