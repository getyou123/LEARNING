package org.getyou123;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class protalController {
    @RequestMapping("/")
    public String protal() {
        return "index";
    }
}
