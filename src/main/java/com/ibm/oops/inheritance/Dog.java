package com.ibm.oops.inheritance;

public class Dog extends Animal {
    public Dog() {
        super("Dog"); // new Animal("Dog")
    }

    void sound() {
        IO.println("Dog is sounds -  Bark");
    }
//
    @Override
    void eat() {
        IO.println("Dog is eating!");
        // ---
    }
}

// Override: (Dynamic binding/RunTime polymorphism)
/*
    - method signature should exactly same
    - method should into a Parent-Child classes
*/
// has a relationship --
// is a relationship -- inheritance