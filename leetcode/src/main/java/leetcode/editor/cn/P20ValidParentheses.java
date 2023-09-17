//给定一个只包括 '('，')'，'{'，'}'，'['，']' 的字符串 s ，判断字符串是否有效。 
//
// 有效字符串需满足： 
//
// 
// 左括号必须用相同类型的右括号闭合。 
// 左括号必须以正确的顺序闭合。 
// 每个右括号都有一个对应的相同类型的左括号。 
// 
//
// 
//
// 示例 1： 
//
// 
//输入：s = "()"
//输出：true
// 
//
// 示例 2： 
//
// 
//输入：s = "()[]{}"
//输出：true
// 
//
// 示例 3： 
//
// 
//输入：s = "(]"
//输出：false
// 
//
// 
//
// 提示： 
//
// 
// 1 <= s.length <= 10⁴ 
// s 仅由括号 '()[]{}' 组成 
// 
//
// Related Topics 栈 字符串 👍 4001 👎 0

package leetcode.editor.cn;

import java.util.LinkedList;

//Java：有效的括号
public class P20ValidParentheses {
    public static void main(String[] args) {
        Solution solution = new P20ValidParentheses().new Solution();
        // TO TEST
    }

    //leetcode submit region begin(Prohibit modification and deletion)
    class Solution {
        public boolean isValid(String s) {
            LinkedList<Character> stack = new LinkedList<Character>();
            for (int i = 0; i < s.length(); i++) {
                if (s.charAt(i) == '(' || s.charAt(i) == '{' || s.charAt(i) == '[') {
                    stack.addFirst(s.charAt(i));
                } else if (
                        stack.size() > 0 &&
                                ((s.charAt(i) == ')' && stack.getFirst() == '(')
                                        || (s.charAt(i) == '}' && stack.getFirst() == '{')
                                        || (s.charAt(i) == ']' && stack.getFirst() == '['))) {
                    stack.removeFirst();
                } else {
                    return false;
                }
            }
            if (stack.size() == 0) {
                return true;
            } else return false;
        }
    }
//leetcode submit region end(Prohibit modification and deletion)

}
