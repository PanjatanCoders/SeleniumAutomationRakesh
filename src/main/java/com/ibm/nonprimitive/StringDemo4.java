package com.ibm.nonprimitive;

public class StringDemo4 {
    static void main() {
        String str = "Welcome to Java Programming Language.";

        String[] strArr = str.split(" ");
        String finalString = "";
        for (String s : strArr) {
            finalString = String.join("-", s);
        }

        String s1 = "Java";
        String s2 = "Selenium";

        String s3 = "";
        s3 = String.join("-", strArr);

        IO.println(s3);
    }
}
