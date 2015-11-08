package web.controller;

import mapper.ScheduleConfigLoader;
import mapper.serializer.ScheduleCsvSerializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import web.model.Result;
import web.service.data.DataService;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
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

    @RequestMapping(value = "/{id}/download", method = RequestMethod.GET)
    public ModelAndView downloadResult(@PathVariable("id") int id, HttpServletResponse response) throws IOException {
        response.setContentType("applications/csv");
        response.setContentType("applications/unknown");
//        response.setCharacterEncoding("UTF-16LE");
        response.setHeader("content-disposition", "attachment;filename =data_" + id + ".csv");

        ServletOutputStream writer = response.getOutputStream();
        writer.write(new ScheduleCsvSerializer().serialize(ScheduleConfigLoader.fromLocalSchedule(String.format("schedule_result_%d.json", id))).getBytes("UTF-8"));
        writer.flush();
        writer.close();
        return null;
    }

    @RequestMapping(value = "{id}/real", method = RequestMethod.GET)
    public String outRealPage(@PathVariable("id") int id, Model model) throws IOException {
        model.addAttribute("scheduleId", id);
        model.addAttribute("schedule_csv", dataService.paintRealSchedule(id));
        return "output/real";
    }
}
