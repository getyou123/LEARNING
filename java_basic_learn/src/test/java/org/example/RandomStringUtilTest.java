package org.example;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.junit.Test;

public class RandomStringUtilTest {
    @Test
    public void test001(){

        // 在指定的范围内随机产出指定的字符串
        System.out.println(RandomStringUtils.random(3, "afz123456"));
        // 纯字母
        System.out.println(RandomStringUtils.randomAlphabetic(10));
        // 纯数字
        System.out.println(RandomStringUtils.randomNumeric(10));
        // 字母加数字
        System.out.println(RandomStringUtils.randomAlphanumeric(10));


    }
}
