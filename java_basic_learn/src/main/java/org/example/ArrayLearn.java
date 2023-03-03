package org.example;

import org.junit.Test;

import java.util.Arrays;

public class ArrayLearn {

    @Test
    public void testArrays() {
        // 实现数组对比
        int[] arr1 = new int[]{1, 2, 3, 4};
        int[] arr2 = new int[]{1, 3, 2, 4};
        boolean isEquals = Arrays.equals(arr1, arr2);
        System.out.println(isEquals);

        int[] arr3 = new int[]{1, 2, 3, 4};
        int[] arr4 = new int[]{1, 2, 3, 4};
        boolean isEquals1 = Arrays.equals(arr3, arr4);
        System.out.println(isEquals1);

        // 输出数组信息
        System.out.println("数组信息：" + Arrays.toString(arr1));

        // 填充数组元素
        int[] ints = new int[5];
        Arrays.fill(ints, 10);
        System.out.println("数组信息：" + Arrays.toString(ints));

        // 数组排序
        Arrays.sort(arr2);
        System.out.println("数组排序：" + Arrays.toString(arr2));

    }


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
