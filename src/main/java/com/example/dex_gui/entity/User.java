package com.example.dex_gui.entity;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users")
public class User {

    @ManyToMany
    @JoinTable(
            name = "user_quartet",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "quartet_id")
    )
    private Set<Quartet> quartets = new HashSet<>();

    public Set<Quartet> getQuartets() {
        return quartets;
    }

    public void setQuartets(Set<Quartet> quartets) {
        this.quartets = quartets;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;

    public boolean isEisaiUser() {
        return eisaiUser;
    }

    public void setEisaiUser(boolean eisaiUser) {
        this.eisaiUser = eisaiUser;
    }

    public boolean isSftpUser() {
        return sftpUser;
    }

    public void setSftpUser(boolean sftpUser) {
        this.sftpUser = sftpUser;
    }

    private boolean eisaiUser;
    private boolean sftpUser;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
}
