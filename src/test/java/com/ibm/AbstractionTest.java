package com.ibm;

import com.ibm.oops.abstraction.Circle;
import com.ibm.oops.abstraction.Rectangle;

public class AbstractionTest {
    static void main() {
        Circle circle = new Circle();

        circle.displayName("Circle");

        circle.displayArea();

        IO.println("*".repeat(40));

        Rectangle rectangle = new Rectangle();
        rectangle.displayName("Rectangle");
        rectangle.displayArea();
    }
}

