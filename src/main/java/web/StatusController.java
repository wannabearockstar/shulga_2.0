package web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by wannabe on 06.11.15.
 */
@Controller
@RequestMapping(value = "/status")
public class StatusController {
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public String statusPage(@PathVariable(value = "id") long id, Model model) {
        model.addAttribute("id", id);
        return "status/index";
    }
}
