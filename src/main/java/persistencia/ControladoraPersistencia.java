package persistencia;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import logica.Dueno;
import logica.Mascota;
import persistencia.exceptions.NonexistentEntityException;

public class ControladoraPersistencia {

    DuenoJpaController duenoJPA = new DuenoJpaController();
    MascotaJpaController mascotaJPA = new MascotaJpaController();

    public void guardar(Dueno dueno, Mascota mascota) {
        try {
            duenoJPA.create(dueno);
            mascotaJPA.create(mascota);
        } catch (Exception ex) {
            Logger.getLogger(ControladoraPersistencia.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            duenoJPA = null;
            mascotaJPA = null;
        }
    }

    public List<Mascota> traerMascotas() {
        return mascotaJPA.findMascotaEntities();
    }

    public void borrarMascota(int num_cliente) {
        try {
            mascotaJPA.destroy(num_cliente);
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(ControladoraPersistencia.class.getName()).log(Level.SEVERE, null, ex);
        }
        ;
    }

    public Mascota traerMascota(int num_dueno) {
        return mascotaJPA.findMascota(num_dueno);
    }

    public void editar(Mascota mascota) {
        
        try {
            mascotaJPA.edit(mascota);
        } catch (Exception ex) {
            Logger.getLogger(ControladoraPersistencia.class.getName()).log(Level.SEVERE, null, ex);
        }
       
    }

    public Dueno traerDueno(Dueno dueno) {
        return duenoJPA.findDueno(dueno.getId());
    }

    public void editar(Dueno dueno) {
        try {
            duenoJPA.edit(dueno);
        } catch (Exception ex) {
            Logger.getLogger(ControladoraPersistencia.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
