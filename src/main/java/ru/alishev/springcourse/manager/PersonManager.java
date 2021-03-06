package ru.alishev.springcourse.manager;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

import org.hibernate.query.Query;
import ru.alishev.springcourse.entity.PersonEntity;
import ru.alishev.springcourse.util.DateUtils;


import java.text.ParseException;
import java.util.Date;
import java.util.List;

public class PersonManager {
    public static void main(String[] args) {

        SessionFactory factory = null;
        try {
//        создание SessionFactory
            factory = udemySessionFactory();

//        -------------------------------------------
//        сохранение объекта в БД
//        -------------------------------------------
//        PersonEntity p = new PersonEntity();
//        p.setFirstName("Mike");
//        p.setLastName("Tyson");
//        saveObjectsHibernate(p, factory);

//        -------------------------------------------
//        Доставание объекта из БД
//        -------------------------------------------
//        System.out.println(getObjectHibernate( 10, factory));

//        System.out.println(getObjectListWithParamHibernate(factory));

//        -------------------------------------------
//        Изменение объекта в БД
//        -------------------------------------------
//        getObjectListHibernate(factory);
//        changeObjectNameByHibernate( 5, factory, "Boris");
//        changeObjectNameByHQuery( 5, factory, "Scoooooby");

//        -------------------------------------------
//        Изменение объекта в БД
//        -------------------------------------------
//        deleteObjectHibernate(1, factory);
//        deleteObjectNameByHQuery(2, factory);


//        -------------------------------------------
//        добавление объекта с полем дата в БД
//        -------------------------------------------
            String theDateOfBirthStr = "29/08/1990";

            Date theDateOfBirth = null;
            try {
                theDateOfBirth = DateUtils.parseDate(theDateOfBirthStr);
                PersonEntity tempPerson = new PersonEntity("Samy", "Barbarisky", "Samy@gmail.com", theDateOfBirth);
                saveObjectsHibernate(tempPerson, factory);
            } catch (ParseException e) {
                e.printStackTrace();
            }


            getObjectListHibernate(factory);
        } finally {
            factory.close();
        }

    }

    private static SessionFactory buildSessionFactory() {
        try {
            StandardServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                    .configure("hibernate.cfg.xml").build();

            Metadata metadata = new MetadataSources(serviceRegistry).getMetadataBuilder().build();

            return metadata.getSessionFactoryBuilder().build();
        } catch (Throwable ex) {
            System.err.println("Initial SessionFactory creation failed." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    private static SessionFactory udemySessionFactory() {
        SessionFactory factory = new Configuration()
                .configure("hibernate.cfg.xml")
                .addAnnotatedClass(PersonEntity.class)
                .buildSessionFactory();
        return factory;
    }

    private static void saveObjectsHibernate(Object object, SessionFactory factory) {
//        SessionFactory factory = udemySessionFactory();
            Session session = factory.getCurrentSession();
            session.beginTransaction();
            session.save((PersonEntity) object);
//            int id = session.save((PersonEntity) object);
//            System.out.println(id);
            session.getTransaction().commit();
//            System.out.println("did commit");
            session.close();
//            System.out.println("session closed");

    }

    public static Object getObjectHibernate(int id, SessionFactory factory){
//        SessionFactory factory = udemySessionFactory();
        Session session = factory.getCurrentSession();
        session.beginTransaction();
        PersonEntity personEntity = session.get(PersonEntity.class, id);
        session.close();
        return personEntity;
    }

    public static List<? extends Object> getObjectListHibernate(SessionFactory factory) {
        Session session = factory.getCurrentSession();
        session.beginTransaction();
        List<PersonEntity> list = session.createQuery("FROM PersonEntity").list();

        list.forEach(System.out::println);

        session.close();
        return list;
    }

    public static List<? extends Object> getObjectListWithParamHibernate(SessionFactory factory) {
        Session session = factory.getCurrentSession();
        session.beginTransaction();

//        вернуть всех person, у кого firstName='Василий'
//        List<PersonEntity> list = session.createQuery("FROM PersonEntity p WHERE p.firstName='Василий'").list();

//        вернуть всех person, у кого firstName='Василий' или lastName заканчивается на 'son'
        List<PersonEntity> list = session.createQuery("FROM PersonEntity p WHERE p.firstName='Василий' OR p.lastName LIKE '%son'").list();

        session.close();
        return list;
    }

    public static void changeObjectNameByHibernate(int id, SessionFactory factory, String name) {
        Session session = factory.getCurrentSession();
        session.beginTransaction();
        PersonEntity personEntity = session.get(PersonEntity.class, id);
        personEntity.setFirstName(name);
        session.getTransaction().commit();
        session.close();
    }

    public static void changeObjectNameByHQuery(int id, SessionFactory factory, String name) {
        Session session = factory.getCurrentSession();
        session.beginTransaction();
        String hql = "UPDATE PersonEntity SET firstName=:name WHERE firstName='Vasili'";
        Query query = session.createQuery(hql);
        query.setParameter("name", name);
        int result = query.executeUpdate();
//        session.createQuery("UPDATE PersonEntity SET firstName=:name WHERE firstName='Vasili'");

//        PersonEntity personEntity = session.get(PersonEntity.class, id);
//        personEntity.setFirstName(name);
        session.getTransaction().commit();
        session.close();
    }

    public static void deleteObjectHibernate(int id, SessionFactory factory) {
        Session session = factory.getCurrentSession();
        session.beginTransaction();
        PersonEntity personEntity = session.get(PersonEntity.class, id);
        session.delete(personEntity);
        session.getTransaction().commit();
        session.close();
    }

    public static void deleteObjectNameByHQuery(int id, SessionFactory factory) {
        Session session = factory.getCurrentSession();
        session.beginTransaction();
        String hql = "DELETE FROM PersonEntity WHERE id=:id";
        Query query = session.createQuery(hql);
        query.setParameter("id", id);
        int result = query.executeUpdate();

        session.getTransaction().commit();
        session.close();
    }
}
