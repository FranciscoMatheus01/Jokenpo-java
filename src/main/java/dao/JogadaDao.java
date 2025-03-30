package dao;


import model.Jogada;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;

public class JogadaDao {
   private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("JogadaPU");

  public void salvar(Jogada jogada) {
      EntityManager em = emf.createEntityManager();
      try {
          em.getTransaction().begin();
          em.persist(jogada);
          em.getTransaction().commit();
      } finally {
          em.close();
      }
  }
  

  public void editar(Jogada jogada) {
      EntityManager em = emf.createEntityManager();
      try {
          em.getTransaction().begin();
          em.merge(jogada);
          em.getTransaction().commit();
      } finally {
          em.close();
      }
  }

  public void excluir(Integer id) {
      EntityManager em = emf.createEntityManager();
      try {
          em.getTransaction().begin();
          Jogada jogada = em.find(Jogada.class, id);
          if (jogada != null) {
              em.remove(jogada);
          }
          em.getTransaction().commit();
      } finally {
          em.close();
      }
  }

  public List<Jogada> listar() {
      EntityManager em = emf.createEntityManager();
      try {
          return em.createQuery("SELECT j FROM Jogada j", Jogada.class).getResultList();
          } finally {
              em.close();
          }
      }
  }

