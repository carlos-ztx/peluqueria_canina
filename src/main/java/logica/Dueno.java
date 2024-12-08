package logica;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Dueno implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int id;
    @Basic
    private String nombre;
    private String celDueno;

    public Dueno() {
    }

    public Dueno(int id, String nombre, String celDueno) {
        this.id = id;
        this.nombre = nombre;
        this.celDueno = celDueno;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCelDueno() {
        return celDueno;
    }

    public void setCelDueno(String celDueno) {
        this.celDueno = celDueno;
    }

    @Override
    public String toString() {
        return "Dueno{" + "id=" + id + ", nombre=" + nombre + ", celDueno=" + celDueno + '}';
    }
    
    
    
}
