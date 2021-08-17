package ru.alishev.springcourse.manager;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;
import ru.alishev.springcourse.entity.Course;
import ru.alishev.springcourse.entity.PersonEntity;
import ru.alishev.springcourse.entity.Technicalrecuirment;
import ru.alishev.springcourse.util.DateUtils;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

public class Manager {

    public static void main(String[] args) {

        SessionFactory factory = null;

        try {
//        создание SessionFactory
            factory = udemySessionFactory();

//            удаление объекта через TechnicalrequirmentObject
//            deleteTechnicalrequirmentObjectHibernate(13, factory);

//            удаление объекта через PersonEntity
//            deletePersonEntityObjectHibernate(10, factory);

//            доставание объекта PersonEntity по его ID + связанный TechnicalrequirmentObject объект
//            getObjectHibernate(14,factory);

//          create Objects__________________________________
/*
            String theDateOfBirthStr = "15/12/1581";

//              creating 1st object
            Date theDateOfBirth = null;
            try {
                theDateOfBirth = DateUtils.parseDate(theDateOfBirthStr);
                PersonEntity personEntity = new PersonEntity("Petr", "Petrov", "Petr@gmail.com", theDateOfBirth);

//              creating 2nd object
                Technicalrecuirment technicalrecuirment = new Technicalrecuirment(false, true, 5.5, "SomeDepartment");

//              associate/link these 2 objects together
                technicalrecuirment.setPersonEntity(personEntity);

                saveObjectsHibernate(technicalrecuirment, factory);
            } catch (ParseException e) {
                e.printStackTrace();
            }
//          create Objects__________________________________
*/

//            getObjectListHibernate(factory);
        } finally {
            factory.close();
        }

    }

    private static SessionFactory udemySessionFactory() {
        SessionFactory factory = new Configuration()
                .configure("hibernate.cfg.xml")
                .addAnnotatedClass(PersonEntity.class)
                .addAnnotatedClass(Technicalrecuirment.class)
                .addAnnotatedClass(Course.class)
                .buildSessionFactory();
        return factory;
    }

    private static void saveObjectsHibernate(Object object, SessionFactory factory) {
//        SessionFactory factory = udemySessionFactory();
        Session session = factory.getCurrentSession();
        try {
            session.beginTransaction();
//      --------------------------------------------------------------------------------------------------------
//      !!!!!   NOTE:   save technicalRequirement  - will also save PeronEntity object because of CascadeType.ALL
//      --------------------------------------------------------------------------------------------------------
            session.save((Technicalrecuirment) object);
//        System.out.println("Saved technicalRequirement: " + (Technicalrecuirment) object);
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    public static void deleteTechnicalrequirmentObjectHibernate(int id, SessionFactory factory) {
        Session session = factory.getCurrentSession();
        session.beginTransaction();

        try {
            Technicalrecuirment technicalrecuirment = session.get(Technicalrecuirment.class, id);
//      --------------------------------------------------------------------------------------------------------
//      !!!!!   NOTE:   delete technicalRequirement  - will also delete PeronEntity object because of CascadeType.ALL
//      --------------------------------------------------------------------------------------------------------
            session.delete(technicalrecuirment);
//        PersonEntity personEntity = session.get(PersonEntity.class, id);
//        session.delete(personEntity);
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }

    }

    public static void deletePersonEntityObjectHibernate(int id, SessionFactory factory) {
        Session session = factory.getCurrentSession();

        try {
//      --------------------------------------------------------------------------------------------------------
//      !!!!!   NOTE:   delete personEntity   - will also delete technicalRequirement object because of CascadeType.ALL
//      --------------------------------------------------------------------------------------------------------
            session.beginTransaction();
            PersonEntity personEntity = session.get(PersonEntity.class, id);
            session.delete(personEntity);
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }

    }

    public static void getObjectHibernate(int id, SessionFactory factory){
//        SessionFactory factory = udemySessionFactory();
        Session session = factory.getCurrentSession();

        try {
            session.beginTransaction();
            PersonEntity personEntity = session.get(PersonEntity.class, id);
            System.out.println("personEntity: " + personEntity);
            Technicalrecuirment technicalrequirment = personEntity.getTechnicalrecuirment();
            System.out.println("technicalrequirment: " + technicalrequirment);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }

    }

    //    методы ниже ещё не переделал -> ...



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
