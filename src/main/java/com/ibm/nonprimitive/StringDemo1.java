void main() {
//    stringBasic();
    stringOperations();
}

void stringOperations() {
    String a = "Java Selenium Automation, 721234";
//    length
//    IO.println(a.length());
//    IO.println(a.repeat(3)); //reat a string for n number of times
//    IO.println(a.toLowerCase());
//    IO.println(a.toUpperCase());
//    IO.println(a.charAt(0));
//    IO.println(a.charAt(2));
//    IO.println(a.substring(0, 6));
//    IO.println(a.substring(5));
//    IO.println(a.substring(a.length()-6));
//    IO.println(a.concat("- India"));
//    IO.println(a.startsWith("Java"));
//    IO.println(a.endsWith("721234"));

//    IO.println(a.indexOf('S')); //by default first index of the char
//    IO.println(a.indexOf("Auto"));
//    IO.println(a.indexOf('a'));
//    IO.println(a.lastIndexOf('a'));

    a = a.replace("Java", "Python");
    IO.println(a.replace("Java", "Python"));
    IO.println(a);

}

void stringBasic() {
    String a = "hello"; // literal way --> SCP(String Constant Pool)
    String b = "hello";
    String c = new String("hello"); //interned/object
    String d = a;
    String e = "JAVA";

//    IO.println(a);
//    IO.println(b);
//    IO.println(c);
//    IO.println(d);

//    verify object:
//    IO.println("a == b: " + (a == b));
//    IO.println("a == c: " + (a == c));
//    IO.println("a == d: " + (a == d));

//verify content
    IO.println(a.equals(b));
    IO.println(a.equals(c));
    IO.println(a.equals(d));
    IO.println(a.equals(e));
    IO.println(a.equalsIgnoreCase(d));
}