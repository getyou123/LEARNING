//给定两个字符串 s 和 t ，编写一个函数来判断 t 是否是 s 的字母异位词。 
//
// 注意：若 s 和 t 中每个字符出现的次数都相同，则称 s 和 t 互为字母异位词。 
//
// 
//
// 示例 1: 
//
// 
//输入: s = "anagram", t = "nagaram"
//输出: true
// 
//
// 示例 2: 
//
// 
//输入: s = "rat", t = "car"
//输出: false 
//
// 
//
// 提示: 
//
// 
// 1 <= s.length, t.length <= 5 * 10⁴ 
// s 和 t 仅包含小写字母 
// 
//
// 
//
// 进阶: 如果输入字符串包含 unicode 字符怎么办？你能否调整你的解法来应对这种情况？ 
//
// Related Topics 哈希表 字符串 排序 👍 799 👎 0

package leetcode.editor.cn;

import java.util.HashMap;

//Java：有效的字母异位词
public class P242ValidAnagram {
    public static void main(String[] args) {
        Solution solution = new P242ValidAnagram().new Solution();
        String s = "anagram";
        String t = "nagaram";
        System.out.println(solution.isAnagram(s,t));

        // TO TEST
    }

    //leetcode submit region begin(Prohibit modification and deletion)
    class Solution {
        public boolean isAnagram(String s, String t) {
            if (s.length() != t.length()) return false;
            HashMap<Character, Integer> characterIntegerHashMap = new HashMap<Character, Integer>();
            for (int i = 0; i < s.length(); i++) {
                if(characterIntegerHashMap.containsKey(s.charAt(i))){
                    characterIntegerHashMap.put(s.charAt(i),characterIntegerHashMap.get(s.charAt(i)) + 1);
                }else {
                    characterIntegerHashMap.put(s.charAt(i),1);
                }
            }

            for (int i = 0; i < t.length(); i++) {
                if(characterIntegerHashMap.containsKey(t.charAt(i))){
                    characterIntegerHashMap.put(t.charAt(i),characterIntegerHashMap.get(t.charAt(i)) - 1);
                    if(characterIntegerHashMap.get(t.charAt(i))==0){
                        characterIntegerHashMap.remove(t.charAt(i));
                    }
                }else {
                    return false;
                }
            }
            return characterIntegerHashMap.size()==0;
        }
    }
//leetcode submit region end(Prohibit modification and deletion)

}
