package com.ibm.oops.inheritance;

public class TestInheritance {
    static void main() {
        Dog dog = new Dog();

        dog.eat();
        dog.run();
        dog.sound();
//        obj.sleep();
        dog.eat();


        IO.println("*".repeat(30));
        Cat cat = new Cat();
        cat.sound();
//        cat.eat();
        cat.run();
    }
}


/*
    Browser - BaseClass
    - Chrome - url, close, configureSet
    - Firefox
    - Edge
    - Safari
    - IE
*/