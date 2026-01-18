
void main() {
    IO.println("Demo of Loop");
//    demoForLoop();
    demoForLoop1();
//    demoWhileLoop();
//    doWhileLoop();
}

void demoForLoop() {
//    Syntax
//    for (int i = 1; i <= 10; i++) {}
//    Print numbers form 0 to 10
    for (int i = 0; i <= 10; i++) {
        IO.println(i);
    }
}

void demoForLoop1() {
    IO.println("Inside demoForLoop1");
    int i = 0;
//    for ( ; i <= 10; i++) {
//        IO.println(i);
//    }
//    for ( ; i <= 10; ) {
//        IO.println(i);
//        i++;
//    }

    for ( ; ; ) {
        IO.println(i);
        i++;
        if (i == 10) {
            break;
        }
//        i++;
    }

}


void demoWhileLoop() {
    //    Print numbers form 0 to 10
//    while (condition) {}  --> Syntax
    int i = 0;
    while (i <= 10) {
        IO.println(i);
        i ++;
    }
}

void doWhileLoop() {
    int i = 0;
    do {
        IO.println(i);
        i++;
    } while (i <= 10);

    IO.println("Outside of do-while loop");
}