package com.ibm.oops.abstraction;

public abstract class Shape {

    public abstract void displayArea();

    public void displayName(String name) {
        IO.println("Name: " + name);
    }
}
