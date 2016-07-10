package easy;

/**
 * Created by xinszhou on 16/7/5.
 */
public class TwoSum {
    public int[] twoSum(int[] nums, int target) {
        int size = nums.length;

        int[] result = new int[2];

        for (int i = 0; i < size; i++) {
            for (int j = i + 1; j < size; j++) {
                if (nums[i] + nums[j] == target) {
                    result[0] = i;
                    result[1] = j;
                    return result;
                }
            }
        }
        return result;
    }

    public static void main(String[] args) {
        int[] input = new int[4];
        input[0] = 2;
        input[1] = 7;
        input[2] = 11;
        input[3] = 15;

        int[] result = new TwoSum().twoSum(input, 9);

        System.out.println(result[0] + ", " + result[1]);

    }
}