package org.example;

import org.apache.commons.lang3.ObjectUtils;
import org.junit.Test;

import java.util.Objects;

public class ObjectUtilsTest {
    @Test
    public void test001() {
        String s1 = null;
        String s2 = "s2";
        String s3 = "s3";

        // 首个不为空的对象
        System.out.println(ObjectUtils.firstNonNull(s1, s2, s3));
        System.out.println(ObjectUtils.firstNonNull(s2, s1, s3));
        System.out.println(ObjectUtils.firstNonNull(s3, s1, s2));
        System.out.println(ObjectUtils.firstNonNull(s1, s1, s2));


        // 对象为空则
        System.out.println("s1 为空则 返回s2：" + ObjectUtils.defaultIfNull(s1, s2));

        // 对象均不为空
        System.out.println(ObjectUtils.allNotNull(s2, s3, s2));

        System.out.println(ObjectUtils.allNotNull(s1, s3, s2));
    }
}
