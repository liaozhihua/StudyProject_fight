package com.xm.studyproject.reflect;

public class Person {
    private String name;
    private int age;

    private Person(String name) {
        this.name = name;
    }

    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }

    private void setInfo(String name, int age) {
        int tempName = 3;
        this.name = name;
        this.age = age;
    }
}
