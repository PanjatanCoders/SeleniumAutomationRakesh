package com.ibm.oops.polymorphism;

public class Login {
    void login() {
        System.out.println("login");
    }

    void login(int otp) {
        System.out.println("login with otp: " + otp);
    }

    void login(String username, String password) {
        System.out.println("login with username: " + username + " and password: " + password);
    }

    void login(String secretCode) {
        System.out.println("login with secret code: " + secretCode);
    }
}
