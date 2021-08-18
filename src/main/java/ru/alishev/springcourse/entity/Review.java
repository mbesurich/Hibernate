package ru.alishev.springcourse.entity;

import javax.persistence.*;

// define fields
// define constructors
// define getters and setters
// define tostring
// annotate fields

@Entity
@Table(name = "review")
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "review_id")
    private int id;

    @Column(name = "comment")
    private String comment;


//    private Course course;
//    courses_id int REFERENCES courses (courses_id)


    public Review() {
    }

    public Review(String comment) {
        this.comment = comment;
    }

    public int getId() {
        return id;
    }

    public void setId(int review_id) {
        this.id = review_id;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    @Override
    public String toString() {
        return "Review{" +
                "review_id=" + id +
                ", comment='" + comment + '\'' +
                '}';
    }
}
