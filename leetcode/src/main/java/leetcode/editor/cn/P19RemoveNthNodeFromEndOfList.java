////给你一个链表，删除链表的倒数第 n 个结点，并且返回链表的头结点。
////
////
////
//// 示例 1：
////
////
////输入：head = [1,2,3,4,5], n = 2
////输出：[1,2,3,5]
////
////
//// 示例 2：
////
////
////输入：head = [1], n = 1
////输出：[]
////
////
//// 示例 3：
////
////
////输入：head = [1,2], n = 1
////输出：[1]
////
////
////
////
//// 提示：
////
////
//// 链表中结点的数目为 sz
//// 1 <= sz <= 30
//// 0 <= Node.val <= 100
//// 1 <= n <= sz
////
////
////
////
//// 进阶：你能尝试使用一趟扫描实现吗？
////
//// Related Topics 链表 双指针 👍 2574 👎 0
//
//package leetcode.editor.cn;
////Java：删除链表的倒数第 N 个结点
//public class P19RemoveNthNodeFromEndOfList{
//    public static void main(String[] args) {
//        Solution solution = new P19RemoveNthNodeFromEndOfList().new Solution();
//        // TO TEST
//    }
//    //leetcode submit region begin(Prohibit modification and deletion)
///**
// * Definition for singly-linked list.
// * public class ListNode {
// *     int val;
// *     ListNode next;
// *     ListNode() {}
// *     ListNode(int val) { this.val = val; }
// *     ListNode(int val, ListNode next) { this.val = val; this.next = next; }
// * }
// */
//class Solution {
//    public ListNode removeNthFromEnd(ListNode head, int n) {
//        ListNode preHead = new ListNode();
//        preHead.next =head;
//        int k = 0;
//
//        ListNode fast = preHead;
//        ListNode slow = preHead;
//
//        while(k<n){
//            k++;
//            fast=fast.next;
//            if(fast==null) return preHead.next;
//        }
//        while(fast.next!=null){
//            fast=fast.next;
//            slow=slow.next;
//        }
//        slow.next = slow.next.next;
//        return preHead.next;
//    }
//}
////leetcode submit region end(Prohibit modification and deletion)
//
//}
