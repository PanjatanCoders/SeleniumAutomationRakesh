void main() {
//    declaration
    int[] nums;
    int num1[];
    int []num2;
    String[] names;

//    Initialization
//    Empty array
    int[] arr3 = new int[10]; // it means we can store only 10 int type data
    String [] arr4 = new String[10];
    String[] arr5 = new String[10]; //10 : number of elements/size of the array
    int count = 200;
//    size: 1000 -> 10000
    nums = new int[count];

    IO.println(arr3.length);
    IO.println(arr3[0]);
    IO.println(arr3[1]);
    IO.println(arr3[2]);
    IO.println("*".repeat(40));

    IO.println(arr4[0]);
    IO.println(arr4[1]);
//    1, 2, 3, 44, 5,3
    int[] numbes = {1, 2, 3, 44, 5, 3};
    String[] cities = {
            "Pune", "Mumbai", "Delhi"
    };
}