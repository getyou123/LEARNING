//如果连续数字之间的差严格地在正数和负数之间交替，则数字序列称为 摆动序列 。第一个差（如果存在的话）可能是正数或负数。仅有一个元素或者含两个不等元素的序列也
//视作摆动序列。 
//
// 
// 例如， [1, 7, 4, 9, 2, 5] 是一个 摆动序列 ，因为差值 (6, -3, 5, -7, 3) 是正负交替出现的。 
// 相反，[1, 4, 7, 2, 5] 和 [1, 7, 4, 5, 5] 不是摆动序列，第一个序列是因为它的前两个差值都是正数，第二个序列是因为它的最后一
//个差值为零。 
// 
//
// 子序列 可以通过从原始序列中删除一些（也可以不删除）元素来获得，剩下的元素保持其原始顺序。 
//
// 给你一个整数数组 nums ，返回 nums 中作为 摆动序列 的 最长子序列的长度 。 
//
// 
//
// 示例 1： 
//
// 
//输入：nums = [1,7,4,9,2,5]
//输出：6
//解释：整个序列均为摆动序列，各元素之间的差值为 (6, -3, 5, -7, 3) 。
// 
//
// 示例 2： 
//
// 
//输入：nums = [1,17,5,10,13,15,10,5,16,8]
//输出：7
//解释：这个序列包含几个长度为 7 摆动序列。
//其中一个是 [1, 17, 10, 13, 10, 16, 8] ，各元素之间的差值为 (16, -7, 3, -3, 6, -8) 。
// 
//
// 示例 3： 
//
// 
//输入：nums = [1,2,3,4,5,6,7,8,9]
//输出：2
// 
//
// 
//
// 提示： 
//
// 
// 1 <= nums.length <= 1000 
// 0 <= nums[i] <= 1000 
// 
//
// 
//
// 进阶：你能否用 O(n) 时间复杂度完成此题? 
//
// Related Topics 贪心 数组 动态规划 👍 970 👎 0

package leetcode.editor.cn;

import java.util.LinkedList;

//Java：摆动序列
public class P376WiggleSubsequence{
    public static void main(String[] args) {
        Solution solution = new P376WiggleSubsequence().new Solution();
        int[] arr= new int[]{33,53,12,64,50,41,45,21,97,35,47,92,39,0,93,55,40,46,69,42,6,95,51,68,72,9,32,84,34,64,6,2,26,98,3,43,30,60,3,68,82,9,97,19,27,98,99,4,30,96,37,9,78,43,64,4,65,30,84,90,87,64,18,50,60,1,40,32,48,50,76,100,57,29,63,53,46,57,93,98,42,80,82,9,41,55,69,84,82,79,30,79,18,97,67,23,52,38,74,15};
        System.out.println(solution.wiggleMaxLength(arr));
        // TO TEST
    }
    //leetcode submit region begin(Prohibit modification and deletion)
    class Solution {
        public int wiggleMaxLength(int[] nums) {
            if(nums.length==1) return 1;
            if(nums.length==2){
                if(nums[1]!=nums[0]) return 2;
                else return 1;
            }

            LinkedList<Integer> Lk = new LinkedList<Integer>();
            for(int i =0;i<nums.length;i++){
                if(Lk.size()==0){
                    Lk.add(nums[i]);
                } else if(Lk.size()==1){
                    if(Lk.getLast()!=nums[i]){
                        Lk.add(nums[i]);
                    }
                } else {
                    if((nums[i]-Lk.getLast()) * (Lk.getLast()-Lk.get(Lk.size()-2))<0) {
                        Lk.add(nums[i]);
                    }else{
                        Lk.removeLast();
                        Lk.add(nums[i]);
                    }
                }
            }
            return Lk.size();
        }
    }
//leetcode submit region end(Prohibit modification and deletion)

}
