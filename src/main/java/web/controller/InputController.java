package web.controller;

import ga.model.bound.BoundCollection;
import ga.model.config.CurriculumUnit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import web.model.Result;
import web.service.data.DataService;

import java.io.IOException;

@Controller()
@RequestMapping(value = "/input")
public class InputController {
    @Autowired
    private DataService dataService;

    @RequestMapping(value = "/steps/models", method = RequestMethod.GET)
    public String inputStepModelsPage(Model model) {
        return "input/models";
    }

    @RequestMapping(value = "/steps/disciplines", method = RequestMethod.GET)
    public String inputStepBoundariesPage(Model model) {
        return "input/disciplines";
    }

    @RequestMapping(value = "/steps/models", method = RequestMethod.POST)
    public ResponseEntity<Result> saveConfiguration(@RequestBody CurriculumUnit[] curriculums) throws IOException {
        return new ResponseEntity<>(Result.success(dataService.createScheduleConfig(curriculums)), HttpStatus.OK);
    }

    @RequestMapping(value = "/steps/models/{id}", method = RequestMethod.GET)
    public ResponseEntity<Result> getConfiguration(@PathVariable("id") int id) throws IOException {
        return new ResponseEntity<>(Result.success(dataService.getScheduleConfig(id)), HttpStatus.OK);
    }

    @RequestMapping(value = "/steps/models/{id}/boundaries", method = RequestMethod.PUT)
    public ResponseEntity<Result> setBoundaries(@PathVariable("id") int id, @RequestBody BoundCollection boundCollection) throws IOException {
        return new ResponseEntity<>(Result.success(dataService.setBoundaries(id, boundCollection)), HttpStatus.OK);
    }
}
