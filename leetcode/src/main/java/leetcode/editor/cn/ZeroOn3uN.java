//给定一个只包含数字的字符串 s ，用以表示一个 IP 地址，返回所有可能从 s 获得的 有效 IP 地址 。你可以按任何顺序返回答案。 
//
// 有效 IP 地址 正好由四个整数（每个整数位于 0 到 255 之间组成，且不能含有前导 0），整数之间用 '.' 分隔。 
//
// 例如："0.1.2.201" 和 "192.168.1.1" 是 有效 IP 地址，但是 "0.011.255.245"、"192.168.1.312" 
//和 "192.168@1.1" 是 无效 IP 地址。 
//
// 
//
// 示例 1： 
//
// 
//输入：s = "25525511135"
//输出：["255.255.11.135","255.255.111.35"]
// 
//
// 示例 2： 
//
// 
//输入：s = "0000"
//输出：["0.0.0.0"]
// 
//
// 示例 3： 
//
// 
//输入：s = "1111"
//输出：["1.1.1.1"]
// 
//
// 示例 4： 
//
// 
//输入：s = "010010"
//输出：["0.10.0.10","0.100.1.0"]
// 
//
// 示例 5： 
//
// 
//输入：s = "10203040"
//输出：["10.20.30.40","102.0.30.40","10.203.0.40"]
// 
//
// 
//
// 提示： 
//
// 
// 0 <= s.length <= 3000 
// s 仅由数字组成 
// 
//
// 
//
// 
// 注意：本题与主站 93 题相同：https://leetcode-cn.com/problems/restore-ip-addresses/ 
//
// Related Topics 字符串 回溯 👍 52 👎 0

package leetcode.editor.cn;
//Java：复原 IP 

import java.util.LinkedList;
import java.util.List;

public class ZeroOn3uN {
    public static void main(String[] args) {
        Solution solution = new ZeroOn3uN().new Solution();

        List<String> strings = solution.restoreIpAddresses("010010");
        System.out.println(strings);

        // TO TEST
    }

    //leetcode submit region begin(Prohibit modification and deletion)
    class Solution {
        public void trace(String s, String building, List<String> res, int dep) {

            if (dep == 5) {
                if (s.length() == 0) {
                    res.add(building.substring(1));
                }
                return;
            }

            for (int i = 1; i <= Math.min(3, s.length()); i++) {
                String tmpStr = s.substring(0, i);
                Integer tmpInt = Integer.valueOf(tmpStr);
                if (tmpInt >= 0 && tmpInt <= 255 && tmpInt.toString().equals(tmpStr)) {
                    String newBuilding = building.concat(".".concat(tmpInt.toString()));
                    trace(s.substring(i), newBuilding, res, dep + 1);
                }
            }
        }

        public List<String> restoreIpAddresses(String s) {
            List<String> res = new LinkedList<String>();
            trace(s, "", res, 1);
            return res;
        }
    }
//leetcode submit region end(Prohibit modification and deletion)
}
