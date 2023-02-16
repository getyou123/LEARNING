package org.example;

import org.apache.ibatis.session.SqlSession;
import org.example.mapper.DeptMapper;
import org.example.mapper.EmpMapper;
import org.example.pojo.Dept;
import org.example.pojo.Emp;
import org.example.utils.SqlSessionUtil;
import org.junit.Test;

public class DeptTest {
    @Test
    public void testGetDeptByDeptId() {
        // 获取 sqlSession 对象
        SqlSession sqlSession = SqlSessionUtil.getSqlSession();
        // 获取Mapper代理实现类对象
        DeptMapper deptMapper = sqlSession.getMapper(DeptMapper.class);
        Dept deptByDeptId = deptMapper.getDeptByDeptId(1);
        System.out.println(deptByDeptId);
    }
}
