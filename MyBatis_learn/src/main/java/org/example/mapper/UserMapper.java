package org.example.mapper;

import org.example.pojo.User;

import java.util.List;
import java.util.Map;

public interface UserMapper {

    /**
     * 添加用户信息
     */
    int insertUser();

    /**
     * 更新用户信息
     */
    void updateUser();

    /**
     * 删除用户
     */
    void deleteUser();

    /**
     * 查询功能-单个用户信息
     */
    User getUserById();

    /**
     * 查询功能-所欲用户信息
     */
    List<User> getAllUser();

    /**
     * 查询-通过用户名称来
     */
    User getUserByName(String username);

    /**
     * 验证登录-1
     *
     * @param username
     * @param password
     * @return
     */
    User checkLogin(String username, String password);

    /**
     * 验证登录-2
     *
     * @param M map
     * @return 实际查询出来的user
     */
    User checkLoginMap(Map<String, String> M);

    /**
     * 实现用户信息插入
     * @param user
     */
    void insertUserByUserObj(User user);


}