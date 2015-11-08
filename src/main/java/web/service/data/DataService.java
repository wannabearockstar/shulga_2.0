package web.service.data;

import ga.model.bound.BoundCollection;
import ga.model.config.CurriculumUnit;
import ga.model.config.ScheduleConfig;
import ga.model.schedule.Schedule;
import mapper.GroupInfoLoader;
import mapper.ScheduleConfigLoader;
import mapper.model.GroupInfo;
import mapper.serializer.ScheduleCsvSerializer;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by wannabe on 07.11.15.
 */
@Service
public class DataService {

    public Integer createScheduleConfig(CurriculumUnit[] curriculum) throws IOException {
        ScheduleConfig config = ScheduleConfigLoader.fromCurriculum(curriculum);

        int randomIndex = ThreadLocalRandom.current().nextInt(0, Integer.MAX_VALUE);
        ScheduleConfigLoader.saveToLocal(config, String.format("schedule_config_%d.json", randomIndex));

        return randomIndex;
    }

    public ScheduleConfig getScheduleConfig(int id) {
        try {
            return ScheduleConfigLoader.fromLocal(String.format("schedule_config_%d.json", id));
        } catch (IOException e) {
            return null;
        }
    }

    public ScheduleConfig setBoundaries(int id, BoundCollection boundCollection) {
        try {
            ScheduleConfig config = ScheduleConfigLoader.fromLocal(String.format("schedule_config_%d.json", id));
            config.setBounds(boundCollection);
            ScheduleConfigLoader.saveToLocal(config, String.format("schedule_config_%d.json", id));
            return config;
        } catch (IOException e) {
            return null;
        }
    }

    public void saveSchedule(int id, ScheduleConfig config) throws IOException {
        ScheduleConfigLoader.saveToLocal(config, String.format("schedule_config_%d.json", id));
    }

    public Schedule getResult(int id) {
        try {
            return ScheduleConfigLoader.fromLocalSchedule(String.format("schedule_result_%d.json", id));
        } catch (IOException e) {
            return null;
        }
    }

    public String paintSchedule(int id) throws IOException {
        Schedule schedule = getResult(id);
        return new ScheduleCsvSerializer().serialize(schedule);
    }

    public String paintRealSchedule(int id) throws IOException{
        Schedule schedule = getResult(id);
        Schedule realSchedule = GroupInfo.toSchedule(GroupInfoLoader.fromRemote("http://dvfu.vl.ru/api2/method/full.schedule.get.json", schedule.getConfig().getGroups()));
        return new ScheduleCsvSerializer().serialize(realSchedule);
    }
}
