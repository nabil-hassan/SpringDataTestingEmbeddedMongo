package net.nh.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Objects;

@Document(collection = "customer")
public class Customer {

    @Id
    private Long id;

    private String forename;
    private String surname;
    private String username;
    private String passwordHash;

    public Customer() {
    }

    public Customer(Long id, String forename, String surname, String username, String passwordHash) {
        this.id = id;
        this.forename = forename;
        this.surname = surname;
        this.username = username;
        this.passwordHash = passwordHash;
    }

    public Long getId() {
        return id;
    }

    public String getForename() {
        return forename;
    }

    public void setForename(String forename) {
        this.forename = forename;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Customer customer = (Customer) o;
        return Objects.equals(id, customer.id) &&
                Objects.equals(forename, customer.forename) &&
                Objects.equals(surname, customer.surname) &&
                Objects.equals(username, customer.username) &&
                Objects.equals(passwordHash, customer.passwordHash);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, forename, surname, username, passwordHash);
    }
}
