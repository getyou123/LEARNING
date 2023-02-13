package org.example;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.example.mapper.UserMapper;
import org.example.pojo.User;
import org.example.utils.SqlSessionUtil;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;

/**
 * Unit test for simple App.
 */
public class AppTest {

    @Test
    public void TestInert() throws IOException {
        //读取MyBatis的核心配置文件
        InputStream is = Resources.getResourceAsStream("mybatis-config.xml");
        //创建SqlSessionFactoryBuilder对象
        SqlSessionFactoryBuilder sqlSessionFactoryBuilder = new SqlSessionFactoryBuilder();
        //通过核心配置文件所对应的字节输入流创建工厂类SqlSessionFactory，生产SqlSession对象
        SqlSessionFactory sqlSessionFactory = sqlSessionFactoryBuilder.build(is);
        //创建SqlSession对象，此时通过SqlSession对象所操作的sql都必须手动提交或回滚事务
        //SqlSession sqlSession = sqlSessionFactory.openSession();
        //创建SqlSession对象，此时通过SqlSession对象所操作的sql都会自动提交
        SqlSession sqlSession = sqlSessionFactory.openSession(true);
        //通过代理模式创建UserMapper接口的代理实现类对象
        UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
        //调用UserMapper接口中的方法，就可以根据UserMapper的全类名匹配元素文件，通过调用的方法名匹配映射文件中的SQL标签，并执行标签中的SQL语句
        int result = userMapper.insertUser();
        //sqlSession.commit();
        System.out.println("结果：" + result);
    }

    @Test
    public void testUpdate() {
        // 获取 sqlSession 对象
        SqlSession sqlSession = SqlSessionUtil.getSqlSession();
        // 获取Mapper代理实现类对象
        UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
        // 执行操作
        userMapper.updateUser();
        sqlSession.close();
    }

    @Test
    public void testDelete() {
        // 获取 sqlSession 对象
        SqlSession sqlSession = SqlSessionUtil.getSqlSession();
        // 获取Mapper代理实现类对象
        UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
        // 执行操作
        userMapper.deleteUser();
        sqlSession.close();
    }


    @Test
    public void testGetUserById() {
        // 获取 sqlSession 对象
        SqlSession sqlSession = SqlSessionUtil.getSqlSession();
        // 获取Mapper代理实现类对象
        UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
        // 执行操作
        User byId = userMapper.getUserById();
        System.out.println(byId);
        sqlSession.close();
    }

    @Test
    public void testGetAllUser() {
        // 获取 sqlSession 对象
        SqlSession sqlSession = SqlSessionUtil.getSqlSession();
        // 获取Mapper代理实现类对象
        UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
        // 执行操作
        List<User> allUser = userMapper.getAllUser();
        for (User u : allUser) {
            System.out.println(u);
        }
        sqlSession.close();
    }

    @Test
    public void testGetUserByUserName() {
        // 获取 sqlSession 对象
        SqlSession sqlSession = SqlSessionUtil.getSqlSession();
        // 获取Mapper代理实现类对象
        UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
        // 执行操作
        User User1 = userMapper.getUserByName("admin1");
        System.out.println(User1);
        sqlSession.close();
    }

    @Test
    public void testCheckLogin() {
        // 获取 sqlSession 对象
        SqlSession sqlSession = SqlSessionUtil.getSqlSession();
        // 获取Mapper代理实现类对象
        UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
        // 执行操作
        User User1 = userMapper.checkLogin("admin1", "123456");
        System.out.println(User1);
        sqlSession.close();
    }

    @Test
    public void testCheckLoginMap() {
        // 获取 sqlSession 对象
        SqlSession sqlSession = SqlSessionUtil.getSqlSession();
        // 获取Mapper代理实现类对象
        UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
        HashMap<String, String> stringStringHashMap = new HashMap<>();
        stringStringHashMap.put("username", "admin1");
        stringStringHashMap.put("password", "123456");
        // 执行操作
        User User1 = userMapper.checkLoginMap(stringStringHashMap);
        System.out.println(User1);
        sqlSession.close();
    }

    @Test
    public void testInsertUser() {
        // 获取 sqlSession 对象
        SqlSession sqlSession = SqlSessionUtil.getSqlSession();
        // 获取Mapper代理实现类对象
        UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
        User user = new User(null, "insertUser", "1234456", 11, "男", "79990@qq.com");
        // 执行操作
        userMapper.insertUserByUserObj(user);
        sqlSession.close();
    }
}
