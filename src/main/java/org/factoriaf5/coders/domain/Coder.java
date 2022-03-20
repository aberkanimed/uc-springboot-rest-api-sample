package org.factoriaf5.coders.domain;

import javax.persistence.*;

@Entity
@Table(name = "CODER")
public class Coder {
    @Id
    @Column(name = "CODER_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long coderId;
    @Column(name = "FIRST_NAME")
    private String firstName;
    @Column(name = "LAST_NAME")
    private String lastName;
    @Column(name = "EMAIL_ADDRESS")
    private String emailAddress;
    @Column(name = "COUNTRY")
    private String country;
    @Column(name = "COURSE")
    private String course;

    public  Coder() {}

    public Coder(String firstName, String lastName, String emailAddress, String country, String course) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.emailAddress = emailAddress;
        this.country = country;
        this.course = course;
    }

    public long getCoderId() {
        return coderId;
    }

    public void setCoderId(long coderId) {
        this.coderId = coderId;
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

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }
}