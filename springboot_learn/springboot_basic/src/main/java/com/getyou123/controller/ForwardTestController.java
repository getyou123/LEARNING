package com.getyou123.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.ResponseBody;


import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;


@Controller
public class ForwardTestController {

    /**
     * 转发的前
     *
     * @param request
     * @return
     */
    @GetMapping("/first")
    public String first(HttpServletRequest request) {
        request.setAttribute("msg", "this is a msg");
        request.setAttribute("code", 200);
        return "forward:/second";
    }

    /**
     * 转发目的地
     *
     * @param request
     * @param msg
     * @param integer
     * @return
     */
    @ResponseBody
    @GetMapping("/second")
    public String second(HttpServletRequest request,
                         @RequestAttribute("msg") String msg,
                         @RequestAttribute("code") Integer integer
    ) {
        Object msg1 = request.getAttribute("msg");


        HashMap<String, Object> map = new HashMap<>();

        map.put("msg", msg1);
        map.put("msg1", msg);
        map.put("code", integer);
        return map.toString();
    }

}
