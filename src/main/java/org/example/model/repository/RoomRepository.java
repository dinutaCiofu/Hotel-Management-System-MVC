package org.example.model.repository;

import org.example.model.entities.Room;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

@Repository
public class RoomRepository extends AbstractRepository<Room> {
    public Room findRoomByNumber(String nrRoom) {
        try (var session = HibernateUtil.getSessionFactory().openSession()) {
            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<Room> cq = cb.createQuery(Room.class);
            Root<Room> root = cq.from(Room.class);
            cq.select(root).where(cb.equal(root.get("nrRoom"), nrRoom));
            TypedQuery<Room> query = session.createQuery(cq);
            return query.getSingleResult();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }


}
