package persistencia;

import logica.Dueno;
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

public class DuenoJpaController implements Serializable {

    public DuenoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }

    public DuenoJpaController() {
        // Aquí se crea la fábrica de EntityManager para conectar con la base de datos
        emf = Persistence.createEntityManagerFactory("PeluCaninaPU"); //  persistence.xml
    }

    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }
    
     // Método para crear un nuevo Alumno
    public void create(Dueno dueno) throws Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(dueno); // Guarda el objeto Dueno en la base de datos
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findDueno(dueno.getId()) != null) {
                throw new Exception("Dueno " + dueno + " ya existe.");
            }
            em.getTransaction().rollback();
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }
    
        // Método para editar un Dueno existente
    public void edit(Dueno dueno) throws Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            dueno = em.merge(dueno); // Realiza un merge (sincroniza los datos con la base de datos)
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findDueno(dueno.getId()) == null) {
                throw new Exception("No se pudo encontrar al dueno con id " + dueno.getId());
            }
            em.getTransaction().rollback();
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }
    
        // Método para eliminar un Alumno
    public void destroy(int id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Dueno dueno;
            try {
                dueno = em.getReference(Dueno.class, id);
                dueno.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("No se pudo encontrar al dueno con id " + id, enfe);
            }
            em.remove(dueno); // Elimina el dueno de la base de datos
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }
    
     // Método para encontrar un Alumno por su ID
    public Dueno findDueno(int id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Dueno.class, id); // Busca un dueno por su id
        } finally {
            em.close();
        }
    }

    // Método para obtener todos los duenos
    public List<Dueno> findDuenoEntities() {
        return findDuenoEntities(true, -1, -1);
    }

    // Método para obtener un rango de duenos
    public List<Dueno> findDuenoEntities(int maxResults, int firstResult) {
        return findDuenoEntities(false, maxResults, firstResult);
    }

    private List<Dueno> findDuenoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery<Dueno> cq = em.getCriteriaBuilder().createQuery(Dueno.class);
            cq.select(cq.from(Dueno.class)); // Selecciona todas las entidades Alumno
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults); // Limita los resultados
                q.setFirstResult(firstResult); // Establece el primer resultado a partir del cual comenzar
            }
            return q.getResultList(); // Retorna la lista de duenos
        } finally {
            em.close();
        }
    }

    // Método para contar todos los duenos
    public int getDuenoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery<Long> cq = em.getCriteriaBuilder().createQuery(Long.class);
            Root<Dueno> rt = cq.from(Dueno.class);
            cq.select(em.getCriteriaBuilder().count(rt)); // Cuenta todos los duenos
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue(); // Convierte el resultado a un entero
        } finally {
            em.close();
        }
    }
}
