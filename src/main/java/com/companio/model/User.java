package com.companio.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name="app_user")
public class User {
    @Id
    private String email;
    private String name;
    private String password;
    private boolean verified=false;//default setting is false;
}
