//数字 n 代表生成括号的对数，请你设计一个函数，用于能够生成所有可能的并且 有效的 括号组合。 
//
// 
//
// 示例 1： 
//
// 
//输入：n = 3
//输出：["((()))","(()())","(())()","()(())","()()()"]
// 
//
// 示例 2： 
//
// 
//输入：n = 1
//输出：["()"]
// 
//
// 
//
// 提示： 
//
// 
// 1 <= n <= 8 
// 
//
// Related Topics 字符串 动态规划 回溯 👍 3284 👎 0

package leetcode.editor.cn;

import java.util.LinkedList;
import java.util.List;

//Java：括号生成
public class P22GenerateParentheses{
    public static void main(String[] args) {
        Solution solution = new P22GenerateParentheses().new Solution();
        // TO TEST
    }
    //leetcode submit region begin(Prohibit modification and deletion)
    class Solution {

        public void backtracking(LinkedList<String> res, String s, int n, int lc, int rc){

            if(s.length()==2*n && lc==n && rc==n){
                res.add(s);
            }

            if(lc<n){
                backtracking(res,s+'(',n,lc+1,rc);
            }

            if(rc<n&& rc<lc){
                backtracking(res,s+')',n,lc,rc+1);
            }

        }



        public List<String> generateParenthesis(int n) {

            LinkedList<String> res = new LinkedList<String>();

            backtracking(res,"",n,0,0);

            return res;



        }
    }
//leetcode submit region end(Prohibit modification and deletion)

}
