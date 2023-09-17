//给定一个单词列表 words 和一个整数 k ，返回前 k 个出现次数最多的单词。 
//
// 返回的答案应该按单词出现频率由高到低排序。如果不同的单词有相同出现频率， 按字典顺序 排序。 
//
// 
//
// 示例 1： 
//
// 
//输入: words = ["i", "love", "leetcode", "i", "love", "coding"], k = 2
//输出: ["i", "love"]
//解析: "i" 和 "love" 为出现次数最多的两个单词，均为2次。
//    注意，按字母顺序 "i" 在 "love" 之前。
// 
//
// 示例 2： 
//
// 
//输入: ["the", "day", "is", "sunny", "the", "the", "the", "sunny", "is", "is"], 
//k = 4
//输出: ["the", "is", "sunny", "day"]
//解析: "the", "is", "sunny" 和 "day" 是出现次数最多的四个单词，
//    出现次数依次为 4, 3, 2 和 1 次。
// 
//
// 
//
// 注意： 
//
// 
// 1 <= words.length <= 500 
// 1 <= words[i] <= 10 
// words[i] 由小写英文字母组成。 
// k 的取值范围是 [1, 不同 words[i] 的数量] 
// 
//
// 
//
// 进阶：尝试以 O(n log k) 时间复杂度和 O(n) 空间复杂度解决。 
//
// Related Topics 字典树 哈希表 字符串 桶排序 计数 排序 堆（优先队列） 👍 558 👎 0

package leetcode.editor.cn;

import java.io.FilterOutputStream;
import java.util.*;

//Java：前K个高频单词
public class P692TopKFrequentWords{
    public static void main(String[] args) {
        Solution solution = new P692TopKFrequentWords().new Solution();
        String[] test = new String[]{"i","love","leetcode","i","love","coding"};
        int t  = 2;
        System.out.println(solution.topKFrequent(test,2));
        // TO TEST
    }
    //leetcode submit region begin(Prohibit modification and deletion)
class Solution {

        public List<String> topKFrequent(String[] words, int k) {
            PriorityQueue<Map.Entry<String,Integer>> pq = new PriorityQueue<Map.Entry<String,Integer>>((a, b)->{
                if( (a.getValue()<b.getValue()) ||(a.getValue()==b.getValue() && a.getKey().compareTo(b.getKey())>0)) {
                    return -1;
                }else {
                    return 1;
                }
            });

            HashMap<String,Integer> map =new HashMap<String,Integer>();
            for(int i = 0;i<words.length;i++){
                if(map.containsKey(words[i])){
                    map.put(words[i],map.get(words[i])+1);
                }else {
                    map.put(words[i],1);
                }
            }

            for(Map.Entry<String,Integer> entry : map.entrySet()){
                if(pq.size()<k){
                    pq.add(entry);
                }else{
                    if(
                            pq.size()>0 &&
                                    ((pq.peek().getValue()< entry.getValue()) ||
                                            (entry.getValue() == pq.peek().getValue() && pq.peek().getKey().compareTo(entry.getKey())>0))
                    ){
                        pq.poll();
                        pq.add(entry);
                    }

                }
//                System.out.println(pq.toString());
            }


            LinkedList<String> res = new LinkedList<String>();
            while(pq.size()>0){
                res.addFirst(pq.poll().getKey());
            }

            return res;

        }
}
//leetcode submit region end(Prohibit modification and deletion)

}
