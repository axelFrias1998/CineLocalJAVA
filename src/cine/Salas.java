package cine;

public class Salas {
    int id, asientos;
    String disponible;
//    public SimpleStringProperty proyectando = new SimpleStringProperty();

    public Salas(int id, int asientos, String disponible) {
        this.id = id;
        this.asientos = asientos;
        this.disponible = disponible;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAsientos() {
        return asientos;
    }

    public void setAsientos(int asientos) {
        this.asientos = asientos;
    }

    public String isDisponible() {
        return disponible;
    }

    public void setDisponible(String disponible) {
        this.disponible = disponible;
    }

    
    @Override
    public String toString() {
        return "Salas{" + "id=" + id + ", asientos=" + asientos + ", disponible=" + disponible + '}';
    }

    
}
