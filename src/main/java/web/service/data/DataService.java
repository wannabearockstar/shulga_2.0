package web.service.data;

import ga.model.bound.BoundCollection;
import ga.model.config.CurriculumUnit;
import ga.model.config.ScheduleConfig;
import ga.model.repository.*;
import ga.model.schedule.Schedule;
import ga.model.service.ScheduleConfigService;
import mapper.GroupInfoLoader;
import mapper.ScheduleConfigLoader;
import mapper.model.GroupInfo;
import mapper.serializer.ScheduleCsvSerializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * Created by wannabe on 07.11.15.
 */
@Service
public class DataService {
	@Autowired
	private ScheduleConfigService scheduleConfigService;
	@Autowired
	private ScheduleConfigRepository scheduleConfigRepository;
	@Autowired
	private AuditoryRepository auditoryRepository;
	@Autowired
	private DisciplineRepository disciplineRepository;
	@Autowired
	private DayTimeRepository dayTimeRepository;
	@Autowired
	private BoundCollectionRepository boundCollectionRepository;
	@Autowired
	private ScheduleRepository scheduleRepository;

	public String createScheduleConfig(CurriculumUnit[] curriculum) throws IOException {
		ScheduleConfig config = ScheduleConfigLoader.fromCurriculum(curriculum);

		return scheduleConfigRepository.save(config).getId();
	}

	public ScheduleConfig getScheduleConfig(String id) {
		return scheduleConfigService.findOne(id);
	}

	public ScheduleConfig setBoundaries(String id, BoundCollection boundCollection) {
			ScheduleConfig config = scheduleConfigRepository.findOne(id);
			config.setBounds(boundCollectionRepository.save(boundCollection));
			scheduleConfigRepository.save(config);
			return config;
	}

	public void saveSchedule(String id, ScheduleConfig config) throws IOException {
		scheduleConfigService.save(config);
	}

	public Schedule getResult(String id) {
		return scheduleRepository.findOne(id);
	}

	public String paintSchedule(String id) throws IOException {
		Schedule schedule = getResult(id);
		return new ScheduleCsvSerializer().serialize(schedule);
	}

	//// TODO: 15.04.16 в сигнатуру скорее всего надо передавать данные для fetch
	public String paintRealSchedule(String id) throws IOException {
		ScheduleConfig scheduleConfig = scheduleConfigService.findOne(id);
		Schedule realSchedule = GroupInfo.toSchedule(GroupInfoLoader.fromRemote("http://dvfu.vl.ru/api2/method/full.schedule.get.json", scheduleConfig.getGroups()));
//		realSchedule.getConfig().setTimes(scheduleConfig.getTimes());
		return new ScheduleCsvSerializer().serialize(realSchedule);
	}

	public String createScheduleConfig(ScheduleConfig config, CurriculumUnit[] curriculum) throws IOException {
		ScheduleConfig curriculmConfig = ScheduleConfigLoader.fromCurriculum(curriculum);
		config.setCurriculum(curriculmConfig.getCurriculum());

		config.setTimes(dayTimeRepository.save(config.getTimes()));
		config.setAuditories(auditoryRepository.save(config.getAuditories()));
		config.setDisciplines(disciplineRepository.save(config.getDisciplines()));
		return scheduleConfigRepository.save(config).getId();
	}
}
