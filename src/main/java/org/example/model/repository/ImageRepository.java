package org.example.model.repository;

import org.example.model.entities.Image;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

@Repository
public class ImageRepository extends AbstractRepository<Image> {
    public List<Image> findAllImagesByRoomId(Long roomId) {
        try (var session = HibernateUtil.getSessionFactory().openSession()) {
            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<Image> cq = cb.createQuery(Image.class);
            Root<Image> root = cq.from(Image.class);
            cq.select(root).where(cb.equal(root.get("room"), roomId));
            TypedQuery<Image> query = session.createQuery(cq);
            return query.getResultList();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }
}
