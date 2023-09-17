////给你二叉树的根节点 root ，返回其节点值的 锯齿形层序遍历 。（即先从左往右，再从右往左进行下一层遍历，以此类推，层与层之间交替进行）。
////
////
////
//// 示例 1：
////
////
////输入：root = [3,9,20,null,null,15,7]
////输出：[[3],[20,9],[15,7]]
////
////
//// 示例 2：
////
////
////输入：root = [1]
////输出：[[1]]
////
////
//// 示例 3：
////
////
////输入：root = []
////输出：[]
////
////
////
////
//// 提示：
////
////
//// 树中节点数目在范围 [0, 2000] 内
//// -100 <= Node.val <= 100
////
////
//// Related Topics 树 广度优先搜索 二叉树 👍 786 👎 0
//
//package leetcode.editor.cn;
//
//
//import java.util.LinkedList;
//
////Java：二叉树的锯齿形层序遍历
//public class P103BinaryTreeZigzagLevelOrderTraversal{
//    public static void main(String[] args) {
//        Solution solution = new P103BinaryTreeZigzagLevelOrderTraversal().new Solution();
//        // TO TEST
//    }
//    //leetcode submit region begin(Prohibit modification and deletion)
///**
// * Definition for a binary tree node.
// * public class TreeNode {
// *     int val;
// *     TreeNode left;
// *     TreeNode right;
// *     TreeNode() {}
// *     TreeNode(int val) { this.val = val; }
// *     TreeNode(int val, TreeNode left, TreeNode right) {
// *         this.val = val;
// *         this.left = left;
// *         this.right = right;
// *     }
// * }
// */
//class Solution {
//    public List<List<Integer>> zigzagLevelOrder(TreeNode root) {
//        List<List<Integer>> res =  new LinkedList<>();
//
//        LinkedList<TreeNode> queue  = new LinkedList<TreeNode>();
//        if(root!=null){
//            queue.addFirst(root);
//        }
//
//
//        int level = 0;
//        while(!queue.isEmpty()){
//            int size =queue.size();
//            LinkedList<Integer> tmp = new LinkedList<>();
//            for(int i = 0;i<size;i++){
//                TreeNode last = queue.removeLast();
//                if(level%2==0){
//                    tmp.addLast(last.val);
//                }else{
//                    tmp.addFirst(last.val);
//                }
//                if(last.left !=null) queue.addFirst(last.left);
//                if(last.right !=null) queue.addFirst(last.right);
//            }
//            level++;
//            res.add(tmp);
//        }
//
//        return res;
//    }
//}
////leetcode submit region end(Prohibit modification and deletion)
//
//}
