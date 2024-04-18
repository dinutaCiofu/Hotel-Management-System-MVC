package org.example.model.repository;

import org.example.model.entities.Hotel;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

@Repository
public class HotelRepository extends AbstractRepository<Hotel>{
    public Hotel findByNume(String nume) {
        try (var session = HibernateUtil.getSessionFactory().openSession()) {
            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<Hotel> cq = cb.createQuery(Hotel.class);
            Root<Hotel> root = cq.from(Hotel.class);
            cq.select(root).where(cb.equal(root.get("nume"), nume));
            TypedQuery<Hotel> query = session.createQuery(cq);
            return query.getSingleResult();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}