package logica;

import java.util.List;
import persistencia.ControladoraPersistencia;

public class Controladora {
    ControladoraPersistencia control = new ControladoraPersistencia();

    public void guardarDatos(String nombreMascota, String raza, String color, 
            String observaciones, String alergico, String especial, String nombreDueno, 
            String celDueno) {
        
        Dueno dueno = new Dueno();
        dueno.setNombre(nombreDueno);
        dueno.setCelDueno(celDueno);
        
        Mascota mascota = new Mascota();
        mascota.setNombre(nombreMascota);
        mascota.setColor(color);
        mascota.setRaza(raza);
        mascota.setAlergico(alergico);
        mascota.setAtencionEspecial(especial);
        mascota.setObservaciones(observaciones);
        mascota.setDueno(dueno);
        
        control.guardar(dueno,mascota);
    }

    public List<Mascota> traerMascotas() {
        return control.traerMascotas();
    }

    public void borrarMascota(int num_cliente) {
        control.borrarMascota(num_cliente);
    }

    public Mascota traerMascota(int num_dueno) {
        return control.traerMascota(num_dueno);
    }

    public void editarDatos(Mascota mascota,String nombreMascota, String raza, String color,
            String observaciones, String alergico, String especial, String nombreDueno,
            String celDueno) {
        
        
        mascota.setNombre(nombreMascota);
        mascota.setColor(color);
        mascota.setRaza(raza);
        mascota.setAlergico(alergico);
        mascota.setAtencionEspecial(especial);
        mascota.setObservaciones(observaciones);
        
        control.editar(mascota);
        
        Dueno dueno = this.buscarDueno(mascota.getDueno());
        dueno.setCelDueno(celDueno);
        dueno.setNombre(nombreDueno);
        
        control.editar(dueno);
    }

    private Dueno buscarDueno(Dueno dueno) {
        return control.traerDueno(dueno);
    }


    
}
