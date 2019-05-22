package cine;

public class SesionSala {
    private static SesionSala instance;
    
    private int NumAsientos;
    private int Columnas;
    private int Filas;
    private int IdSala;

    public SesionSala(int NumAsientos, int Columnas, int Filas, int IdSala) {
        this.NumAsientos = NumAsientos;
        this.Columnas = Columnas;
        this.Filas = Filas;
        this.IdSala = IdSala;
    }

    public static SesionSala getInstance() {
        return instance;
    }

    public static SesionSala setInstance(int Columnas, int Filas, int IdSala, int NumAsientos) {
        if(instance == null)
            instance = new SesionSala(NumAsientos, Columnas, Filas, IdSala);
        return instance;
    }
    
    public static void liberaSala(){
        instance = null;
    }

    public int getNumAsientos() {
        return NumAsientos;
    }

    public void setNumAsientos(int NumAsientos) {
        this.NumAsientos = NumAsientos;
    }

    public int getColumnas() {
        return Columnas;
    }

    public void setColumnas(int Columnas) {
        this.Columnas = Columnas;
    }

    public int getFilas() {
        return Filas;
    }

    public void setFilas(int Filas) {
        this.Filas = Filas;
    }

    public int getIdSala() {
        return IdSala;
    }

    public void setIdSala(int IdSala) {
        this.IdSala = IdSala;
    }
    
}
