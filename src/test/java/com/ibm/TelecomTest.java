package com.ibm;

import com.ibm.oops.interfacedemo.Airtel;
import com.ibm.oops.interfacedemo.TRAI;
import com.ibm.oops.interfacedemo.Voda;

public class TelecomTest {
    static void main() {
        TRAI obj = new Voda();

        obj.call();
        obj.message();
        obj.calculateGst();
    }
}
// WebDriver driver = new ChromeDriver();