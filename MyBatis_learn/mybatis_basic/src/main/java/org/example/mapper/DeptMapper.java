package org.example.mapper;

import org.apache.ibatis.annotations.*;
import org.example.pojo.Dept;

public interface DeptMapper {

    /**
     * 通过deptId 获取 dept
     *
     * @param deptId id
     * @return dept对象
     */
    Dept getDeptByDeptId(@Param("dept_id") Integer deptId);

    /**
     * 通过 deptId 获取部门的信息
     *
     * @param deptId id
     * @return dept
     */
    Dept getDeptAndAllEmpByDeptId(@Param("dept_id") Integer deptId);


    /**
     * 通过 deptId 获取部门的信息
     *
     * @param deptId id
     * @return dept
     */
    Dept getDeptAndAllEmpByDeptIdStep(@Param("dept_id") Integer deptId);


    /**
     * 通过 deptId 获取部门的信息
     * 注解方式
     *
     * @param deptId id
     * @return dept
     */

    @Select("select dept_id, dept_name From t_dept where dept_id =#{dept_id}")
    @Results(id="DeptMapper",value = {
            @Result(column = "dept_id",property = "deptId"),
            @Result(column = "dept_name",property = "deptName"),
            @Result(column = "dept_id",property = "emps",many = @Many(select = "org.example.mapper.EmpMapper.getEmpListByStep")),
    })
    Dept getDeptAndAllEmpByDeptIdAll(@Param("dept_id") Integer deptId);

}
