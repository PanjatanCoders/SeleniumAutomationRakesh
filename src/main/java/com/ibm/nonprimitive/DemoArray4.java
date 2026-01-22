void main() {
    int[] nums = {23, 12, 32, 30};
//    copy the array into an new array

//    int[] newNums = nums;
//    IO.println("Nums: " + Arrays.toString(nums));
//    IO.println("NewNums: "  + Arrays.toString(newNums));
//
//    newNums[2] = 46;
//    IO.println("Nums: " + Arrays.toString(newNums));
//    IO.println("NewNums: "  + Arrays.toString(newNums));
    int[] nums1 = nums.clone();

    nums1[2] = 23;
    nums1[0] = 213;
//        IO.println("Nums: " + Arrays.toString(nums));
//    IO.println("Nums1: "  + Arrays.toString(nums1));

//    IO.println(nums.equals(newNums));
//    IO.println(Arrays.equals(nums,  nums1));
//    int []num3 = Arrays.copyOfRange(nums,0,2);
//    IO.println(Arrays.toString(num3));

//    Arrays.sort(nums);
    int []arr1 = new  int[nums.length];
    for (int i = 0; i < nums.length; i++) {
        arr1[i] = nums[i]*2;
    }
    IO.println(Arrays.toString(arr1));
    IO.println(Arrays.compare(nums, nums1));
    IO.println(Arrays.compare(nums, arr1));
}