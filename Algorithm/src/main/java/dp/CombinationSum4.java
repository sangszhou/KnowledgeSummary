package dp;

import java.util.Arrays;

/**
 * Created by xinszhou on 16/7/26.
 * <p>
 * f(a,b) 表示 array[a:size] 能够拼成 b 的个数
 * f(a,b) = f(a,b-array[a]) + f(a+1,b-array[a]) + f(a+1, b)
 * <p>
 * 注意初始化和什么时候样的结果有效
 */
public class CombinationSum4 {


    //solution1 2016年07月26日 星期二
    public int combinationSum41(int[] nums, int target) {

        int[][] matrix = new int[nums.length + 1][target + 1];

        // 初始化
        for (int i = nums.length; i >= 0; i--)
            matrix[i][0] = 1;

        for (int j = 1; j <= target; j++)
            matrix[nums.length][j] = 0;

        for (int i = nums.length - 1; i >= 0; i--) {
            for (int j = 1; j <= target; j++) {

                matrix[i][j] = 0;

                int times = j / nums[i];

                for (int idx = 0; idx <= times; idx++) {
                    matrix[i][j] += matrix[i + 1][j - nums[i] * idx];
                }

                System.out.format("matrix[%d][%d] = %d   ", i, j, matrix[i][j]);
            }
            System.out.println();
        }

        return matrix[0][target];
    }

    // Solution 2, according to discussion and ClimbStairs
    // which is very cool
    public int combinationSum4(int[] nums, int target) {
        int[] dp = new int[target + 1];

        dp[0] = 1;

        for (int i = 1; i <= target; i++) {
            dp[i] = 0;

            for (int num : nums) {
                if(i >= num)
                    dp[i] += dp[i - num];
            }
        }
        return dp[target];
    }


    public static void main(String[] args) {
        int[] numbers = {1, 2, 3};
        int target = 4;

        int result = new CombinationSum4().combinationSum4(numbers, target);
        System.out.println("result: " + result);
    }
}
