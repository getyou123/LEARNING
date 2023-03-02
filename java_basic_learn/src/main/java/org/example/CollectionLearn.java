package org.example;

import org.junit.Test;

import java.util.*;

public class CollectionLearn {

    /**
     * add 操作
     */
    @Test
    public void testAdd() {
        //ArrayList是Collection的子接口List的实现类之一。
        Collection coll = new ArrayList();
        coll.add("cc");
        coll.add("dd");
        System.out.println(coll);
    }

    /**
     * 区分add 和 addAll
     */
    @Test
    public void testAddAll() {
        Collection c1 = new ArrayList();
        c1.add(1);
        c1.add(2);
        System.out.println("c1集合元素的个数：" + c1.size());//2
        System.out.println("c1 = " + c1);

        Collection c2 = new ArrayList();
        c2.add(1);
        c2.add(2);
        System.out.println("c2集合元素的个数：" + c2.size());//2
        System.out.println("c2 = " + c2);

        Collection other = new ArrayList();
        other.add(1);
        other.add(2);
        other.add(3);
        System.out.println("other集合元素的个数：" + other.size());//3
        System.out.println("other = " + other);
        System.out.println();

        c1.addAll(other);
        System.out.println("c1集合元素的个数：" + c1.size());//5
        System.out.println("c1.addAll(other) = " + c1);

        c2.add(other);
        System.out.println("c2集合元素的个数：" + c2.size());//3
        System.out.println("c2.add(other) = " + c2);
    }


    /**
     * 数组转集合
     * 集合转数组
     * 集合遍历
     */
    @Test
    public void testForEach() {
        Collection c1 = new ArrayList();
        c1.add(1);
        c1.add(2);
        System.out.println("c1集合元素的个数：" + c1.size());//2
        System.out.println("c1 = " + c1);

        // 集合 -> 数组 集合转数组
        Object[] objects = c1.toArray();

        // 数组 -> 集合 数组转集合 转为的是一个定长的List
        List<Object> objects1 = Arrays.asList(objects);

        System.out.println("iterator遍历");
        // iterator实现遍历
        Iterator iterator = c1.iterator();
        while (iterator.hasNext()) {
            System.out.println(iterator.next());
        }

        System.out.println("for循环-注意这种for循环的方式修改数据元素是不成功的");
        // for循环
        for (Object obj : c1) {
            System.out.println(obj);
        }
    }

    @Test
    public void testForDel() {
        Collection c1 = new ArrayList<Integer>();
        c1.add(1);
        c1.add(1);
        c1.add(2);
        System.out.println("c1集合元素的个数：" + c1.size());//2
        System.out.println("c1 = " + c1);


        Iterator<Integer> iterator0 = c1.iterator();
        while (iterator0.hasNext()) {
            Integer it = iterator0.next();
            if (it == 1) {
                iterator0.remove();
            }
        }

        Iterator iterator1 = c1.iterator();
        while (iterator1.hasNext()) {
            System.out.println(iterator1.next());
        }

        // 下面的代码则会报错
        List<Integer> arrayList = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            arrayList.add(Integer.valueOf(i));
        }

        // 复现方法一
        Iterator<Integer> iterator = arrayList.iterator();
        while (iterator.hasNext()) {
            Integer integer = iterator.next();
            if (integer.intValue() == 5) {
                arrayList.remove(integer);
            }
        }

        // 复现方法二
        iterator = arrayList.iterator();
        for (Integer value : arrayList) {
            Integer integer = iterator.next();
            if (integer.intValue() == 5) {
                arrayList.remove(integer);
            }
        }
    }


}
