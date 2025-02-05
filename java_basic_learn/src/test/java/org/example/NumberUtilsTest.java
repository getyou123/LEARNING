package org.example;

import org.apache.commons.lang3.math.NumberUtils;
import org.junit.Test;

public class NumberUtilsTest {
    @Test
    public void test001() {
        //判定一个参数是不是数字
        System.out.println("12 isDigits GEt " + NumberUtils.isDigits("12"));
        // --true
        System.out.println("12 isParsable GEt " + NumberUtils.isParsable("12"));
        // --true
        System.out.println("12 isCreatable GEt " + NumberUtils.isCreatable("12"));
        // --true

        System.out.println("12.1 isDigits GEt " + NumberUtils.isDigits("12.1"));
        //--false
        System.out.println("12.1 isParsable GEt " + NumberUtils.isParsable("12.1"));
        // --true
        System.out.println("12.1 isCreatable GEt " + NumberUtils.isCreatable("12.1"));
        // --true

        System.out.println("-12.1 isDigits GEt " + NumberUtils.isDigits("-12.1"));
        //--false
        System.out.println("-12.1 isParsable GEt " + NumberUtils.isParsable("-12.1"));
        // --true
        System.out.println("-12.1 isCreatable GEt " + NumberUtils.isCreatable("-12.1"));
        // --true


        System.out.println("-12.1 isDigits GEt " + NumberUtils.isDigits("-12.1"));
        //--false
        System.out.println("-12.1 isParsable GEt " + NumberUtils.isParsable("-12.1"));
        // --true
        System.out.println("-12.1 isCreatable GEt " + NumberUtils.isCreatable("-12.1"));
        // --true

    }

    @Test
    public void test002() {
        // 字符串转数字
        System.out.println("12.3 toLong:" + NumberUtils.toLong("12.3", 888L));
        System.out.println("12.3oooo toLong:" + NumberUtils.toLong("12.3oooo", 90L));
        System.out.println("+12.3 toLong:" + NumberUtils.toLong("+12.3", 90L));

        System.out.println("+12.3 toDouble:" + NumberUtils.toDouble("+12.3", 90.00));
        System.out.println("-12.3 toDouble:" + NumberUtils.toDouble("-12.3", 90.00));

        System.out.println("-12 toLong:" + NumberUtils.toLong("-12", 9));
    }
}
