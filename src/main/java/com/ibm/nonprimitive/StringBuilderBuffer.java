package com.ibm.nonprimitive;

public class StringBuilderBuffer {
    static void main() {
        stringBuilderDemo();
        IO.println("*".repeat(40));
        stringBufferDemo();
    }

    static void stringBuilderDemo() {
        StringBuilder sbObj = new StringBuilder("Java");
        StringBuilder sbObj1 = new StringBuilder();
        IO.println(sbObj.toString());

        sbObj1.append("Selenium").append("Automation");
        IO.println(sbObj1.toString());

        sbObj1.delete(2, 4);
        IO.println(sbObj1.toString());

        sbObj1.insert(3, "Python");
        IO.println(sbObj1.toString());

//        sbObj1.repeat("Java", 10);
//        IO.println(sbObj1.toString());
//
//        sbObj1.reverse();
//        IO.println(sbObj1.toString());

        sbObj1.setCharAt(3, 'o');
        IO.println(sbObj1.toString());

    }
    static void stringBufferDemo() {
        StringBuffer sbObj = new StringBuffer("Java");
        StringBuffer sbObj1 = new StringBuffer();
        IO.println(sbObj.toString());

        sbObj1.append("Selenium").append("Automation");
        IO.println(sbObj1.toString());

        sbObj1.delete(2, 4);
        IO.println(sbObj1.toString());

        sbObj1.insert(3, "Python");
        IO.println(sbObj1.toString());

//        sbObj1.repeat("Java", 10);
//        IO.println(sbObj1.toString());
//
//        sbObj1.reverse();
//        IO.println(sbObj1.toString());

        sbObj1.setCharAt(3, 'o');
        IO.println(sbObj1.toString());

    }
}
