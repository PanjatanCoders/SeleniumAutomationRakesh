package com.ibm.collection;

import java.util.HashSet;
import java.util.Iterator;

public class HasSetDemo {
    static void main() {
        HashSet<String> hashSet = new HashSet<>();

        hashSet.add("TestNG");
        hashSet.add("TestNG");
        hashSet.add("Java");
        hashSet.add("Python");
        hashSet.add("Cypress");
        hashSet.add("JavaScript");

        hashSetDemo1(hashSet);
    }

    static void hashSetDemo1(HashSet<String> hSet) {
        System.out.println(hSet);
//        traversing the set
//        for (String s : hSet) {
//            System.out.println(s);
//        }
        Iterator itr = hSet.iterator();
        while (itr.hasNext()) {
            System.out.println(itr.next());
        }

    }
}



// Set: not allowing the duplicate elements

// Array: - index DS
// ArrayList : indexed DS
// Set : not indexed based
// Map: no index concept(HashMap)
