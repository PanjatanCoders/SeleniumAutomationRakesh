void main() {
    String fruites[] = {"Apple", "Banana", "Guava", "Orange"};
    int[] nums = {23, 12, 32, 30};
    int[] nums1 = new int[10];
//    IO.println(fruites);
//    IO.println(nums);

    IO.println("*".repeat(40));
//    IO.println(Arrays.toString(fruites));
//    IO.println(Arrays.toString(nums));

//    Operations on an Array
    IO.println(Arrays.toString(nums1));
    nums1[3] = 30; //assign/modify data to an index
    IO.println(Arrays.toString(nums1));

//    nums1[10] = 5; //ArrayIndexOutOfboundsException will occur
    nums1[9] = 5;
    IO.println(Arrays.toString(nums1));

//    Deletion of an element is not allowed.


}