package org.example;

import org.apache.commons.lang3.ArrayUtils;
import org.junit.Test;

public class ArrayUtilsTest {
    @Test
    public void test001(){

        // 数组长度为0 或者 数组为null
        int[] ints = new int[0];
        System.out.println(ArrayUtils.isEmpty(ints));
        //--true
        ints = null;
        System.out.println(ArrayUtils.isEmpty(ints));
        //--true

        // 打印数据
        System.out.println(ArrayUtils.toString(new int[]{1, 2, 3}));

        //向数据添加元素 返回值是一个新的数组
        int[] ints1 = new int[1];
        ints1[0] = 1;
        System.out.println(ArrayUtils.toString(ArrayUtils.add(ints1, 10)));


    }
}
