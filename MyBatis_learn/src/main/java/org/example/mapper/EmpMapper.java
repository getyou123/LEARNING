package org.example.mapper;

import org.apache.ibatis.annotations.Param;
import org.example.pojo.Emp;

public interface EmpMapper {
    /**
     * 通过empId获取emp
     * @param empId id
     * @return emp
     */
    Emp getEmpByEmpId(@Param("empId") Integer empId);


    /**
     * 通过empId获取emp- 对dept进行赋值
     * @param empId id
     * @return emp
     */
    Emp getEmpByEmpId1(@Param("empId") Integer empId);


    /**
     * 通过empId获取emp- 对dept进行赋值
     * @param empId id
     * @return emp
     */
    Emp getEmpByEmpId2(@Param("empId") Integer empId);


    /**
     *
     * 通过empId获取emp- 分布查询
     * @param empId id
     * @return emp
     */
    Emp getEmpByEmpIdByStep(@Param("empId") Integer empId);


}
