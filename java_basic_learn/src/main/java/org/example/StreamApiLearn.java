package org.example;

import com.sun.xml.internal.ws.api.model.wsdl.WSDLOutput;
import org.junit.Test;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class StreamApiLearn {

    // 从集合获取stream
    @Test
    public void test01() {
        List<Integer> list = Arrays.asList(1, 2, 3, 4, 5);
        //JDK1.8中，Collection系列集合增加了方法
        Stream<Integer> stream = list.stream();
    }


    // 从数组获取stream
    @Test
    public void test02() {
        String[] arr = {"hello", "world"};
        Stream<String> stream = Arrays.stream(arr);
    }


    // 通过 Stream.of 获取
    @Test
    public void test04() {
        Stream<Integer> stream = Stream.of(1, 2, 3, 4, 5);
        stream.forEach(System.out::println);
    }

    // StreamApi 调用

    @Test
    public void test05() {
        Stream<Integer> stream = Stream.of(1, 2, 3, 4, 5, 7, 8, 9, 20, 11);

        System.out.println("----分页操作-------");
        // 过滤 并 skip+limit 来充当分页
        stream.map(s -> s + 1)
                .filter(s -> s % 2 == 0)
                .skip(3)
                .limit(2)
                .forEach(System.out::println);


        // 去重
        System.out.println("----distinct------");
        Stream<Integer> stream1 = Stream.of(1, 2, 3, 4, 5, 7, 8, 9, 20, 11);
        stream1.map(s -> {
                    if (s % 2 == 0) return 1;
                    else return 0;
                }).distinct()
                .forEach(System.out::println);


        // flatmap
        System.out.println("----flatmap------");
        Stream<String> stream3 = Stream.of("id` bigint unsigned NOT NULL AUTO_INCREMENT COMMENT '自增主键，无业务含义'",
                " `invest_event_id` varchar(100) NOT NULL DEFAULT '' COMMENT '投资事件id',\n",
                " prism.\n",
                "  `invest_id` bigint unsigned NOT NULL COMMENT '（名片）'");

        stream3.flatMap(s -> Arrays.stream(s.split("[^a-zA-Z']+")))
                .filter(word -> !word.isEmpty())
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
                .forEach((k, v) -> System.out.println(k + ":" + v));

        // sorted
        System.out.println("----sorted------");
        Stream<Integer> stream4 = Stream.of(1, 2, 13, 4, 45, 17, 8, 9, 20, 11);
        stream4.sorted().forEach(System.out::println);
        System.out.println("----sorted1------");
        Stream<Integer> stream5 = Stream.of(1, 2, 13, 4, 45, 17, 8, 9, 20, 11);
        stream5.sorted(((o1, o2) -> o2.compareTo(o1))).forEach(System.out::println);

        // allMatch
        System.out.println("----allMatch------");
        Stream<Integer> stream6 = Stream.of(1, 2, 13, 4, 45, 17, 8, 9, 20, 11);
        boolean b = stream6.allMatch(s -> s % 2 == 0);

        Stream<Integer> stream7 = Stream.of(1, 2, 13, 4, 45, 17, 8, 9, 20, 11);
        boolean b1 = stream7.allMatch(s -> s % 2 >= 0);

        System.out.println(b);
        System.out.println(b1);


        // anyMatch
        System.out.println("----anyMatch------");
        Stream<Integer> stream8 = Stream.of(1, 2, 13, 4, 45, 17, 8, 9, 20, 11);
        boolean b2 = stream8.anyMatch(s -> s % 2 == 0);

        System.out.println(b2);


        // noneMatch
        System.out.println("----noneMatch------");
        String[] arr = {"apple", "pear", "banana"};
        boolean result = Arrays.stream(arr).noneMatch(s -> s.length() > 5);
        System.out.println(result); // true


        //findFirst
        System.out.println("----findFirst------");
        List<String> list = Arrays.asList("apple", "banana", "apricot", "orange");
        Optional<String> resultF = list.stream()
                .filter(s -> s.startsWith("d"))
                .findFirst();
        if (resultF.isPresent()) {
            System.out.println("The first string starting with \"a\" is: " + resultF.get());
        } else {
            System.out.println("No string starting with \"a\" found.");
        }


        // count
        System.out.println("----count------");
        long count = list.stream().count();
        System.out.println(count); // 输出：5

        // 获取最大值和最小值

        System.out.println("----最大最小值------");
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5);

        // 获取最大值
        Optional<Integer> max = numbers.stream().max(Integer::compareTo);
        if (max.isPresent()) {
            System.out.println("Max value is: " + max.get()); // 输出: Max value is: 5
        }

        // 获取最小值
        Optional<Integer> min = numbers.stream().min(Integer::compareTo);
        if (min.isPresent()) {
            System.out.println("Min value is: " + min.get()); // 输出: Min value is: 1
        }


        System.out.println("----最大最小值------");
        IntStream intStream = IntStream.of(1, 2, 3, 4, 5);

        IntSummaryStatistics stats = intStream.summaryStatistics();
        int max1 = stats.getMax();
        int min2 = stats.getMin();

        System.out.println("Max: " + max1);
        System.out.println("Min: " + min2);


        // reduce
        System.out.println("----reduce------");
        // 求最大值
        int max3 = numbers.stream().reduce(Integer.MIN_VALUE, (a, bb) -> Math.max(a, bb));
        // 求最小值
        int min3 = numbers.stream().reduce(Integer.MAX_VALUE, (a, bb) -> Math.min(a, bb));
        System.out.println(min3);
        System.out.println(max3);


        System.out.println("----collect------");
        List<Integer> collect = numbers.stream().collect(Collectors.toList());
        Set<Integer> collect1 = numbers.stream().collect(Collectors.toSet());
        System.out.println(collect);
        System.out.println(collect1);



    }


}
