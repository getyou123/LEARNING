package org.example;

public class ArrayLearn {
    public static void main(String[] args) {
        // 包含初始化的-静态的
        int[] arr = new int[]{1, 2, 3};
        int[] arr2 = {2, 4, 5};

        // 固定大小的-动态的
        int[] arr1 = new int[45];

        // 获取长度
        for (int i = 0; i < arr.length; i++) {
            System.out.println(arr[i]);
        }

        // 二维数组的初始化
        int[][] arr221 = new int[][]{{3, 8, 2}, {2, 7}, {9, 0, 1, 6}};
        int[][] arr222 = {
                {1, 2, 3},
                {4, 5, 6},
                {7, 8, 9, 10}
        };

        for (int i = 0; i < arr222.length; i++) {
            for (int j = 0; j < arr222[i].length; j++) {
                System.out.print(arr222[i][j]);
            }
            System.out.println();
        }
    }
}
