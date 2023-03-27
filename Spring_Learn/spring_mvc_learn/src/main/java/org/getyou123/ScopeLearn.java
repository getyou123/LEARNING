package org.getyou123;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;

@Controller
public class ScopeLearn {

    /**
     * ModelAndView有Model和View的功能
     * Model主要用于向请求域共享数据
     * View主要用于设置视图，实现页面跳转
     */
    @RequestMapping("/testModelAndView")
    public ModelAndView testModelAndView() {
        ModelAndView mav = new ModelAndView(); //向请求域共享数据
        mav.addObject("testScope", "hello,ModelAndView"); //设置视图，实现页面跳转
        mav.setViewName("successFromModelAndView");
        return mav;
    }

    /**
     * 单独使用model进行请求域内的数据共享
     *
     * @param model m
     * @return view
     */
    @RequestMapping("/testModel")
    public String testModel(Model model) {
        model.addAttribute("testScope", "hello,Model");
        return "successFromModelAndView";
    }

    /**
     * session内的数据共享
     *
     * @param session m
     * @return view
     */
    @RequestMapping("/testSession")
    public String testSession(HttpSession session) {
        session.setAttribute("testSessionScope", "hello,session");
        return "successForSesionAndApplication";
    }

    /**
     * session内的数据共享
     *
     * @param session m
     * @return view
     */
    @RequestMapping("/testApplication")
    public String testApplication(HttpSession session) {
        ServletContext application = session.getServletContext();
        application.setAttribute("testApplicationScope", "hello,application");
        return "successForSesionAndApplication";
    }


}
