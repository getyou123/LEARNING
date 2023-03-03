package org.example;


import org.junit.Test;

import java.util.*;

//map的典型实现是hashmap

public class MapLearn {
    public static void main(String[] args) {

        Map<Character, Integer> M = new HashMap<Character, Integer>();//接口和实现的对象


        Character C = new Character('a');

        // 增加
        M.put(C, 34);
        if (M.containsKey(C)) System.out.println(M.get(C));

        // 获取
        System.out.println(M.get(C));

        // 删除返回值是value
        Integer remove = M.remove(C);
        System.out.println("remove的元素：" + remove);

        M.put(C, 34);
        //size
        System.out.println("map大小：" + M.size());

        // 是否包含
        boolean boolean1 = M.containsKey(C);
        System.out.println("包含元素：" + boolean1);

        //遍历方式一
        System.out.print("遍历方式一：");
        Set<Character> characters = M.keySet();
        for (Character key : characters) {
            System.out.println(M.get(key));
        }

        // 遍历方式二
        System.out.print("遍历方式二：");
        Set<Map.Entry<Character, Integer>> entries = M.entrySet();
        for (Map.Entry<Character, Integer> en : entries) {
            System.out.println("key:" + en.getKey() + " value:" + en.getValue());
        }

        // 获取keySet
        Set<Character> characters1 = M.keySet();
        System.out.println("keySet:" + characters1);

        // 获取values
        Collection<Integer> values = M.values();
        System.out.println("values:" + values);

    }

    @Test
    public void testLinkedHashMap() {
        LinkedHashMap integerLinkedHashMap = new LinkedHashMap<String, Integer>();
        integerLinkedHashMap.put("aa", 123);
        integerLinkedHashMap.put("cc", 123);
        integerLinkedHashMap.put("bb", 123);

        Set entrySet = integerLinkedHashMap.entrySet();
        for (Object o : entrySet) {
            System.out.println(o);
        }
    }

    @Test
    public void testTreeMap() {
        // 可以指定使用自然序列或者是自定义序列
        TreeMap<String, Integer> stringIntegerTreeMap = new TreeMap<>(new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return o2.compareTo(o1);
            }
        });
        stringIntegerTreeMap.put("aa", 123);
        stringIntegerTreeMap.put("cc", 123);
        stringIntegerTreeMap.put("bb", 123);

        Set<Map.Entry<String, Integer>> entries = stringIntegerTreeMap.entrySet();
        for (Map.Entry<String, Integer> entry : entries) {
            System.out.println(entry);
        }
    }
}