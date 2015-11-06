package web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by wannabe on 06.11.15.
 */
@Controller
@RequestMapping(value = "/output")
public class OutController {
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String outPage(Model model) {
        return "output/result";
    }
}
