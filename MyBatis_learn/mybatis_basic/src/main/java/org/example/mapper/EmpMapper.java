package org.example.mapper;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.example.pojo.Emp;
import org.example.pojo.User;

import java.util.List;
import java.util.Map;

public interface EmpMapper {
    /**
     * 通过empId获取emp
     *
     * @param empId id
     * @return emp
     */
    Emp getEmpByEmpId(@Param("empId") Integer empId);


    /**
     * 通过empId获取emp- 对dept进行赋值
     *
     * @param empId id
     * @return emp
     */
    Emp getEmpByEmpId1(@Param("empId") Integer empId);


    /**
     * 通过empId获取emp- 对dept进行赋值
     *
     * @param empId id
     * @return emp
     */
    Emp getEmpByEmpId2(@Param("empId") Integer empId);


    /**
     * 通过empId获取emp- 分步查询
     *
     * @param empId id
     * @return emp
     */
    Emp getEmpByEmpIdByStep(@Param("empId") Integer empId);


    /**
     * 通过empId获取emp- 分布查询
     *
     * @param dept_id id
     * @return emp list
     */
    List<Emp> getEmpListByStep(@Param("dept_id") Integer dept_id);


    /**
     * 按照构造的emp来查询mysql库
     *
     * @param emp 构造的emp对象
     * @return 查询返回的emp对象
     */
    List<Emp> getEmpListByCondition(Emp emp);


    /**
     * 实现批量插入操作
     *
     * @param emps 操作
     */
    void insertMultiEmps(@Param("emps") List<Emp> emps);


    /**
     * 实现批量删除操作
     *
     * @param emps
     */
    void deleteMultiEmps(@Param("emps") int[] emps);

    /**
     * 注解-通过单个的id获取对象
     *
     * @param id id
     * @return emp
     */
    @Select("select * From t_emp where emp_id = #{id}")
    Emp getEmpByIdAnno(int id);


    /**
     * 注解-实现插入操作z
     *
     * @param emp user
     * @return row_num
     */
    @Insert("insert into t_emp(emp_id,emp_name,sex) values " +
            "(#{empId}, #{empName},#{sex})")
    int insertEmpByAnno(Emp emp);

    /**
     * 注解-实现删除
     *
     * @param id id
     * @return row
     */
    @Delete("delete from t_emp where emp_id=#{id}")
    int deleteEmpByIdAnno(int id);


    /**
     * 注解-获取单行数据为map
     * @return map
     */
    @Select("select * from t_emp where emp_id = 1")
    Map<String, Object> getAMapFromTable();

}
