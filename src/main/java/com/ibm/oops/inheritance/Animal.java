package com.ibm.oops.inheritance;

public class Animal {
    String animalName;

    public Animal(String animalName) {
        this.animalName = animalName;
    }

    void run() {
        IO.println(animalName + " is running");
    }
//
//    void sound() {
//        switch (animalName) {
//            case "Dog":
//                IO.println("Dog is Barking!");
//                break;
//            case "Cat":
//                IO.println("Cat is Meawing!");
//        }
//    }
//
    void eat() {
        IO.println("Dog is eating");
    }
//
    void sleep(String name) {
        IO.println(name + " is sleeping");
    }
}
