////给定一个不含重复数字的数组 nums ，返回其 所有可能的全排列 。你可以 按任意顺序 返回答案。
////
////
////
//// 示例 1：
////
////
////输入：nums = [1,2,3]
////输出：[[1,2,3],[1,3,2],[2,1,3],[2,3,1],[3,1,2],[3,2,1]]
////
////
//// 示例 2：
////
////
////输入：nums = [0,1]
////输出：[[0,1],[1,0]]
////
////
//// 示例 3：
////
////
////输入：nums = [1]
////输出：[[1]]
////
////
////
////
//// 提示：
////
////
//// 1 <= nums.length <= 6
//// -10 <= nums[i] <= 10
//// nums 中的所有整数 互不相同
////
////
//// Related Topics 数组 回溯 👍 2600 👎 0
//
//package leetcode.editor.cn;
//
//import java.util.LinkedList;
//import java.util.List;
//
////Java：全排列
//public class P46Permutations{
//    public static void main(String[] args) {
//        Solution solution = new P46Permutations().new Solution();
//        // TO TEST
//    }
//    //leetcode submit region begin(Prohibit modification and deletion)
//class Solution {
//        public static List<List<Integer>> res = new LinkedList<>();
//
//        public static void backtracking(int numsLength,LinkedList<Integer> pre,LinkedList<Integer> aRes){
//            if(aRes.size()==numsLength){
//                LinkedList<Integer> tmp = new LinkedList<Integer>();
//                for(int i = 0;i<aRes.size();i++){
//                    tmp.add(aRes.get(i));
//                }
//                res.add(tmp);
//                return ;
//            }
//
//            for(int i = 0;i<pre.size();i++){
//                int r = pre.get(i);
//                aRes.addLast(r);
//
//                pre.remove(i);
//
//                backtracking(numsLength,pre,aRes);
//
//                pre.add(i,r);
//
//
//                aRes.removeLast();
//
//
//            }
//
//
//
//        }
//
//        public List<List<Integer>> permute(int[] nums) {
//
//            res = new LinkedList<>();
//
//            LinkedList<Integer> tmp = new LinkedList<Integer>();
//            for(int i = 0;i<nums.length;i++){
//                tmp.add(nums[i]);
//            }
//            backtracking(nums.length,tmp,new LinkedList<Integer>());
//
//            return res;
//
//
//        }
//}
////leetcode submit region end(Prohibit modification and deletion)
//
//}
