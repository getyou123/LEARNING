package org.getyou123;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Controller
public class ResponseBodyLearn {

    // 响应字符串
    @RequestMapping("/testResponseBodyStr")
    @ResponseBody
    public String testResponseBodyStr() {
        //此时响应浏览器数据success
        return "success";
    }

    //响应浏览器list集合
    @RequestMapping("/test/ResponseBodyList/json")
    @ResponseBody
    public List<User> testResponseBodyList() {
        User user1 = new User("A", "admin1", "123456", 23, "男");
        User user2 = new User("B", "admin2", "123456", 23, "男");
        User user3 = new User("B", "admin3", "123456", 23, "男");
        List<User> list = Arrays.asList(user1, user2, user3);
        return list;
    }

    //响应浏览器map集合
    @RequestMapping("/test/ResponseBodyMap/json")
    @ResponseBody
    public Map<String, Object> testResponseBodyMap() {
        User user1 = new User("A", "admin1", "123456", 23, "男");
        User user2 = new User("B", "admin2", "123456", 23, "男");
        User user3 = new User("B", "admin3", "123456", 23, "男");
        Map<String, Object> map = new HashMap<>();
        map.put("1001", user1);
        map.put("1002", user2);
        map.put("1003", user3);
        return map;
    }

    //响应浏览器实体类对象
    @RequestMapping("/test/ResponseBodyUser/json")
    @ResponseBody
    public User testResponseBody() {
        User user = new User("A", "admin1", "123456", 23, "男");
        return user;
    }
}
