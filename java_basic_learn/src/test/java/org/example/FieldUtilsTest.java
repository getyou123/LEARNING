package org.example;

import org.apache.commons.lang3.reflect.FieldUtils;
import org.junit.Test;

public class FieldUtilsTest {
    // 获取属性 设置属性
    @Test
    public void test001() {
        Man man = new Man();
        man.age = 10;
        man.setName("zhu");
        try {
            Object age = FieldUtils.readField(man, "age", true);
            System.out.println(age);

            Object name = FieldUtils.readField(man, "name", true);
            System.out.println(name);

            FieldUtils.writeField(man,"age",100,true);
            System.out.println(man);

            FieldUtils.writeField(man,"name","Bili",true);
            System.out.println(man);

        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }

    }
}
