package org.example.pojo;


import lombok.Data;

@Data
public class Emp {
    private Integer empId;
    private String empName;
    private String sex;
    private Dept dept;

}
