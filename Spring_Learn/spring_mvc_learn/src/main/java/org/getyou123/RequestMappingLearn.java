package org.getyou123;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


@Controller
@RequestMapping("/RequestMappingLearn")
public class RequestMappingLearn {
    @RequestMapping("/hello")
    public String hello() {
        //设置视图名称
        return "index";
    }


    /**
     * 支持了两种请求方式，如果不支持会报错 405
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


}
