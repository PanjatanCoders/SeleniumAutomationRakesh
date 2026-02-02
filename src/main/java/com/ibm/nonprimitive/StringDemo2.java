package com.ibm.nonprimitive;

public class StringDemo2 {
    static void main() {
//        String str = "Language Java Selenium";

//        String s1 = str.substring(3); // a Selenium
//        String s2 = str.substring(3, 20); // a S

//        IO.println("*".repeat(40));
//        IO.println(str);
//        IO.println(s1);
//        IO.println(s2);
//
////        fetch Java from the above text
        IO.println("*".repeat(40));
//        IO.println(
//                str.substring(str.indexOf("Java"), str.indexOf("Java")+ 4)
//        );

//        System.out.println(str.replace("Java", "Python"));
//        System.out.println(str);
//        IO.println(
//                str.replace('a', 'o')
//        );
////        IO.println(
////                str.replaceAll('a', 'o');
////        );
////
//        IO.println(
//                str.replaceAll("a", "u")
//        );
//        IO.println(
//                str.replaceFirst("n", "p")
//        );

        String str = "Welcome to Java Programming Language. we will learn Java Selenium";
//        IO.println(
//                str.lastIndexOf('a')
//        );
        IO.println(str.indexOf("to"));
//
        IO.println(str.startsWith("to", 8));
        IO.println(
                str.contains("Language")
        );
        IO.println(
                str.endsWith("Language") //false
        );
    }
}
// week - 2 classes/3hrs
// 8 classes - manual/non tech - 40K-50K/-
// Java - Selenium Frame
// trainer: xpath -
// Selectors hub

//Remaining of String
// StringBuffer and StringBuilder