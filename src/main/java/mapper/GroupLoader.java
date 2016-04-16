package mapper;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import ga.model.comparator.GroupComparator;
import ga.model.schedule.Group;
import mapper.model.GroupCollection;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class GroupLoader {

	/**
	 * Load list of all groups from remote api
	 */
	public static List<Group> fromRemote(String url) throws IOException {

		CloseableHttpClient client = HttpClients.createDefault();
		HttpGet get = new HttpGet(url);

		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

		try (CloseableHttpResponse response = client.execute(get)) {

			Reader reader = new InputStreamReader(response.getEntity().getContent());
			try (BufferedReader br = new BufferedReader(reader)) {
				GroupCollection collection = mapper.readValue(br, GroupCollection.class);
				collection.getGroups().sort(new GroupComparator());
				return collection.getGroups();
			}
		}
	}

	/**
	 * Load list of groups from file system
	 */
	public static List<Group> fromLocal(String filename) throws IOException {
		Path path = Paths.get(filename);

		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

		try (BufferedReader br = Files.newBufferedReader(path)) {
			GroupCollection collection = mapper.readValue(br, GroupCollection.class);
			collection.getGroups().sort(new GroupComparator());
			return collection.getGroups();
		}
	}

	/**
	 * Save list of groups to file system
	 */
	public static void saveToLocal(List<Group> groups, String filename) throws IOException {
		Path path = Paths.get(filename);
		ObjectMapper mapper = new ObjectMapper();

		try (BufferedWriter writer = Files.newBufferedWriter(path)) {
			GroupCollection collection = new GroupCollection(groups);
			mapper.writeValue(writer, collection);
		}
	}

}
