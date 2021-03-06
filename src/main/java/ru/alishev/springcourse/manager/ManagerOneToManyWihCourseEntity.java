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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ManagerOneToManyWihCourseEntity {

    public static void main(String[] args) {

        SessionFactory factory = null;

        try {
//        создание SessionFactory
            factory = udemySessionFactory();

//              add courses into Person __________________________________
/*            Course course1 = new Course("firstCourse");
            Course course2 = new Course("secondCourse");
            Course course3 = new Course("thirdCourse");
            addCoursesIntoPerson(factory, 17, course1, course2, course3);*/
//              add courses into Person __________________________________

//              get courses from Person __________________________________
            List<Course> courses = getCourses(factory, 17);
            courses.forEach(System.out::println);
//              get courses from Person __________________________________

//              delete course from DB, but Person will keep existing __________________________________
//            deleteCourseFromDB(factory, 1);
//              delete course from DB, but Person will keep existing __________________________________

//            getObjectListHibernate(factory);
        } finally {
            factory.close();
        }
    }

    public static SessionFactory udemySessionFactory() {
        SessionFactory factory = new Configuration()
                .configure("hibernate.cfg.xml")
                .addAnnotatedClass(PersonEntity.class)
                .addAnnotatedClass(Technicalrecuirment.class)
                .addAnnotatedClass(Course.class)
                .buildSessionFactory();
        return factory;
    }

    public static void addCoursesIntoPerson(SessionFactory factory, int idOfPerson, Course...courses) {
        Session session = factory.getCurrentSession();
        try {
            session.beginTransaction();
            PersonEntity personEntity = session.get(PersonEntity.class, idOfPerson);
            for (Course course : courses){
                personEntity.add(course);
            }
            for (Course course : courses){
                session.save(course);
            }
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    public static void deleteCourseFromDB(SessionFactory factory, int idOfCourse) {
        Session session = factory.getCurrentSession();
        try {
            session.beginTransaction();
            Course course = session.get(Course.class, idOfCourse);
            session.delete(course);
            session.getTransaction().commit();
            System.out.println("course was deleted: " + course);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    public static List<Course> getCourses(SessionFactory factory, int idOfPerson) {
//        List<Course> courses = null;
        List<Course> courses = new ArrayList<>();
        Session session = factory.getCurrentSession();
        try {
            session.beginTransaction();
            PersonEntity personEntity = session.get(PersonEntity.class, idOfPerson);
//            System.out.println("person: " + personEntity);
//            courses = personEntity.getCourses();

            for(Course course : personEntity.getCourses()){
                courses.add(course);
            }

//            System.out.println("Courses: " + courses);
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
        return courses;
    }

}
