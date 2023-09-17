//给定一个二进制数组 nums ， 计算其中最大连续 1 的个数。 
//
// 
//
// 示例 1： 
//
// 
//输入：nums = [1,1,0,1,1,1]
//输出：3
//解释：开头的两位和最后的三位都是连续 1 ，所以最大连续 1 的个数是 3.
// 
//
// 示例 2: 
//
// 
//输入：nums = [1,0,1,1,0,1]
//输出：2
// 
//
// 
//
// 提示： 
//
// 
// 1 <= nums.length <= 10⁵ 
// nums[i] 不是 0 就是 1. 
// 
//
// Related Topics 数组 👍 395 👎 0

package leetcode.editor.cn;

//Java：最大连续 1 的个数
public class P485MaxConsecutiveOnes {
    public static void main(String[] args) {
        Solution solution = new P485MaxConsecutiveOnes().new Solution();
        // TO TEST
    }

    //leetcode submit region begin(Prohibit modification and deletion)
    class Solution {
        public int findMaxConsecutiveOnes(int[] nums) {
            int i = 0;
            int j = 0;
            int res = 0;
            while (j < nums.length) {
                while (nums[i] != 1) {
                    i++;
                    if(i>=nums.length) return res;
                }
                j = i;
                while (j < nums.length && nums[j] == 1) {
                    j++;
                }
                if (j == nums.length || nums[j] != 1) {
                    res = Math.max(res, j - i);
                    i = j;
                }
            }

            return res;

        }
    }
//leetcode submit region end(Prohibit modification and deletion)

}
