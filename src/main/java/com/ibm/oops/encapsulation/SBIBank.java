package com.ibm.oops.encapsulation;

public class SBIBank {
    final String name = "SBIBank";
    private double balance;
    private String uname;
    private String email;

    public String getName() {
        return name;
    }

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
//    public SBIBank() {}

//    Constructor
//    public SBIBank(double balance, String uname, String email) {
//        this.balance = balance;
//        this.uname = uname;
//        this.email = email;
//    }

    void displayBanalce() {}

    public double getBalance() {
        return this.balance;
    }
//
//    public void setBalance(double balance) {
//        //....
//        this.balance = balance;
//    }
}

//
//Constructor:
//class A{
//    void display() {
//        SBIBank obj = new SBIBank();
//        obj.balance = 100;
//    }
//}
// Access specifier: public, protected, private, default
// getter/setter