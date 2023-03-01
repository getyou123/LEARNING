package org.example;


import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * 多种方式实现对象比较大小
 * 一种方式是实现Comparable 接口，这种情况下比较的规则是固定的，而且是需要在类上实现的，代码是侵入的
 * 另外一种写一个Comparator，这种的是额外的，非侵入的，可以自定义应用到指定对象的多种排序规则
 */

class Struct implements Comparable<Struct> {
    private int a;
    private int b;
    private int c;

    @Override
    public int compareTo(Struct o) {
        int res;
        if (this.a > o.a || this.b > o.b || this.c > o.c) {
            res = -1;
        } else if (o.a == this.a && o.b == this.b && this.c == o.c) {
            res = 0;
        } else {
            res = 1;
        }
        return res;
    }

    public Struct(int a, int b, int c) {
        this.a = a;
        this.b = b;
        this.c = c;
    }

    @Override
    public String toString() {
        return "Struct{" +
                "a=" + a +
                ", b=" + b +
                ", c=" + c +
                '}';
    }

    public int getA() {
        return a;
    }

    public int getB() {
        return b;
    }

    public int getC() {
        return c;
    }
}


/**
 * 这是一种Comparator
 */
class StructComparator1 implements Comparator<Struct> {
    @Override
    public int compare(Struct o1, Struct o2) {
        int res;
        if (o1.getA() > o2.getA() || o1.getB() > o2.getB() || o1.getC() > o2.getC()) {
            res = -1;
        } else if (o2.getA() == o1.getA() && o2.getB() == o1.getB() && o2.getC() == o2.getC()) {
            res = 0;
        } else {
            res = 1;
        }
        return res;
    }
}


/**
 * 第二种数据比较规则
 */
class StructComparator2 implements Comparator<Struct> {
    @Override
    public int compare(Struct o1, Struct o2) {
        return o1.getA() - o2.getA();
    }
}

public class Compare {

    public static void main(String[] args) {

        Struct struct1 = new Struct(1, 2, 3);
        Struct struct2 = new Struct(1, 2, 4);
        Struct struct3 = new Struct(3, 2, 3);

        ArrayList<Struct> structs = new ArrayList<>();
        structs.add(struct2);
        structs.add(struct1);
        structs.add(struct3);

        // 排序
        System.out.println("--------------------");
        Collections.sort(structs);

        for (int i = 0; i < structs.size(); i++) {
            System.out.println(structs.get(i));
        }

        // 排序
        System.out.println("--------------------");
        Collections.sort(structs,new StructComparator2());

        for (int i = 0; i < structs.size(); i++) {
            System.out.println(structs.get(i));
        }

        // 排序
        System.out.println("--------------------");
        Collections.sort(structs,new StructComparator1());

        for (int i = 0; i < structs.size(); i++) {
            System.out.println(structs.get(i));
        }


    }

}
