package com.rev.mapper;

import com.rev.pojo.Dept;
import com.rev.pojo.DeptExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface DeptMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_dept
     *
     * @mbggenerated Fri Feb 17 09:46:22 CST 2023
     */
    int countByExample(DeptExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_dept
     *
     * @mbggenerated Fri Feb 17 09:46:22 CST 2023
     */
    int deleteByExample(DeptExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_dept
     *
     * @mbggenerated Fri Feb 17 09:46:22 CST 2023
     */
    int insert(Dept record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_dept
     *
     * @mbggenerated Fri Feb 17 09:46:22 CST 2023
     */
    int insertSelective(Dept record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_dept
     *
     * @mbggenerated Fri Feb 17 09:46:22 CST 2023
     */
    List<Dept> selectByExample(DeptExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_dept
     *
     * @mbggenerated Fri Feb 17 09:46:22 CST 2023
     */
    int updateByExampleSelective(@Param("record") Dept record, @Param("example") DeptExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_dept
     *
     * @mbggenerated Fri Feb 17 09:46:22 CST 2023
     */
    int updateByExample(@Param("record") Dept record, @Param("example") DeptExample example);
}