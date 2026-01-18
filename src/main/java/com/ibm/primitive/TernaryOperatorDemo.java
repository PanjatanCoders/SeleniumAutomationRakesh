void main() {
    IO.println(display(-10));
}

String display(int age) {
//    if (age > 18) {
//        return "You are eligible for vote";
//    } else {
//       return  "You are not eligible for vote";
//    }
//    String msg = age > 18 ? "You are eligible for vote": "You are not eligible for vote";
//    return msg;

    return  age > 18 ? "You are eligible for vote" : age  < 0 ?
            "Invalid output" :"You are not eligible for vote";
}