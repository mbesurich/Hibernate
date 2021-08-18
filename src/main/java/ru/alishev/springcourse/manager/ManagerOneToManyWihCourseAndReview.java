package ru.alishev.springcourse.manager;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;
import ru.alishev.springcourse.entity.Course;
import ru.alishev.springcourse.entity.PersonEntity;
import ru.alishev.springcourse.entity.Review;
import ru.alishev.springcourse.entity.Technicalrecuirment;

import java.util.ArrayList;
import java.util.List;

public class ManagerOneToManyWihCourseAndReview {

    public static void main(String[] args) {

        SessionFactory factory = null;

        try {
//        создание SessionFactory
            factory = udemySessionFactory();

//              add reviews into course __________________________________
/*            Course course1 = new Course("firstCourseWithReview");
            addReviewsIntoCourse(factory, course1,
                    new Review("Review number 1"),
                    new Review("Review 2 number 1"),
                    new Review("Review 333 number 1"));*/
//              add reviews into course __________________________________

//              get course __________________________________
//            System.out.println("I've got the course: " + getCourse(factory, 4));
//              get course __________________________________

//              get courses into Person __________________________________
//            List<Course> courses = getCourses(factory, 17);
//            courses.forEach(System.out::println);
//              get courses into Person __________________________________

//              delete course from DB, but Person will keep existing __________________________________
            deleteCourseFromDB(factory, 5);
//              delete course from DB, but Person will keep existing __________________________________

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
                .addAnnotatedClass(Review.class)
                .buildSessionFactory();
        return factory;
    }

    public static void addReviewsIntoCourse(SessionFactory factory, Course course, Review...reviews) {
        Session session = factory.getCurrentSession();
        try {
            session.beginTransaction();
            for (Review review : reviews){
                course.addReviews(review);
            }
            System.out.printf("saving the course %s with it's reviews %s", course, course.getReviews());
            session.save(course); // because of cascadeType.ALL - this save(course) - will save all reviews in this course
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    public static Course getCourse(SessionFactory factory, int courseId){
        Session session = factory.getCurrentSession();
        Course course = null;
        try {
            session.beginTransaction();
            course = session.get(Course.class, courseId);
//            System.out.println(course);
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
        return course;
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

}
