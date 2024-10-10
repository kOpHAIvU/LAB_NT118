package com.example.task_6;

public class Employee {
    String id, name;
    Boolean position;

    public Employee(String id, String name, Boolean position) {
        this.id = id;
        this.name = name;
        this.position = position;
    }

    public Boolean getPosition() {
        return position;
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }
}
