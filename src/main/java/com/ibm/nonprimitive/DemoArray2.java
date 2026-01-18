void main() {
    int[] nums = {1, 2, 3, 4, 5, 6, 7, 8, 9};
    String[] cities = {"Pune", "Delhi", "Kolkata"};

    int size = nums.length;
//    IO.println(nums[size - 1]);
//    for (int i = 0; i < size; i++) {
//        IO.println("Data at index: " + i +" is:" + nums[i]);
//    }

//    IO.println(nums[10]);
//    Enhanced/Advanced for loop
//    for(int num : nums) {
//        IO.println(num);
//    }
    for (String city : cities) {
        IO.println(city);
    }

//    Multi-Dimensional Array
    int[][] multiNums = {
            {1, 2, 3, 10},
            {4, 5, 6, 11, 20, 30},
            {11, 12, 13, 22}
    };
    IO.println(multiNums.length); //3
    IO.println(multiNums[0].length); //4
    IO.println(multiNums[1].length); //6
    IO.println(multiNums[1][4]); // 20
    IO.println(Integer.MIN_VALUE);
    IO.println(Integer.MAX_VALUE);
}