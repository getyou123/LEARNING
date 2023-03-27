package org.getyou123;

import com.sun.tracing.dtrace.Attributes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Collection;

@RequestMapping("/employee")
@Controller
public class EmployeeController {

    // 没必要使用service,直接使用employeeDao
    @Autowired
    private EmployeeDao employeeDao;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String getEmployeeList(Model model) { // Model 实现请求域的数据共享
        Collection<Employee> employeeList = employeeDao.getAll();
        model.addAttribute("employeeList", employeeList);
        return "employee_list";
    }

}
