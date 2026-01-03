package com.ibm;

public class DemoVariable {
    static void main() {
        int a = 10;
        String str = "Java";
        int age = 10;

//        -128 to 127
//        byte b = 127;

        int a1 =30, b, c = 10;
//        int s = 20, String s1 = "Python"; //not allowed
        int[] arr = new int[5];
        int []arr1;
        int arr2[];

        double d = 20.30;
        System.out.println(a);
        long a2 = a; // widening - auto-boxing
        System.out.println(a2);

        int x = (int) d; // narrowing - explicit type conversion
        System.out.println(d);
        System.out.println(x);
    }
}

//added a comment


// length of variable: 4-16char recommendate(meaningful name)
// Wrapper class

