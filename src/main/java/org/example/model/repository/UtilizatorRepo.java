package org.example.model.repository;

import org.example.model.entities.Utilizator;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

@Repository
public class UtilizatorRepo extends AbstractRepository<Utilizator>{
    public Utilizator findByEmail(String email) {
        try (var session = HibernateUtil.getSessionFactory().openSession()) {
            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<Utilizator> cq = cb.createQuery(Utilizator.class);
            Root<Utilizator> root = cq.from(Utilizator.class);
            cq.select(root).where(cb.equal(root.get("email"), email));
            TypedQuery<Utilizator> query = session.createQuery(cq);
            return query.getSingleResult();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
