package com.ibm.collection;

import java.util.ArrayList;
import java.util.Iterator;

public class ArrayListDemo {
    static void main() {
//        demo1();
//        demo2();
        demo3();
    }

    static void demo3() {
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("Java");
        arrayList.add("Selenium");
        arrayList.add("Python");
        arrayList.add("Python"); // index - 3
        arrayList.add("Perl");

        System.out.println(arrayList.subList(1, 3));
        System.out.println(arrayList.lastIndexOf("Python"));
        System.out.println(arrayList.toArray()[0]);
        arrayList.addFirst("TestNG");
        System.out.println(arrayList);
        System.out.println(arrayList.contains("TestNG"));
    }
    static void demo2 () {
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("Java");
        arrayList.add("Selenium");
        arrayList.add("Python");
        arrayList.add("Python");
        arrayList.add("Perl");

//        for (int i = 0; i < arrayList.size(); i++) {
//            System.out.println(arrayList.get(i));
//        }

//        for (String data : arrayList) {
//            System.out.println(data);
//        }

//        Traversing using Iterator
//        Iterator itr = arrayList.iterator();
//        while (itr.hasNext()) {
//            System.out.println(itr.next());
//        }

//        arrayList.forEach(System.out::println);

    }


    static void demo1() {
        String str;
        ArrayList<String> arrayList = new ArrayList<>();
//        ArrayList<Integer> arrayList1 = new ArrayList<>();

        System.out.println(arrayList.size());
        arrayList.add("Java");
        arrayList.add("Selenium");
        arrayList.add("Python");
        System.out.println(arrayList);
        System.out.println(arrayList.size());
        System.out.println(arrayList.get(0));
        arrayList.add(1, "Selenium WebDriver"); // insert into an index
        System.out.println(arrayList.get(0));
        arrayList.set(0, "Perl"); // update into an index
        System.out.println(arrayList);
        arrayList.remove("Perl"); //[Selenium WebDriver, Selenium, Python]
        arrayList.remove(1); // [Selenium WebDriver, Python]
        arrayList.add("Python");
        System.out.println(arrayList);

    }}
//Object

//insert/update/delete/fetch
// ArrayList Characteristics:
// - maintain the insertion order