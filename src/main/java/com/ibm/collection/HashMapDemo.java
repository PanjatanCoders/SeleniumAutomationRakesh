package com.ibm.collection;

import java.util.*;

public class HashMapDemo {
    static void main() {
        HashMap<String, String> hs = new HashMap<>();
        hs.put("a1", "1000");
        hs.put("a2", "2000");
        hs.put("a3", "3000");
        hs.put("a3", "5000"); //it will override the existing data

//        hasMapDemo1(hs);
        hasMapDemo2(hs);
    }

    static void hasMapDemo2(HashMap<String, String> hs) {
//        Traversing : print all the key, vales from the map
//        Approach 1:
//        1. First get the keys from the map(it will be a Set of keys)
//        2. Use for loop for the Set of Keys and fetch the values from the map
//        Set<String> myKeys = hs.keySet();
//
//        for (String myKey : myKeys) {
//            System.out.println(myKey + " : " + hs.get(myKey));
//        }

//  Approach 2:
        for (Map.Entry<String, String> myEntry : hs.entrySet()) {
            System.out.println(myEntry.getKey() + " => " + myEntry.getValue());
        }

    }

    static void hasMapDemo1(HashMap<String, String> hashMap) {
        System.out.println(hashMap);
        System.out.println(hashMap.size());
        System.out.println(hashMap.get("a2"));

        Set ar = hashMap.keySet();
        System.out.println(hashMap.keySet());
        System.out.println(hashMap.values());

        hashMap.putIfAbsent("a1", "1500");
        hashMap.putIfAbsent("a4", "1500");
        System.out.println(hashMap);

        System.out.println(hashMap.get("a5"));
        System.out.println(hashMap.getOrDefault("a6", "100"));
    }
}

// Map duplicate key is not allowed
// There is no check on values
// Reflection
// browser: chrome/firefox/edge