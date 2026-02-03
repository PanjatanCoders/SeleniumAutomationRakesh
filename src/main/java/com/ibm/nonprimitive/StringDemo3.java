package com.ibm.nonprimitive;

import java.util.Arrays;

public class StringDemo3 {
    static void main() {
        String str = "Welcome to Java Programming Language. we will learn Java Selenium";

//        IO.println(str.strip());
//        IO.println(str.trim());
        IO.println("*".repeat(40));

//        IO.println(str.split());
        String[] arr = str.split(" ");
        IO.println(Arrays.toString(arr));

//        String[] arr1 = str.split(" ", 3);
//        IO.println(Arrays.toString(arr1));


//        Write a program to reverse the above words from the String text
        int len = arr.length;
        String[] arr2 = new String[len];


        for (int i = len - 1; i >= 0; i--) { // i 9, - 0
            arr2[len - i - 1] = arr[i];
        }

        IO.println(Arrays.toString(arr2));
    }
}

// length = 10
//







//a string whose value is this string, with all leading and trailing space removed,
// or this string if it has no leading or trailing space.

//a string whose value is this string, with all leading and trailing white space remove