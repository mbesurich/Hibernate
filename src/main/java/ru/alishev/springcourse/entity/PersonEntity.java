package ru.alishev.springcourse.entity;

import ru.alishev.springcourse.util.DateUtils;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

// define fields
// define constructors
// define getters and setters
// define tostring
// annotate fields

@Entity
@Table(name = "person")
public class PersonEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "person_id")
    private int personId;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name="date_of_birth")
    @Temporal(TemporalType.DATE)
    private Date dateOfBirth;

    @Column(name="email")
    @Email
    private String email;

    @OneToOne(mappedBy = "personEntity", cascade = CascadeType.ALL)
    private Technicalrecuirment technicalrecuirment;

    // в качестве аргумента сюда передаётся название поля из @JoinColumn(name = "person_id") private PersonEntity personEntity;
    @OneToMany(fetch = FetchType.LAZY,
            mappedBy = "personEntity",
            cascade = {CascadeType.DETACH, CascadeType.DETACH,
            CascadeType.REFRESH, CascadeType.PERSIST})//, fetch = FetchType.EAGER)
    private List<Course> courses;

    public PersonEntity() {
    }

    public PersonEntity( String firstName, String lastName, String email, Date theDateOfBirth) {

        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.dateOfBirth = theDateOfBirth;

    }

    public int getPersonId() {
        return personId;
    }

    public void setPersonId(int personId) {
        this.personId = personId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public Technicalrecuirment getTechnicalrecuirment() {
        return technicalrecuirment;
    }

    public void setTechnicalrecuirment(Technicalrecuirment technicalrecuirment) {
        this.technicalrecuirment = technicalrecuirment;
    }

    public List<Course> getCourses() {
        return courses;
    }

    public void setCourses(List<Course> courses) {
        this.courses = courses;
    }

//    add convenience methods for bi-directional relationship

    public void add(Course course) {
        if (course == null) {
            courses = new ArrayList<>();
        }
        courses.add(course);
        course.setPersonEntity(this);
    }

    @Override
    public String toString() {
        return "Person [personId=" + personId + ", firstName=" + firstName + ", lastName=" + lastName + ", email=" + email
                + ", dateOfBirth=" + DateUtils.formatDate(dateOfBirth) + "]";
    }

}
