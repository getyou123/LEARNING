package org.example;

import com.google.common.collect.BinaryTreeTraverser;
import org.apache.commons.collections4.CollectionUtils;
import org.junit.Test;

import java.util.ArrayList;

public class CollectionUtilsTest {
    @Test
    public void test001(){
        // 判断集合为空 （null 或者 size =0 都是true）
        ArrayList<Integer> objects = new ArrayList<>();
        System.out.println(CollectionUtils.isEmpty(objects));
        // --true

        objects = null;
        System.out.println(CollectionUtils.isEmpty(objects));
        // --true

        // 判断集合非空 （不等null 且 size>0）
        System.out.println(CollectionUtils.isNotEmpty(objects));
        // -- false
        ArrayList<Integer> integers = new ArrayList<>();
        System.out.println(CollectionUtils.isNotEmpty(integers));
        // --false
        integers.add(1);
        System.out.println(CollectionUtils.isNotEmpty(integers));
        // --true


        // 交集 并集 补集 差集




    }
}
