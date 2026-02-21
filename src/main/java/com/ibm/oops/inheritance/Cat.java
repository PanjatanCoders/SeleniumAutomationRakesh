package com.ibm.oops.inheritance;

public class Cat extends Animal{
    public Cat() {
        super("Cat");
    }

    void sound() {
        IO.println("Cat is sounds -  Meaw");
    }
}
