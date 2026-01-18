void main() {
    for (int i = 1; i <= 10; i++) {
//        skip if value is 5
        if (i == 5)
            continue;  //to skip the current iteration
//        stop the iteration if data is 7
        if (i == 7)
            break;
        IO.println(i);

    }
}