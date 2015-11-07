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
import web.service.data.DataService;

@Controller
@RequestMapping(value = "/output")
public class OutController {
    @Autowired
    DataService dataService;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String outPage(Model model) {
        return "output/result";
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<Result> getResult(@PathVariable("id") int id) {
        return new ResponseEntity<>(Result.success(dataService.getResult(id)), HttpStatus.OK);
    }
}
