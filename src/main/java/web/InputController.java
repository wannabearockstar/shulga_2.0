package web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value = "/input")
public class InputController {
    @RequestMapping(value = "/steps/models", method = RequestMethod.GET)
    public String inputStepModelsPage(Model model) {
        return "input/models";
    }

    @RequestMapping(value = "/steps/disciplines", method = RequestMethod.GET)
    public String inputStepBoundariesPage(Model model) {
        return "input/disciplines";
    }
}
