package mapper;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import ga.model.schedule.Group;
import mapper.model.GroupInfo;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class GroupInfoLoader {

    /**
     * Get group info from remote server api
     */
    public static GroupInfo fromRemote(String url, Group group) throws IOException {

        CloseableHttpClient client = HttpClients.createDefault();
        HttpGet get = new HttpGet(url + "?group=" + group.getId());

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        try (CloseableHttpResponse response = client.execute(get)) {

            Reader reader = new InputStreamReader(response.getEntity().getContent());
            try (BufferedReader br = new BufferedReader(reader)) {

                Date begin = getBeginOfThisWeek();
                Date end = getBeginOfNextWeek();

                // get group info for this week
                return mapper.readValue(br, GroupInfo.class)
                        .filter(begin, end)
                        .setGroup(group);
            }
        }
    }

    /**
     * Get groups info from remote server api
     */
    public static List<GroupInfo> fromRemote(String url, List<Group> groups) throws IOException {
        List<GroupInfo> data = new ArrayList<>(groups.size());

        for (Group group : groups) {
            data.add(GroupInfoLoader.fromRemote(url, group));
        }

        return data;
    }

    private static Date getBeginOfThisWeek() {
        // get today and clear time of day
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 0); // ! clear would not reset the hour of day !
        cal.clear(Calendar.MINUTE);
        cal.clear(Calendar.SECOND);
        cal.clear(Calendar.MILLISECOND);

        // get start of this week in milliseconds
        cal.set(Calendar.DAY_OF_WEEK, cal.getFirstDayOfWeek());
        return cal.getTime();
    }

    private static Date getBeginOfNextWeek() {
        // get today and clear time of day
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 0); // ! clear would not reset the hour of day !
        cal.clear(Calendar.MINUTE);
        cal.clear(Calendar.SECOND);
        cal.clear(Calendar.MILLISECOND);

        // get start of the next week
        cal.set(Calendar.DAY_OF_WEEK, cal.getFirstDayOfWeek());
        cal.add(Calendar.WEEK_OF_YEAR, 1);
        return cal.getTime();
    }

}
