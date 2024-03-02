package com.redis.sample.entity;

import lombok.Data;
import java.io.Serializable;

@Data
public class User implements Serializable {
    private static final long serialVersionUID = 1L;
    private String id;
    private String name;
    private int age;


    public User(String id, String name, int age) {
        this.id = id;
        this.name = name;
        this.age = age;
    }
}
