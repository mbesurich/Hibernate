package ru.alishev.springcourse.entity;

import javax.persistence.*;

@Entity
@Table(name = "technicalrecuirment")
public class Technicalrecuirment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "technicalrecuirment_id")
    private int technicalrecuirment_id;

    @Column(name = "windows")
    private boolean windows;

    @Column(name = "msoffice")
    private boolean msoffice;

    @Column(name = "yearsofworking")
    private double yearsofworking;

    @Column(name = "department")
    private String department;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "person_id")
    private PersonEntity personEntity;

    public Technicalrecuirment() {
    }

    public Technicalrecuirment(boolean windows, boolean msoffice, double yearsofworking, String department) {
        this.windows = windows;
        this.msoffice = msoffice;
        this.yearsofworking = yearsofworking;
        this.department = department;
    }

    public int getTechnicalrecuirment_id() {
        return technicalrecuirment_id;
    }

    public void setTechnicalrecuirment_id(int technicalrecuirment_id) {
        this.technicalrecuirment_id = technicalrecuirment_id;
    }

    public boolean isWindows() {
        return windows;
    }

    public void setWindows(boolean windows) {
        this.windows = windows;
    }

    public boolean isMsoffice() {
        return msoffice;
    }

    public void setMsoffice(boolean msoffice) {
        this.msoffice = msoffice;
    }

    public double getYearsofworking() {
        return yearsofworking;
    }

    public void setYearsofworking(double yearsofworking) {
        this.yearsofworking = yearsofworking;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public PersonEntity getPersonEntity() {
        return personEntity;
    }

    public void setPersonEntity(PersonEntity personEntity) {
        this.personEntity = personEntity;
    }

    @Override
    public String toString() {
        return "Technicalrecuirment{" +
                "technicalrecuirment_id=" + technicalrecuirment_id +
                ", windows=" + windows +
                ", msoffice=" + msoffice +
                ", yearsofworking=" + yearsofworking +
                ", department='" + department + '\'' +
                '}';
    }
}
