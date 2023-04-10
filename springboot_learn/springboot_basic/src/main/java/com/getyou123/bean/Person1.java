package com.getyou123.bean;

import lombok.Data;

import java.util.Date;

@Data
public class Person1 {

    private String userName;
    private Integer age;
    private Date birth;
    private Pet pet;

}