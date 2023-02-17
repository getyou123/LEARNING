package org.example.mapper;

import org.apache.ibatis.annotations.Param;
import org.example.pojo.Dept;

public interface DeptMapper {

    /**
     * 通过deptId 获取 dept
     * @param deptId id
     * @return dept对象
     */
    Dept getDeptByDeptId(@Param("dept_id") Integer deptId);

    /**
     * 通过 deptId 获取部门的信息
     * @param deptId id
     * @return dept
     */
    Dept getDeptAndAllEmpByDeptId(@Param("dept_id") Integer deptId);


    /**
     * 通过 deptId 获取部门的信息
     * @param deptId id
     * @return dept
     */
    Dept getDeptAndAllEmpByDeptIdStep(@Param("dept_id") Integer deptId);
}
