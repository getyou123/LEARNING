package org.getyou123.service;

import com.github.pagehelper.PageInfo;
import org.getyou123.pojo.Employee;

public interface EmployeeService {
    PageInfo<Employee> getEmployeeList(Integer pageNum);
}
