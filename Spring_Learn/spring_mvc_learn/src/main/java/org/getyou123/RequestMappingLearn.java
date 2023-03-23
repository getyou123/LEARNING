package org.getyou123;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


import javax.servlet.http.HttpServletRequest;


@Controller
@RequestMapping("/RequestMappingLearn")
public class RequestMappingLearn {
    @RequestMapping("/hello")
    public String hello() {
        //设置视图名称
        return "index";
    }


    /**
     * 这个方法支持了两种请求方式，如果不支持会报错 405
     *
     * @return 页面
     */
    @RequestMapping(
            value = "/testMethod",
            method = {RequestMethod.GET, RequestMethod.POST}
    )
    public String testRequestMapping() {
        return "success";
    }


    @GetMapping("/testGetMapping")
    public String testRequestGetMapping() {
        return "success";
    }


    /**
     * 实现获取Restfull风格
     *
     * @param id       id
     * @param username username
     * @return 页面
     */
    @GetMapping("/testRestfulApi/{id}/{username}")
    public String testRestfulApi(@PathVariable("id") String id,
                                 @PathVariable("username") String username) {
        System.out.println("id:" + id + ",username:" + username);
        return "success";
    }


    /**
     * 通过HttpServletRequest获取输入的参数
     *
     * @param request
     * @return
     */
    @GetMapping("/testGetParaByServlet")
    public String testGetParaByServlet(HttpServletRequest request) {
        String id = request.getParameter("id");
        String username = request.getParameter("username");
        System.out.println("id:" + id + ",username:" + username);
        return "success";
    }

    @GetMapping("/testGetParaBYController")
    public String testGetParaBYController(String username, String password) {
        System.out.println("username:" + username + ",password:" + password);
        return "success";
    }

    /**
     * 使用RequestParam获取请求的参数
     *
     * @param username1 useer
     * @param password  pas
     * @return str
     */

    @GetMapping("/testGetParaBYControllerRequestParam")
    public String testGetParaBYControllerRequestParam(@RequestParam(value = "username", required = true) String username1, String password) {
        System.out.println("username:" + username1 + ",password:" + password);
        return "success";
    }

    /**
     * 把请求参数封装为 pojo
     *
     * @param user pojo
     * @return str
     */
    @PostMapping(value = "/testGetParaBYPojo")
    public String testGetParaBYPojo(User user) {
        System.out.println(user);
        return "success";
    }

}
