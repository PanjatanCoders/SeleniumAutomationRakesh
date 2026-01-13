void main() {
    IO.println("*".repeat(40));
//    printGrade(75);
    printEvenOdd(17);
}

//when you have only 1 statement
//you have to maintain the indentation
void printEvenOdd(int num) {
    if (num % 2 == 0)
        IO.println(num + " is Even number");
    else
        IO.println(num + " is Odd number");
}

void printGrade(int score) {
    IO.println("Finding grade for " + score);
    IO.println("Calculating grade");

    if (score >= 90) {
        IO.println("Inside first condition");
        double c = score * 1.05; // o/p -> double -> int
        System.out.println("Grade A");
        if (score >= 90) {
            IO.println("Inside second condition");
        }
    } else if (score >= 80)
{
        IO.println("Inside 2nd condition");
        System.out.println("Grade B");
    } else if (score >= 70) {
        IO.println("Inside 3rd" +
                " condition");
        System.out.println("Grade C");
    } else {
        System.out.println("Grade F");
    }
}