import mapper.ScheduleConfigLoader;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import java.io.IOException;

@SpringBootApplication
@ComponentScan("web")
public class Application {
    public static void main(String[] args) throws IOException {
//        ScheduleConfig scheduleConfig = ScheduleConfigLoader.fromRemote("http://dvfu.vl.ru/api2/method/full.schedule.get.json", GroupLoader.fromLocal("groups.json"));
//        ScheduleConfigLoader.saveToLocal(scheduleConfig, "config_1.json");
        ScheduleConfigLoader.initAllEntitiesInMemory();
        SpringApplication.run(Application.class, args);
    }
}
