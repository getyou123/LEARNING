package org.example.mapper;

import org.apache.ibatis.annotations.*;
import org.example.pojo.Emp;
import org.example.pojo.User;

import java.util.List;
import java.util.Map;


@Mapper
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
     * 注解-实现插入操作
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



    /**
     * 注解实现-关联查询,这里指明了各个字段的对应关系
     * @return emp
     */
    @Select("select emp_id,emp_name,sex,t_dept.dept_id,t_dept.dept_name  from t_emp join t_dept on t_dept.dept_id = t_emp.dept_id " +
            "where emp_id = #{id}")
    @Results(id = "EmpDeptJoinMapper",value = {
            @Result(column = "emp_id", property = "empId"),
            @Result(column = "emp_name", property = "empName"),
            @Result(column = "sex", property = "sex", javaType = String.class),
            @Result(column = "dept_id", property = "dept.deptId"),
            @Result(column = "dept_name", property = "dept.deptName")
    })
    Emp getAEmpFromTableBYId(@Param("id") int id);


    /**
     * 注解实现-分布查询
     * @return emp
     * @param id id
     * @return emp
     */

    @Select("select emp_id,emp_name,sex,dept_id from t_emp where emp_id = #{id}")
    @Results(id =  "getAEmpFromTableBYIdSecond",value = {
            @Result(column = "emp_id", property = "empId"),
            @Result(column = "emp_name", property = "empName"),
            @Result(column = "sex", property = "sex", javaType = String.class),
            @Result(column = "dept_id",property = "dept", one = @One(select = "org.example.mapper.DeptMapper.getDeptAndAllEmpByDeptIdAll"))
    })
    Emp getAEmpFromTableBYIdSecond(@Param("id") int id);


    /**
     * 注解实现- resultType
     */

    @Select("select emp_id,emp_name,sex,null as dept  from t_emp " +
            "where emp_id = #{id}")
    @ResultType(Emp.class)
    Emp getAEmpFromTableBYId2(@Param("id") int id);




}
