package persistencia;

import logica.Mascota;
import persistencia.exceptions.NonexistentEntityException;
import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.Persistence;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

public class MascotaJpaController implements Serializable {

    public MascotaJpaController(EntityManagerFactory emf) {  // Entity Manager Factory (emf)
        this.emf = emf;
    }

    public MascotaJpaController() {
        // Aquí se crea la fábrica de EntityManager para conectar con la base de datos
        emf = Persistence.createEntityManagerFactory("PeluCaninaPU"); //  persistence.xml
    }

    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }
    
     // Método para crear un nuevo Mascota
    public void create(Mascota mascota) throws Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(mascota); // Guarda el objeto Mascota en la base de datos
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findMascota(mascota.getNumeroCliente()) != null) {
                throw new Exception("Mascota " + mascota + " ya existe.");
            }
            em.getTransaction().rollback();
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }
    
        // Método para editar un Mascota existente
    public void edit(Mascota mascota) throws Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            mascota = em.merge(mascota); // Realiza un merge (sincroniza los datos con la base de datos)
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findMascota(mascota.getNumeroCliente()) == null) {
                throw new Exception("No se pudo encontrar la mascota con id " + mascota.getNumeroCliente());
            }
            em.getTransaction().rollback();
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }
    
        // Método para eliminar un Mascota
    public void destroy(int id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Mascota mascota;
            try {
                mascota = em.getReference(Mascota.class, id);
                mascota.getNumeroCliente();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("No se pudo encontrar la mascota con id " + id, enfe);
            }
            em.remove(mascota); // Elimina la mascota de la base de datos
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }
    
     // Método para encontrar una Mascota por su ID
    public Mascota findMascota(int id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Mascota.class, id); // Busca un mascota por su id
        } finally {
            em.close();
        }
    }

    // Método para obtener todos las Mascotas
    public List<Mascota> findMascotaEntities() {
        return findMascotaEntities(true, -1, -1);
    }

    // Método para obtener un rango de mascotas
    public List<Mascota> findMascotaEntities(int maxResults, int firstResult) {
        return findMascotaEntities(false, maxResults, firstResult);
    }

    private List<Mascota> findMascotaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery<Mascota> cq = em.getCriteriaBuilder().createQuery(Mascota.class);
            cq.select(cq.from(Mascota.class)); // Selecciona todas las entidades Mascota
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults); // Limita los resultados
                q.setFirstResult(firstResult); // Establece el primer resultado a partir del cual comenzar
            }
            return q.getResultList(); // Retorna la lista de mascotaa
        } finally {
            em.close();
        }
    }

    // Método para contar todos los mascotas
    public int getMascotaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery<Long> cq = em.getCriteriaBuilder().createQuery(Long.class);
            Root<Mascota> rt = cq.from(Mascota.class);
            cq.select(em.getCriteriaBuilder().count(rt)); // Cuenta todos las mascota
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue(); // Convierte el resultado a un entero
        } finally {
            em.close();
        }
    }
}
