package cine;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.beans.property.StringProperty;

class Salas extends RecursiveTreeObject<Salas> {
    StringProperty Id;
    StringProperty NumAsientos;
    StringProperty Disponible;

    public Salas(StringProperty Id, StringProperty NumAsientos, StringProperty Disponible) {
        this.Id = Id;
        this.NumAsientos = NumAsientos;
        this.Disponible = Disponible;
    }

    @Override
    public String toString() {
        return "Salas{" + "Id=" + Id + ", NumAsientos=" + NumAsientos + ", Disponible=" + Disponible + '}';
    }
}
