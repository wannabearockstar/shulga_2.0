package mapper;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import ga.model.config.ScheduleConfig;
import ga.model.schedule.Group;
import mapper.model.GroupInfo;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class ScheduleConfigLoader {

    /**
     * Load ScheduleConfig from remote server
     */
    public static ScheduleConfig fromRemote(String url, List<Group> groups) throws IOException {
        List<GroupInfo> data = GroupInfoLoader.fromRemote(url, groups);
        return GroupInfo.toScheduleConfig(data);
    }

    /**
     * Load ScheduleConfig from file system
     */
    public static ScheduleConfig fromLocal(String filename) throws IOException {
        Path path = Paths.get(filename);

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        try (BufferedReader writer = Files.newBufferedReader(path)) {
            return mapper.readValue(writer, ScheduleConfig.class);
        }
    }

    /**
     * Save ScheduleConfig to file system
     */
    public static void saveToLocal(ScheduleConfig config, String filename) throws IOException {
        Path path = Paths.get(filename);
        ObjectMapper mapper = new ObjectMapper();

        try (BufferedWriter writer = Files.newBufferedWriter(path)) {
            mapper.writeValue(writer, config);
        }
    }

}
