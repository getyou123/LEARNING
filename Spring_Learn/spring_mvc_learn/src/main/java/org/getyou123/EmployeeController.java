package org.getyou123;

import com.sun.tracing.dtrace.Attributes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Collection;

@RequestMapping("/employee")
@Controller
public class EmployeeController {

    // 没必要使用service,直接使用employeeDao
    @Autowired
    private EmployeeDao employeeDao;

    /**
     * 查询所有的员工信息
     *
     * @param model model
     * @return str
     */
    @RequestMapping(value = "", method = RequestMethod.GET)
    public String getEmployeeList(Model model) { // Model 实现请求域的数据共享
        Collection<Employee> employeeList = employeeDao.getAll();
        model.addAttribute("employeeList", employeeList);
        return "employee_list";
    }

    /**
     * 数据增加
     *
     * @param id
     * @param model
     * @return
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public String getEmployeeById(@PathVariable("id") Integer id, Model model) {
        Employee employee = employeeDao.get(id);
        model.addAttribute("employee", employee);
        return "employee_update";
    }

    /**
     * 进行数据的新增
     *
     * @param employee 数据
     * @return 页面
     */

    @RequestMapping(value = "", method = RequestMethod.POST)
    public String addEmployee(Employee employee) {
        employeeDao.save(employee);
        return "redirect:/employee";
    }


    /**
     * 实现按照id进行删除
     *
     * @param id id
     * @return 页面
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public String deleteEmployee(@PathVariable("id") Integer id) {
        employeeDao.delete(id);
        return "redirect:/employee";
    }


    /**
     * 执行更新
     *
     * @param employee
     * @return
     */
    @RequestMapping(value = "", method = RequestMethod.PUT)
    public String updateEmployee(Employee employee) {
        employeeDao.save(employee);
        return "redirect:/employee";
    }


}
