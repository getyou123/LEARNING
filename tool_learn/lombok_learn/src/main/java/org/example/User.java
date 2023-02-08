package org.example;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Slf4j
@Accessors(chain = true)
@EqualsAndHashCode
public class User {
    private String name;
    private int age;
    private String sex;

    public void Test(){
        log.info("test 方法调用");
        log.info("进入[{}]时刻是","test");
    }
}
