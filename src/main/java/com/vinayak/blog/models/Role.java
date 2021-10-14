package com.vinayak.blog.models;

import javax.persistence.*;

import static javax.persistence.GenerationType.AUTO;

@Entity
@Table(name = "Role")
public class Role {
    @Id
    @GeneratedValue(strategy = AUTO)
    @Column(name = "id", nullable = false)
    private int id;
    private String name;

    public int getId() {
        return id;
    }

    public Role() {
    }

    public Role(String name) {
        this.name = name;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
