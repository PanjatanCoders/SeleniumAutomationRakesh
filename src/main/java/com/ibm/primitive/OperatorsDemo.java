package com.ibm.primitive;

public class OperatorsDemo {
    static void main() {
        int a = 10;
        int b = 4;
//        IO.println(a/b);
////        IO.println(a//b);
//
//        double d = a/b;
//        System.out.println(d);
//        double d1 = a;
//        double d2 = b;
//        double c = d1/d2;
//        System.out.println(c);
//
//        Integer a1 = 10;
//        Integer b1 = 4;
//        System.out.println(a1/b1);

        System.out.println(a);
//        System.out.println(a++);
//        System.out.println(a);

//        System.out.println(--a); // output: 9
//        System.out.println(a); //output 9

        System.out.println(a += 1); // same as: a = a+1
        System.out.println(a);

//        truthy: 0, "", empty list --> in java we dont have concept.
//        true/false
        String s = "TRUE";
        boolean bool = Boolean.valueOf(s);
        System.out.println(bool);
        boolean flag = true;
    }

}
