package org.example.mapper;

import org.apache.ibatis.annotations.Param;
import org.example.pojo.Emp;
import org.example.pojo.User;

import java.util.List;

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
     * 通过empId获取emp- 分步查询
     * @param empId id
     * @return emp
     */
    Emp getEmpByEmpIdByStep(@Param("empId") Integer empId);


    /**
     *
     * 通过empId获取emp- 分布查询
     * @param dept_id id
     * @return emp list
     */
    List<Emp> getEmpListByStep(@Param("dept_id") Integer dept_id);


    /**
     * 按照构造的emp来查询mysql库
     * @param emp 构造的emp对象
     * @return 查询返回的emp对象
     */
    List<Emp> getEmpListByCondition(Emp emp);


    /**
     * 实现批量插入操作
     * @param emps 操作
     *
     */
    void insertMultiEmps(@Param("emps") List<Emp> emps);


    /**
     * 实现批量删除操作
     * @param emps
     */
     void deleteMultiEmps(@Param("emps") int[] emps);

}
