package org.getyou123.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import org.getyou123.mapper.EmployeeMapper;
import org.getyou123.pojo.Employee;
import org.getyou123.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeServiceImpl implements EmployeeService {
    @Autowired
    private EmployeeMapper employeeMapper;
    @Override
    public PageInfo<Employee> getEmployeeList(Integer pageNum) {
        PageHelper.startPage(pageNum, 4);
        List<Employee> list = employeeMapper.getEmployeeList();
        PageInfo<Employee> page = new PageInfo<>(list, 5);
        return page;
    }
}
