package com.zf.springboot.entity;

/**
 * Created by zhife on 2017/6/18.
 */
public class Student {
    private int id;
    private String name;
    private String course;


    public String toString() {
        return String.format("%s, %s, %s", id, name, course);
    }

    public Student(int id, String name, String course) {
        this.id = id;
        this.name = name;
        this.course = course;
    }

    public Student() {
    }

    public int getId() {
        return id;
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

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }
}
