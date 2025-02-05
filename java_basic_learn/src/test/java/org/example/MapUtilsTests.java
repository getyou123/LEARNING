package org.example;

import org.apache.commons.collections4.MapUtils;
import org.junit.Test;

import java.util.HashMap;

public class MapUtilsTests {
    @Test
    public void test001() {
        // 判定map为空
        System.out.println(MapUtils.isEmpty(null));
        // -- true

        HashMap<Integer, Integer> integerIntegerHashMap = new HashMap<>();
        System.out.println(MapUtils.isEmpty(integerIntegerHashMap));
        // -- true

        integerIntegerHashMap.put(1, 1);
        System.out.println(MapUtils.isEmpty(integerIntegerHashMap));
        // -- false
        System.out.println(MapUtils.isNotEmpty(integerIntegerHashMap));
        // --true
        // 获取key = 1 的值并转为 String
        System.out.println(MapUtils.getString(integerIntegerHashMap, 1));
        // -- 1
        System.out.println(MapUtils.getString(integerIntegerHashMap, 3));
        // -- null 获取不到就转为null

    }
}
