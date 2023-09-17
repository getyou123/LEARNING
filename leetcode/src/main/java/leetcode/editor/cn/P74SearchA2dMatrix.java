//给你一个满足下述两条属性的 m x n 整数矩阵： 
//
// 
// 每行中的整数从左到右按非递减顺序排列。 
// 每行的第一个整数大于前一行的最后一个整数。 
// 
//
// 给你一个整数 target ，如果 target 在矩阵中，返回 true ；否则，返回 false 。 
//
// 
//
// 示例 1： 
// 
// 
//输入：matrix = [[1,3,5,7],[10,11,16,20],[23,30,34,60]], target = 3
//输出：true
// 
//
// 示例 2： 
// 
// 
//输入：matrix = [[1,3,5,7],[10,11,16,20],[23,30,34,60]], target = 13
//输出：false
// 
//
// 
//
// 提示： 
//
// 
// m == matrix.length 
// n == matrix[i].length 
// 1 <= m, n <= 100 
// -10⁴ <= matrix[i][j], target <= 10⁴ 
// 
//
// Related Topics 数组 二分查找 矩阵 👍 811 👎 0

package leetcode.editor.cn;
//Java：搜索二维矩阵
public class P74SearchA2dMatrix{
    public static void main(String[] args) {
        Solution solution = new P74SearchA2dMatrix().new Solution();
        // TO TEST
    }
    //leetcode submit region begin(Prohibit modification and deletion)
class Solution {
    public boolean searchMatrix(int[][] matrix, int target) {
        if(matrix==null) return false;
        int m = matrix.length;
        int n = matrix[0].length;
        if(m*n==1) return matrix[0][0]==target;
        int l = 0;
        int h = m*n-1;

        while(l<h-1){
            int mid = l + (h-l+1)/2;
            if(matrix[mid/n][mid%n]==target) return true;
            if(matrix[mid/n][mid%n]>target) h = mid;
            if(matrix[mid/n][mid%n]<target) l = mid;
        }
        boolean res = false;
        if(matrix[h/n][h%n]==target) res= true;
        if(matrix[l/n][l%n]==target) res= true;
        return res;

    }
}
//leetcode submit region end(Prohibit modification and deletion)

}
