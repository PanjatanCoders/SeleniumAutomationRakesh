void main() {
    myloop: for (int i = 1; i <= 10; i++) {
        innerloop: for (int j = 1; j <= 10; j++) {
            IO.println(i + " -> " + j);
//            when value of i & j is equals get out of the loop
            if (i == j)
                break myloop;

            if (j > i)
                break innerloop;
        }
    }
}