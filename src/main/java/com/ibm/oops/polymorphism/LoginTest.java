package com.ibm.oops.polymorphism;

public class LoginTest {
    static void main() {
        Login login = new Login();

        login.login();
        login.login(12222);
        login.login("admin", "password");
    }
}
