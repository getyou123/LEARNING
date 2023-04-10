package com.getyou123.controller;

import com.getyou123.bean.Person1;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class ParameterTestController {


    /**
     * 设置一个cookie
     *
     * @param response res
     * @return str
     */
    @GetMapping("/setCookie")
    public String beforeGet(HttpServletResponse response) {
        Cookie cookie = new Cookie("_ga", "cookie_value");
        response.addCookie(cookie);
        return "set cookie successed";
    }


    /**
     * 演示获取各种请求参数
     *
     * @param id
     * @param name
     * @param pv
     * @param userAgent
     * @param header
     * @param age
     * @param inters
     * @param params
     * @param _ga
     * @param cookie
     * @return
     */
    //  car/2/owner/zhangsan
    @GetMapping("/car/{id}/owner/{username}")
    public Map<String, Object> getCar(
            @PathVariable("id") Integer id, // 获取路径上的变量
            @PathVariable("username") String name, // 获取路径上的变量
            @PathVariable Map<String, String> pv,  // 把所有的路径变量都放在这个map中
            @RequestHeader("User-Agent") String userAgent, // 获取浏览器的标识
            @RequestHeader Map<String, String> header, // 获取请求头
            @RequestParam("age") Integer age, // 获取单个的请求参数
            @RequestParam("inters") List<String> inters, // 获取list的请求
            @RequestParam Map<String, String> params, // 获取所有的请求参数
            @CookieValue("_ga") String _ga, // 获取cookie中的参数
            @CookieValue("_ga") Cookie cookie // 获取整个的cookie
    ) {
        Map<String, Object> map = new HashMap<>();
        map.put("id", id);
        map.put("name", name);
        map.put("pv", pv);
        map.put("userAgent", userAgent);
        map.put("headers", header);
        map.put("age", age);
        map.put("inters", inters);
        map.put("params", params);
        map.put("_ga", _ga);
        System.out.println(cookie.getName() + "===>" + cookie.getValue());
        return map;
    }


    /**
     * 使用矩阵变量 分号中的变量的就是矩阵变量 解析方式1
     *
     * @param low
     * @param brand
     * @param path
     * @return
     */

    //1、语法： 请求路径：/cars/sell;low=34;brand=byd,audi,yd
    //2、SpringBoot默认是禁用了矩阵变量的功能
    //      手动开启：原理。对于路径的处理。UrlPathHelper进行解析。
    //              removeSemicolonContent（移除分号内容）支持矩阵变量的
    //3、矩阵变量必须有url路径变量才能被解析
    @GetMapping("/cars/{path}")
    public Map carsSell(
            @MatrixVariable("low") Integer low,
            @MatrixVariable("brand") List<String> brand,
            @PathVariable("path") String path) {
        Map<String, Object> map = new HashMap<>();

        map.put("low", low);
        map.put("brand", brand);
        map.put("path", path);
        return map;
    }

    /**
     * 矩阵变量 解析方式2
     *
     * @param bossAge
     * @param empAge
     * @return
     */
    // /boss/1;age=20/2;age=10
    @GetMapping("/boss/{bossId}/{empId}")
    public Map boss(
            @MatrixVariable(value = "age", pathVar = "bossId") Integer bossAge,
            @MatrixVariable(value = "age", pathVar = "empId") Integer empAge
    ) {
        Map<String, Object> map = new HashMap<>();
        map.put("bossAge", bossAge);
        map.put("empAge", empAge);
        return map;
    }


    /**
     * 自定义类的封装参数
     *
     * @param person1
     * @return
     */
    @PostMapping("/saveuser")
    public Person1 saveUser(
            Person1 person1
    ) {
        return person1;
    }

}