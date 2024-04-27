package org.example.model.repository;

import org.example.model.entities.Hotel;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

@Repository
public class HotelRepository extends AbstractRepository<Hotel>{
    public Hotel findByName(String name) {
        try (var session = HibernateUtil.getSessionFactory().openSession()) {
            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<Hotel> cq = cb.createQuery(Hotel.class);
            Root<Hotel> root = cq.from(Hotel.class);
            cq.select(root).where(cb.equal(root.get("name"), name));
            TypedQuery<Hotel> query = session.createQuery(cq);
            return query.getSingleResult();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }
}