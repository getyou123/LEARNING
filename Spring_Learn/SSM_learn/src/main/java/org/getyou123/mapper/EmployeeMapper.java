package org.getyou123.mapper;

import org.apache.ibatis.annotations.Select;
import org.getyou123.pojo.Employee;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface EmployeeMapper{
    @Select("select * From t_emp_ssm")
    List<Employee> getEmployeeList();
}