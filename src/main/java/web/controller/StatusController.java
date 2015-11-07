package web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import web.model.Result;
import web.service.algorithm.AlgorithmService;

@Controller
@RequestMapping(value = "/status")
public class StatusController {
    @Autowired
    private AlgorithmService algorithmService;

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public String statusPage(@PathVariable(value = "id") long id, Model model) {
        model.addAttribute("id", id);
        return "status/index";
    }

    @RequestMapping(value = "/{id}/data", method = RequestMethod.GET)
    public ResponseEntity<Result> statusData(@PathVariable(value = "id") int id) {
        return new ResponseEntity<>(Result.success(algorithmService.getStatus(id)), HttpStatus.OK);
    }
}
