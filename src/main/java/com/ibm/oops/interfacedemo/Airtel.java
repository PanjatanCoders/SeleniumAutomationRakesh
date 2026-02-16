package com.ibm.oops.interfacedemo;

public class Airtel implements TRAI{
    @Override
    public void call() {
        System.out.println("Airtel call");
    }

    @Override
    public void message() {
        System.out.println("Airtel message");
    }

    @Override
    public void calculateGst() {

    }
}
