package web;

import ga.GA;
import ga.model.config.ScheduleConfig;
import ga.model.schedule.Group;
import ga.model.schedule.Schedule;
import mapper.GroupLoader;
import mapper.ScheduleConfigLoader;
import mapper.serializer.ScheduleCsvSerializer;
import mapper.serializer.Serializer;

import java.io.IOException;
import java.util.List;

public class Main {

    public static void main(String[] args) throws IOException {

        Serializer<Schedule> serializer = new ScheduleCsvSerializer();
        List<Group> groups = GroupLoader.fromLocal("groups.json");

        //ScheduleConfig config = ScheduleConfigLoader.fromRemote("http://dvfu.vl.ru/api2/method/full.schedule.get.json", groups);
        ScheduleConfig config = ScheduleConfigLoader.fromLocal("config.json");
        serializer.serialize(GA.solve(config), "output.csv");

    }

}
