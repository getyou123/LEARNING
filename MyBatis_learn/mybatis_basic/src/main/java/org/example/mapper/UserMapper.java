package org.example.mapper;

import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
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
     * 查询功能-多个用户信息
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
     *
     * @param user 用户
     */
    void insertUserByUserObj(User user);


    /**
     * 验证登录-3
     *
     * @param username 名
     * @param password 密码
     */
    User getUserByPara(@Param("username") String username, @Param("password") String password);

    /**
     * 获取所有的行数
     *
     * @return 行数
     */
    Integer getAllCount();

    /**
     * 按照map作为结果来查询
     * 如果不存在具体的实体类
     * 那就使用map
     * 当然存在实体类的时候也是可以查询为map的
     *
     * @return map
     */
    Map<String, Object> getMapFromTable();

    /**
     * 按照map作为结果来查询
     * 当对应多条数据时候可以使用这个list
     *
     * @return map
     */
    List<Map<String, Object>> getAllMapFromTable();

    /**
     * 按照map作为结果来查询
     * 当对应多条数据时候可以使用这个Map map 的结构
     * 不过要指明key
     * 这里指明了是id作为key
     *
     * @return map
     */
    @MapKey("id")
    Map<String, Object> getAllMapAsMapFromTable();


    /**
     * 按照模糊的名字来查询
     *
     * @return list
     */
    List<User> getUserByLikeName(@Param("username") String username);


    /**
     * 实现批量的删除操作
     *
     * @param ids 需要删除的用户ids eg:9,10
     */
    void deleteMultiUser(@Param("ids") String ids);

    /**
     * 查询指定表名字的所有的数据
     *
     * @param tableName 表名字
     */
    List<User> getUserList(@Param("tableName") String tableName);

    /**
     * 插入了数据之后获取主键id
     *
     * @param user 数据
     * @return id
     */
    int getIdAfterInsert(User user);
}