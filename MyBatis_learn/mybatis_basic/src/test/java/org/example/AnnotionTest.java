package org.example;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.example.mapper.EmpMapper;
import org.example.mapper.UserMapper;
import org.example.pojo.Emp;
import org.example.pojo.User;
import org.example.utils.SqlSessionUtil;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

/**
 * 演示使用注解来实现增删改查
 */
public class AnnotionTest {
    @Test
    public void TestInert() throws IOException {
        //读取MyBatis的核心配置文件
        InputStream is = Resources.getResourceAsStream("mybatis-config.xml");
        SqlSessionFactoryBuilder sqlSessionFactoryBuilder = new SqlSessionFactoryBuilder();
        SqlSessionFactory sqlSessionFactory = sqlSessionFactoryBuilder.build(is);
        SqlSession sqlSession = sqlSessionFactory.openSession(true);
        //通过代理模式创建UserMapper接口的代理实现类对象
        EmpMapper mapper = sqlSession.getMapper(EmpMapper.class);
        //调用UserMapper接口中的方法，就可以根据UserMapper的全类名匹配元素文件，通过调用的方法名匹配映射文件中的SQL标签，并执行标签中的SQL语句
        Emp empByIdAnno = mapper.getEmpByIdAnno(1);
        //sqlSession.commit();
        System.out.println("结果：" + empByIdAnno);
    }

    @Test
    public void testInsertUser() {
        // 获取 sqlSession 对象
        SqlSession sqlSession = SqlSessionUtil.getSqlSession();
        // 获取Mapper代理实现类对象
        EmpMapper empMapper = sqlSession.getMapper(EmpMapper.class);

        Emp emp = new Emp();
        emp.setEmpId(12323);
        emp.setEmpName("XXXXX");
        emp.setSex("男");
        System.out.println(emp);

        int i = empMapper.insertEmpByAnno(emp);
        System.out.println(i);

        sqlSession.close();
    }

    @Test
    public void testDeleteUser() {
        // 获取 sqlSession 对象
        SqlSession sqlSession = SqlSessionUtil.getSqlSession();
        // 获取Mapper代理实现类对象
        EmpMapper empMapper = sqlSession.getMapper(EmpMapper.class);
        int i = empMapper.deleteEmpByIdAnno(12323);
        System.out.println(i);
        sqlSession.close();
    }



    @Test
    public void testGetAMapFromTable() {
        // 获取 sqlSession 对象
        SqlSession sqlSession = SqlSessionUtil.getSqlSession();
        // 获取Mapper代理实现类对象
        EmpMapper empMapper = sqlSession.getMapper(EmpMapper.class);
        Map<String, Object> aMapFromTable = empMapper.getAMapFromTable();
        System.out.println(aMapFromTable);
        sqlSession.close();
    }
}
