package org.example;

import org.junit.Test;


import java.util.*;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class LambdaLearn {
    @Test
    public void test1() {
        //未使用Lambda表达式
        Runnable r1 = new Runnable() {
            @Override
            public void run() {
                System.out.println("我爱北京天安门");
            }
        };

        r1.run();

        System.out.println("***********************");

        //使用Lambda表达式 这种需要接口中只有一个方法
        Runnable r2 = () -> {
            System.out.println("我爱北京故宫");
        };

        r2.run();
    }


    @Test
    public void test2() {
        //未使用Lambda表达式
        Comparator<Integer> integerComparator = new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return o1 - o2;
            }
        };

        Integer i1 = 100;
        Integer i2 = 1000;
        System.out.println(integerComparator.compare(i1, i2));


        // 使用Lambda表达式1

        //Comparator<Integer> integerComparator0 = (Integer o1, Integer o2) -> o1 - o2;

        Comparator<Integer> integerComparator1 = (o1, o2) -> o1 - o2;// 可以实现类型推断
        System.out.println(integerComparator1.compare(i2, i1));

        // 设置可以在单个参数的时候去除() s->{}
        // Lambda体中只有一个 return 语句时候就是可以直接省略大括号和return  即： (o1, o2) -> o1 - o2

        // 使用Lambda表达式2 - 方法引用
        Comparator<Integer> integerComparator2 = Integer::compare;
        System.out.println(integerComparator2.compare(i2, i1));

    }


    // 测试 Consumer接口 转为 lambda表达式
    @Test
    public void test3() {

        System.out.println("----------------");
        // 不使用lambda表达式
        List<Integer> list = Arrays.asList(1, 2, 3, 4, 5);
        list.forEach(new Consumer<Integer>() {
            @Override
            public void accept(Integer integer) {
                System.out.println(integer);
            }
        });

        System.out.println("----------------");
        // 实现数据按照条件移除
        List<Integer> list1 = new ArrayList<Integer>();
        list1.add(1);
        list1.add(2);
        list1.add(3);
        list1.removeIf(s -> s.equals(2));
        // 使用 lambda表达式
        list1.forEach(o1 -> System.out.println(o1));


        System.out.println("----------------");
        List<String> list2 = new ArrayList<String>();
        list2.add("测试");
        list2.add("test");
        list2.add("good");

        // 使用 lambda表达式 & 功能型接口
        list2.replaceAll(s -> s.toUpperCase());
        list2.forEach(s -> System.out.println(s));

    }

    // 消费型接口
    @Test
    public void test4() {
        HashMap<Integer, String> map = new HashMap<>();
        map.put(1, "hello");
        map.put(2, "java");
        map.put(3, "lambda");
        map.forEach((k, v) -> System.out.println(k + "->" + v));
    }

    // 供给型接口
    @Test
    public void test5() {
        Stream.generate(() -> Math.random()).forEach(num -> System.out.println(num));
    }

    // 功能型接口 是有返回值的
    @Test
    public void test7() {
        List<Integer> list2 = new ArrayList<Integer>();
        list2.add(1);
        list2.add(2);
        list2.add(3);

        // 定义一个转化Function
        Function<Integer, ReflectLearnUse> reflectLearnUseIntegerFunction = ReflectLearnUse::new;
        // 使用 lambda表达式 & 功能型接口
        List<ReflectLearnUse> result = list2.stream().map(reflectLearnUseIntegerFunction).collect(Collectors.toList());
        // 进行输出
        result.forEach(System.out::println);

        // 进一步简化
        System.out.println("---------");
        List<ReflectLearnUse> result1 = list2.stream().map(ReflectLearnUse::new).collect(Collectors.toList());
        result1.forEach(System.out::println);

        // 转为+1
        System.out.println("---------");
        List<Integer> result2 = list2.stream().map(s -> s + 1).collect(Collectors.toList());
        result2.forEach(System.out::println);

    }

    // 判断型接口
    @Test
    public void test6() {
        ArrayList<String> list = new ArrayList<>();
        list.add("hello");
        list.add("java");
        list.add("ok");
        list.add("yes");

        list.forEach(str -> System.out.println(str));
        System.out.println();

        list.removeIf(str -> str.length() < 5);
        list.forEach(str -> System.out.println(str));
    }


}
