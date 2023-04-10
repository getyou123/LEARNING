package com.getyou123.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 实现从配置文件中读取数据实例化就一个bean
 */

@Component
@ConfigurationProperties(prefix = "mycar")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class Car {
    private String brand;
    private Integer price;
}
