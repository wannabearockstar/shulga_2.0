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

import java.io.IOException;

@Controller
@RequestMapping(value = "/output")
public class OutController {
    @Autowired
    DataService dataService;

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public String outPage(@PathVariable("id") int id, Model model) throws IOException {
        model.addAttribute("scheduleId", id);
        model.addAttribute("schedule_csv", dataService.paintSchedule(id));
        return "output/result";
    }

    @RequestMapping(value = "/{id}/data", method = RequestMethod.GET)
    public ResponseEntity<Result> getResult(@PathVariable("id") int id) {
        return new ResponseEntity<>(Result.success(dataService.getResult(id)), HttpStatus.OK);
    }
}
