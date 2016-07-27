package dp;

/**
 * Created by xinszhou on 16/7/27.
 * <p>
 * 考虑 [1, 5, 6, 5] 对于子序列 5, 6 假如下一个元素没有成 wiggle, 但是比前一个元素大, 那应该把较大的下一个
 * 元素加入到当前的序列中, 因为 6 对下一个元素的要求比 5 要小
 * <p>
 * 如果一个元素满足需求, 那么直接就把它加入到已有序列中
 * <p>
 * 我觉得这道题更像是贪心算法
 */
public class WiggleSubsequence {

    public int wiggleMaxLength(int[] nums) {
        int[] dp = new int[nums.length];

        dp[0] = 0;

        //current positive negative
        int currentPN = 0;
        int lastNum = dp[0];

        for (int i = 1; i < nums.length; i++) {
            dp[i] = 0;

            if (nums[i] > lastNum) {
                if (currentPN == 0) {
                    lastNum = nums[i];
                    currentPN = 1;
                    dp[i] = 2;
                } else if (currentPN > 0) {
                    lastNum = nums[i];
                    dp[i] = dp[i - 1];
                } else { //currentPN < 0
                    lastNum = nums[i];
                    currentPN = 1;
                    dp[i] = dp[i - 1] + 1;
                }
            } else if (nums[i] < lastNum) {
                if (currentPN == 0) {
                    lastNum = nums[i];
                    currentPN = -1;
                    dp[i] = 2;
                } else if (currentPN > 0) {
                    lastNum = nums[i];
                    currentPN = -1;
                    dp[i] = dp[i - 1] + 1;
                } else { //currentPN < 0
                    lastNum = nums[i];
                    dp[i] = dp[i - 1];
                }
            } else {
                dp[i] = dp[i - 1];
            }
        }
        return dp[nums.length - 1];

    }

    public static void main(String[] args) {
        int[] nums1 = {1,7,4,9,2,5};
        System.out.println("seq1: " + new WiggleSubsequence().wiggleMaxLength(nums1));

        int[] nums2 = {1,17,5,10,13,15,10,5,16,8};
        System.out.println("seq2: " + new WiggleSubsequence().wiggleMaxLength(nums2));

        int[] nums3 = {1,2,3,4,5,6,7,8,9};
        System.out.println("seq3: " + new WiggleSubsequence().wiggleMaxLength(nums3));

    }
}
