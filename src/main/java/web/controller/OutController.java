package web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value = "/output")
public class OutController {
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String outPage(Model model) {
        return "output/result";
    }
}
