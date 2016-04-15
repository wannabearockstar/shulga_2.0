package ga.model.service;

import ga.model.config.ScheduleConfig;
import ga.model.repository.AuditoryRepository;
import ga.model.schedule.Auditory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.ThreadLocalRandom;
import java.util.regex.Pattern;

/**
 * Created by wannabe on 15.04.16.
 */
@Service
public class AuditoryService {
	public static final double DEFAULT_DISTANCE = 10;
	public static final double LEVEL_MULTIPLIER = 3;
	public static final Pattern namePattern = Pattern.compile("^\\w\\d{3}$");

	@Autowired
	private AuditoryRepository auditoryRepository;

	public Auditory random(final ScheduleConfig config) {
		int idx = ThreadLocalRandom.current().nextInt(0, config.getAuditories().size());
		return config.getAuditories().get(idx);
	}

	private int getLetterAlphabetOrder(char c) {
		int temp = (int) c;
		int temp_integer = 64; //for upper case
		if (temp <= 90 & temp >= 65) {
			return temp - temp_integer;
		}
		return 0;
	}

	private double getDistanceByName(Auditory first, Auditory other) {
		return Math.abs(getLetterAlphabetOrder(first.getName().charAt(0)) - getLetterAlphabetOrder(other.getName().charAt(0))) + Math.abs(Integer.parseInt(first.getName().substring(1)) - Integer.parseInt(other.getName().substring(1)));
	}

	public double getDistance(Auditory first, Auditory second) {
		if (first.getId() == second.getId()) {
			return 0;
		}
		if (!hasValidCoordinates(first) || !hasValidCoordinates(second)) {
			if (isNameValidForCampus(first) && isNameValidForCampus(second)) {
				return getDistanceByName(first, second);
			}
			return DEFAULT_DISTANCE;
		}

		double x = (first.getLat() - second.getLat()) * 10000;
		double y = (first.getLon() - second.getLon()) * 10000;
		double z = first.getLevel() - second.getLevel() * 10;
		return Math.sqrt(x * x + y * y) + LEVEL_MULTIPLIER * Math.abs(z);
	}


	public boolean hasValidCoordinates(Auditory auditory) {
		return auditory.getLat() != 0.0 && auditory.getLon() != 0.0;
	}

	public boolean isNameValidForCampus(Auditory auditory) {
		return namePattern.matcher(auditory.getName()).matches();
	}
}
