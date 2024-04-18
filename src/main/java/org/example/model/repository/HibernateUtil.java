package org.example.model.repository;

import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

// ofera o sesiune Hibernate configurata
// ofera un mod simplu si reutilizabil de a accesa si gestiona sesiunile
// Hibernate intr-o aplicatie Java, fara a fi nevoie sa se creeze o noua
// instanta de SessionFactory in fiecare loc in care este necesara
// o sesiune Hibernate
public class HibernateUtil {
    // utilizat pentru a crea sesiuni Hibernate si pentru a gestiona
    // cache-ul, conexiunile la baza de date si alte resurse
    private static SessionFactory sessionFactory;

    // daca obiectul sessionFactory nu este inca initializat, atunci
    // se creeaza o noua instanta folosing configurarea implicita
    // din fisierul hibernate.cfg.xml
    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            try {
                // registru de servicii standard Hibernate -  creat utilizand builder
                // configurarea este specificata in fisierul hibernate.cfg.xml
                StandardServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().configure().build();
                sessionFactory = new MetadataSources(serviceRegistry).buildMetadata().buildSessionFactory();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return sessionFactory;
    }
    // se foloseste pentru testare
    // permite setarea unui obiect SessionFactory personalizat
    public static void setSessionFactory(SessionFactory sessionFactory){
        HibernateUtil.sessionFactory = sessionFactory;
    }
}
