package com.ibm.oops.abstraction;

public abstract class Shape {
     double pi = 3.14;

    public abstract void displayArea(); //abstract/not-implemented method

    public void displayName(String name) { //concrete/implemented method
        IO.println("Name: " + name);
    }
}
